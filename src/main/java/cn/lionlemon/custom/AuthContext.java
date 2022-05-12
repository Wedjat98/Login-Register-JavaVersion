package cn.lionlemon.custom;

import cn.lionlemon.entity.UserEntity;
import lombok.Data;

@Data
public class AuthContext {
    private UserEntity userEntity;
    private boolean tokenInvalid;

    public void ensureAuthenticated() {
        if (tokenInvalid) throw new RuntimeException("wrong token error!");
        if (userEntity == null) throw new RuntimeException("Please Login first");

    }
}
