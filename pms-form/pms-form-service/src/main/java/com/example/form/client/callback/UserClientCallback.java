package com.example.form.client.callback;

import com.example.form.client.UserInfoClient;
import com.example.form.model.dto.ResultUserBaseInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author GLaDOS
 * @date 2023/5/1 18:42
 */
@Component
public class UserClientCallback implements FallbackFactory<UserInfoClient> {
    @Override
    public UserInfoClient create(Throwable throwable) {
        return new UserInfoClient() {
            @Override
            public ResultUserBaseInfo getAuthUserBaseInfoById(Integer id) {
                ResultUserBaseInfo rub = new ResultUserBaseInfo();
                rub.setUsername("被限流了...");
                return rub;
            }

            @Override
            public ResultUserBaseInfo getPropUserBaseInfoById(Integer id) {
                ResultUserBaseInfo rub = new ResultUserBaseInfo();
                rub.setUsername("被限流了...");
                return rub;
            }
        };
    }
}
