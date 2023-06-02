package com.example.auth.client;

import com.example.auth.client.callback.Prop2HouseCallback;
import com.example.auth.po.Information;
import com.example.model.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author GLaDOS
 * @date 2023/4/13 20:19
 */
@FeignClient(value = "form-api", fallbackFactory = Prop2HouseCallback.class)
public interface Prop2House {

    @RequestMapping(value = "form/building/info", method = RequestMethod.POST)
    Information bindHouseInfo2Prop(@RequestParam("pid") Integer pid, @RequestParam("hid") Integer hid);

    @RequestMapping(value = "form/building/door/{doorplate}", method = RequestMethod.GET)
    Information getHouseInfoByDoorPlate(@PathVariable(value = "doorplate") String doorplate);
}
