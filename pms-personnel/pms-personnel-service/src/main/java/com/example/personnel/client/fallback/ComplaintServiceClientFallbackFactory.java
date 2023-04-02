package com.example.personnel.client.fallback;

import com.example.exception.Error;
import com.example.model.RestResponse;
import com.example.personnel.client.ComplaintClient;
import com.example.personnel.model.dto.PostComplaintDto;
import com.example.personnel.model.po.Complaint;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 熔断
 * @author GLaDOS
 * @date 2023/4/2 15:55
 */
public class ComplaintServiceClientFallbackFactory implements FallbackFactory<ComplaintClient> {
    @Override
    public ComplaintClient create(Throwable throwable) {
        return new ComplaintClient() {
            @Override
            public RestResponse<Complaint> postComplaint(PostComplaintDto complaint) {
                return RestResponse.validFail("发生了熔断， 上传失败！", Error.DATABASE_INSERT_FAILED);
            }
        };
    }
}
