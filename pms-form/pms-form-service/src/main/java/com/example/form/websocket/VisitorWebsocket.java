package com.example.form.websocket;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.form.client.UserInfoClient;
import com.example.form.mapper.InformationMapper;
import com.example.form.model.po.Information;
import com.example.form.model.po.Visitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.List;

/**
 * 推送访客信息
 * @author GLaDOS
 * @date 2023/6/1 15:26
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/visitor/{userId}")
public class VisitorWebsocket extends WebSocket {

    @Autowired
    private InformationMapper informationMapper;

    @Autowired
    private UserInfoClient userInfoClient;

    @Override
    public void onOpen(Session session, String userId) {
        super.onOpen(session, userId);
        log.info("");
        LambdaQueryWrapper<Information> wrapper4Info = new LambdaQueryWrapper<>();
        wrapper4Info.eq(Information::getPid, userId);
        Information information = informationMapper.selectOne(wrapper4Info);

        List<Visitor> visitorByDoorplate = userInfoClient.getVisitorByDoorplate(information.getDoorplate());
        this.sendOneMessage(userId, "您有"+visitorByDoorplate.size()+"条访客信息");
        visitorByDoorplate.forEach(visitor -> {
            this.sendOneMessage(userId, "访客名为："+visitor.getName()+", 访问时间为："+visitor.getRequiredTime()+"请去访客页面查看");
        });
    }

    @Override
    public void onClose() {
        super.onClose();
    }
}
