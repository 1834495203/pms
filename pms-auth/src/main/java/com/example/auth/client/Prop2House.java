package com.example.auth.client;

import com.example.auth.po.Information;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author GLaDOS
 * @date 2023/4/13 20:19
 */
@FeignClient(value = "form-api")
public interface Prop2House {

    @RequestMapping(value = "form/building/info", method = RequestMethod.POST)
    Information bindHouseInfo2Prop(@RequestParam("pid") Integer pid, @RequestParam("hid") Integer hid);
}
