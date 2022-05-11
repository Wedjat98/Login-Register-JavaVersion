package cn.lionlemon.entity;

import cn.lionlemon.type.UserInput;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
//实体类
@Data
@TableName(value = "tb_user")
public class UserEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String email;
    private String password;

    public  UserEntity formUserInput(UserInput userInput){
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userInput.getEmail());
        userEntity.setPassword(userInput.getPassword());
        return userEntity;


    }

}
