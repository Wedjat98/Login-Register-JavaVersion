package cn.lionlemon.dao.impl;

import cn.lionlemon.dao.UserDaoInterface;
import org.springframework.stereotype.Repository;

@Repository("userDaoInterface")
public class UserDaoImpl implements UserDaoInterface {
    @Override
    public void save() {
        System.out.println("save method is running...............");
    }
}
