package cn.lionlemon.fetcher;

import cn.lionlemon.entity.EventEntity;
import cn.lionlemon.entity.UserEntity;
import cn.lionlemon.mapper.EventEntityMapper;
import cn.lionlemon.mapper.UserEntityMapper;
import cn.lionlemon.type.Event;
import cn.lionlemon.type.User;
import cn.lionlemon.type.UserInput;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.netflix.graphql.dgs.*;
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

    public UserDataFetcher(UserEntityMapper userEntityMapper, EventEntityMapper eventEntityMapper, PasswordEncoder passwordEncoder) {
        this.userEntityMapper = userEntityMapper;
        this.eventEntityMapper = eventEntityMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @DgsQuery
    public List<User> users() {
        List<UserEntity> userEntities = userEntityMapper.selectList(null);
        return userEntities.stream().map(User::formEntity).collect(Collectors.toList());
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
}
