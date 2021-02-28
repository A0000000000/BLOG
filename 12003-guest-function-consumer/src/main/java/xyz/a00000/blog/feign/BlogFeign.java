package xyz.a00000.blog.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.dto.EssayProxyBean;
import xyz.a00000.blog.bean.dto.EssayQueryBean;
import xyz.a00000.blog.bean.orm.EssayComment;
import xyz.a00000.blog.bean.orm.EssayInfo;
import xyz.a00000.blog.bean.orm.EssayTag;

import java.util.List;

@Component
@FeignClient(value = "BLOG-FUNCTION-PROVIDER", qualifier = "blogFeign", path = "/api/blog/provider")
public interface BlogFeign {

    @PostMapping("/getEssayList")
    BaseActionResult<PageBean<EssayQueryBean>> getEssayList(@RequestBody PageForm<EssayInfo> form);

    @GetMapping("/getEssayData")
    BaseActionResult<EssayProxyBean> getEssayData(@RequestParam("essayId") Integer essayId, @RequestParam(value = "password", required = false) String password);

    @GetMapping("/getEssayTags/{id}")
    BaseActionResult<List<EssayTag>> getEssayTags(@PathVariable("id") Integer essayId);

    @PostMapping("/getEssayComments")
    BaseActionResult<PageBean<EssayComment>> getEssayComments(@RequestBody PageForm<EssayComment> form);

}
