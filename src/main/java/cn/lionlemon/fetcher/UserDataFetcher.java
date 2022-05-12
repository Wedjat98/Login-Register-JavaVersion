package cn.lionlemon.fetcher;

import cn.lionlemon.custom.AuthContext;
import cn.lionlemon.entity.BookingEntity;
import cn.lionlemon.entity.EventEntity;
import cn.lionlemon.entity.UserEntity;
import cn.lionlemon.mapper.BookingEntityMapper;
import cn.lionlemon.mapper.EventEntityMapper;
import cn.lionlemon.mapper.UserEntityMapper;
import cn.lionlemon.type.*;
import cn.lionlemon.util.TokenUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.netflix.graphql.dgs.*;
import com.netflix.graphql.dgs.context.DgsContext;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;


@DgsComponent
@Slf4j
public class UserDataFetcher {
    private final UserEntityMapper userEntityMapper;
    private final EventEntityMapper eventEntityMapper;
    private final PasswordEncoder passwordEncoder;

    private final BookingEntityMapper bookingEntityMapper;


    public UserDataFetcher(UserEntityMapper userEntityMapper, EventEntityMapper eventEntityMapper, PasswordEncoder passwordEncoder, BookingEntityMapper bookingEntityMapper) {
        this.userEntityMapper = userEntityMapper;
        this.eventEntityMapper = eventEntityMapper;
        this.passwordEncoder = passwordEncoder;
        this.bookingEntityMapper = bookingEntityMapper;
    }

    @DgsQuery
    public List<User> users(DataFetchingEnvironment dataFetchingEnvironment) {
        AuthContext authContext = DgsContext.getCustomContext(dataFetchingEnvironment);
        authContext.ensureAuthenticated();
        List<UserEntity> userEntities = userEntityMapper.selectList(null);
        return userEntities.stream().map(User::formEntity).collect(Collectors.toList());
    }

    @DgsQuery
    public AuthData login(@InputArgument LoginInput loginInput) {

        UserEntity userEntity = this.findUserByEmail(loginInput.getEmail());
        if (userEntity == null) {
            throw new RuntimeException("email user is not exist");
        }
        boolean match = passwordEncoder.matches(loginInput.getPassword(), userEntity.getPassword());

        if (!match) {
            throw new RuntimeException("check password");
        }

        String token = TokenUtil.signToken(userEntity.getId(), 1);
        return new AuthData().setUserId(userEntity.getId())
                .setToken(token)
                .setTokenExpiration(1);

    }

    @DgsMutation
    public User createUser(@InputArgument UserInput userInput) {
        ensureUserNotExists(userInput);

        UserEntity newUserEntity = new UserEntity();
        newUserEntity.setEmail(userInput.getEmail());
        //must encode user password to ensure the safety;
        newUserEntity.setPassword(passwordEncoder.encode(userInput.getPassword()));

        userEntityMapper.insert(newUserEntity);

        User newUser = User.formEntity(newUserEntity);
        newUser.setPassword(null);
        return newUser;
    }

    @DgsData(parentType = "User")
    public List<Event> createdEvents(DgsDataFetchingEnvironment dgsDataFetchingEnvironment) {
        User user = dgsDataFetchingEnvironment.getSource();
        QueryWrapper<EventEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EventEntity::getCreatorId, user.getId());
        List<EventEntity> eventEntities = eventEntityMapper.selectList(queryWrapper);

        return eventEntities.stream().map(Event::fromEntity).collect(Collectors.toList());
    }

    private void ensureUserNotExists(UserInput userInput) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserEntity::getEmail, userInput.getEmail());
        if (userEntityMapper.selectCount(queryWrapper) >= 1) {
            throw new RuntimeException("this email is signed");
        }
    }

    private UserEntity findUserByEmail(String email) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserEntity::getEmail, email);
        return userEntityMapper.selectOne(queryWrapper);
    }

    @DgsData(parentType = "user")
    public List<Booking> bookings(DgsDataFetchingEnvironment dfe) {
        User user = dfe.getSource();
        QueryWrapper<BookingEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BookingEntity::getUserId, user.getId());
        List<BookingEntity> bookingEntities = bookingEntityMapper.selectList(queryWrapper);
        return bookingEntities
                .stream()
                .map(Booking::fromEntity)
                .collect(Collectors.toList());
    }

}
