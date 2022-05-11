package cn.lionlemon.fetcher;

import cn.lionlemon.entity.UserEntity;
import cn.lionlemon.mapper.UserEntityMapper;
import cn.lionlemon.type.User;
import cn.lionlemon.type.UserInput;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;


@DgsComponent
@Slf4j
public class UserDataFetcher {
    private final UserEntityMapper userEntityMapper;
    private final PasswordEncoder passwordEncoder;
    public UserDataFetcher(UserEntityMapper userEntityMapper, PasswordEncoder passwordEncoder){
        this.userEntityMapper = userEntityMapper;
        this.passwordEncoder = passwordEncoder;
    }
    @DgsMutation
    public User createUser(@InputArgument UserInput userInput){
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

    private void ensureUserNotExists(UserInput userInput){
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserEntity::getEmail,userInput.getEmail());
        if (userEntityMapper.selectCount(queryWrapper)>=1){
            throw new RuntimeException("this email is signed");
        }
    }
}
