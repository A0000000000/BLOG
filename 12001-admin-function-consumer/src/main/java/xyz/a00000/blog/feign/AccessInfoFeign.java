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
import xyz.a00000.blog.bean.orm.AccessInfo;
import xyz.a00000.blog.feign.fallback.AccessInfoFeignFallback;

@Component
@FeignClient(value = "WEBSITE-FUNCTION-PROVIDER", qualifier = "accessInfoFeign", path = "/api/website/accessInfo", fallback = AccessInfoFeignFallback.class)
public interface AccessInfoFeign {

    @GetMapping("/getAccessInfoById/{id}")
    BaseActionResult<AccessInfo> getAccessInfoById(@PathVariable("id") Integer id);

    @PostMapping("/getAccessInfoByForm")
    BaseActionResult<PageBean<AccessInfo>> getAccessInfoByForm(@RequestBody PageForm<AccessInfo> form);

}
