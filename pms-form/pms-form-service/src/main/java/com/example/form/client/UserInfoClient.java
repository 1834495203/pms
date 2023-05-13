package com.example.form.client;

import com.example.form.client.callback.UserClientCallback;
import com.example.form.model.dto.ResultUserBaseInfo;
import com.example.model.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author GLaDOS
 * @date 2023/4/5 21:49
 */
@FeignClient(value = "auth-service", fallbackFactory = UserClientCallback.class)
public interface UserInfoClient {

    /**
     * 获取管理员基本信息
     * @param id 管理员id
     * @return ResultUserBaseInfo
     */
    @RequestMapping(value = "auth/info/auth/{id}", method = RequestMethod.GET)
    ResultUserBaseInfo getAuthUserBaseInfoById(@PathVariable("id") Integer id);

    /**
     * 获取业主基本信息
     * @param id 业主id
     * @return ResultUserBaseInfo
     */
    @RequestMapping(value = "auth/info/prop/{id}", method = RequestMethod.GET)
    ResultUserBaseInfo getPropUserBaseInfoById(@PathVariable("id") Integer id);
}
