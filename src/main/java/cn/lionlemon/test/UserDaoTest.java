package cn.lionlemon.test;

import cn.lionlemon.dao.UserDao;
import cn.lionlemon.type.User;
import org.junit.jupiter.api.Test;

public class UserDaoTest {
@Test
    public void testLogin(){
        User loginuser = new User();
        loginuser.setEmail("xiaozhuzhu");
        loginuser.setPassword("123123");



        UserDao dao = new UserDao();
        User user = dao.login(loginuser);

        System.out.println(user);
    }
}
