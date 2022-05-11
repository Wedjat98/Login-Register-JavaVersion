package cn.lionlemon.type;

import cn.lionlemon.entity.UserEntity;
import lombok.Data;

//对应User表 用户的实体类
@Data
public class User {
    private Integer id;
    private String email;
    private String password;

    public static User formEntity(UserEntity userEntity){
        User user = new User();
        user.setEmail(userEntity.getEmail());
        user.setId(userEntity.getId());
        return user;
    }
}
