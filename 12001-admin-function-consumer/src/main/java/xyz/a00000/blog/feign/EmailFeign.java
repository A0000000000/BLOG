package xyz.a00000.blog.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.Email;
import xyz.a00000.blog.feign.fallback.EmailFeignFallback;

@Component
@FeignClient(value = "WEBSITE-FUNCTION-PROVIDER", qualifier = "emailFeign", path = "/api/website/email", fallback = EmailFeignFallback.class)
public interface EmailFeign {

    @GetMapping("/getEmailById/{id}")
    BaseActionResult<Email> getEmailById(@PathVariable("id") Integer id);

    @PostMapping("/getEmailByForm")
    BaseActionResult<PageBean<Email>> getEmailByForm(@RequestBody PageForm<Email> form);

}
