package cn.lionlemon.service.impl;


//<bean id="userService" class="com.lionlemon.service.impl.UserServiceImpl"> </bean>

import cn.lionlemon.dao.UserDaoInterface;
import cn.lionlemon.service.UserService;

import org.springframework.stereotype.Service;

@Service("userService" )
public class UserServiceImpl implements UserService {
    private UserDaoInterface userDao;

    public void setUserDao(UserDaoInterface userDao) {
        this.userDao = userDao;
    }

    @Override
    public void save() {
        userDao.save();
    }
}
