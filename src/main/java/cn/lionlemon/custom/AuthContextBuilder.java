package cn.lionlemon.custom;

import cn.lionlemon.entity.UserEntity;
import cn.lionlemon.mapper.UserEntityMapper;
import cn.lionlemon.util.TokenUtil;
import com.netflix.graphql.dgs.context.DgsCustomContextBuilderWithRequest;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
@Slf4j
public class AuthContextBuilder implements DgsCustomContextBuilderWithRequest {
    static final String AUTHORIZATION_HEADER = "Authorization";

    private final UserEntityMapper userEntityMapper;

    public AuthContextBuilder(UserEntityMapper userEntityMapper) {
        this.userEntityMapper = userEntityMapper;
    }

    @Override
    public Object build(@Nullable Map map, @Nullable HttpHeaders httpHeaders, @Nullable WebRequest webRequest) {
        log.info("Building Auth Context");
        AuthContext authContext = new AuthContext();
        if (!httpHeaders.containsKey(AUTHORIZATION_HEADER)) {
            log.info("can not authorization");
            return authContext;
        }

        String authorization = httpHeaders.getFirst(AUTHORIZATION_HEADER);
        String token;
//        token = authorization;
        token = authorization != null ? authorization.replace("Bearer ", "") : null;

        Integer userId;
        try {
            userId = TokenUtil.verifyToken(token);
        } catch (Exception e) {
            authContext.setTokenInvalid(true);
            return authContext;
        }

        UserEntity userEntity = userEntityMapper.selectById(userId);
        if (userEntity == null) {
            authContext.setTokenInvalid(true);
            return authContext;
        }
        authContext.setUserEntity(userEntity);

        log.info("Authorize success! userId={}", userId);
        return authContext;
    }
}
