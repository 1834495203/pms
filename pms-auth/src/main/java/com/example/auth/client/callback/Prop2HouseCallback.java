package com.example.auth.client.callback;

import com.example.auth.client.Prop2House;
import com.example.auth.po.Information;
import com.example.exception.PMSException;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author GLaDOS
 * @date 2023/5/25 10:41
 */
@Component
public class Prop2HouseCallback implements FallbackFactory<Prop2House> {
    @Override
    public Prop2House create(Throwable throwable) {
        return new Prop2House() {
            @Override
            public Information bindHouseInfo2Prop(Integer pid, Integer hid) {
                return null;
            }

            @Override
            public Information getHouseInfoByDoorPlate(String doorplate) {
                Information information = new Information();
                information.setState("选择错误");
                return information;
            }
        };
    }
}
