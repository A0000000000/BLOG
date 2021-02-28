package xyz.a00000.blog.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.orm.Email;
import xyz.a00000.blog.feign.fallback.EmailFeignFallback;

@Component
@FeignClient(value = "ASYNC-TASK-PROVIDER", qualifier = "emailFeign", path = "/api/async/email", fallback = EmailFeignFallback.class)
public interface EmailFeign {

    @PostMapping("/sendEmail")
    BaseActionResult<Email> sendEmail(@RequestBody Email email);

}
