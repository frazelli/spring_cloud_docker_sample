package com.bankofaffiliates.userservice.client;

import com.bankofaffiliates.common.domain.EmailInput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "email-service"/*, decode404 = true*/)
public interface EmailServiceClient {

//	@RequestLine("POST /email-service/sendEmail")
	@PostMapping("/sendEmail")
//	@RequestMapping(method = RequestMethod.POST, value = "/email-service/sendEmail")
	ResponseEntity<String> sendEmail(EmailInput email);

}
