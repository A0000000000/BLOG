package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.dto.EssayTagParams;
import xyz.a00000.blog.bean.orm.EssayEssayTag;
import xyz.a00000.blog.bean.orm.EssayTag;
import xyz.a00000.blog.bean.proxy.UserDetailsBean;
import xyz.a00000.blog.component.ResultCodeTools;
import xyz.a00000.blog.component.SecurityTools;
import xyz.a00000.blog.service.EssayTagService;

@RestController
@Slf4j
@RequestMapping("/api/blog/essayTag")
public class EssayTagController {

    @Autowired
    private ResultCodeTools resultCodeTools;
    @Autowired
    private SecurityTools securityTools;

    @Autowired
    private EssayTagService essayTagService;

    @PostMapping("/addNewTag")
    public BaseActionResult<EssayTag> addNewTag(@RequestBody EssayTagParams params) {
        log.info("添加新的标签.");
        log.info("加载用户信息.");
        UserDetailsBean currentUserDetails = securityTools.getCurrentUserDetails();
        BaseServiceResult<EssayTag> result = essayTagService.addNewTag(params, currentUserDetails);
        log.info("添加完成, 返回.");
        return BaseActionResult.from(result, resultCodeTools);
    }

    @PostMapping("/removeTag")
    public BaseActionResult<Void> removeTag(@RequestBody EssayEssayTag essayEssayTag) {
        log.info("删除一个标签.");
        log.info("加载用户信息.");
        UserDetailsBean currentUserDetails = securityTools.getCurrentUserDetails();
        BaseServiceResult<Void> result = essayTagService.removeTag(essayEssayTag, currentUserDetails);
        log.info("删除完成, 返回.");
        return BaseActionResult.from(result, resultCodeTools);
    }

}
