package com.example.personnel.client;

import com.example.model.RestResponse;
import com.example.personnel.client.fallback.ComplaintServiceClientFallbackFactory;
import com.example.personnel.config.MultipartSupportConfig;
import com.example.personnel.model.dto.PostComplaintDto;
import com.example.personnel.model.po.Complaint;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;

/**
 * @author GLaDOS
 * @date 2023/4/2 15:54
 */
@FeignClient(value = "form-api",
        configuration = MultipartSupportConfig.class)
public interface ComplaintClient {

    @RequestMapping(value = "/complaint", method = RequestMethod.PUT)
    RestResponse<Complaint> postComplaint(@RequestPart("complaint") PostComplaintDto complaint);
}
