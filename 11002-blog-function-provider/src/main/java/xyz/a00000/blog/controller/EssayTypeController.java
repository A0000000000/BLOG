package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.EssayType;
import xyz.a00000.blog.bean.proxy.UserDetailsBean;
import xyz.a00000.blog.component.ResultCodeTools;
import xyz.a00000.blog.component.SecurityTools;
import xyz.a00000.blog.service.EssayTypeService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/blog/essayType")
public class EssayTypeController {

    @Autowired
    private SecurityTools securityTools;
    @Autowired
    private ResultCodeTools resultCodeTools;

    @Autowired
    private EssayTypeService essayTypeService;

    @GetMapping("/getAllEssayType")
    public BaseActionResult<List<EssayType>> getAllEssayType() {
        log.info("获取全部的随笔类型数据.");
        log.info("加载用户信息.");
        UserDetailsBean currentUserDetails = securityTools.getCurrentUserDetails();
        log.info("获取数据.");
        BaseServiceResult<List<EssayType>> result = essayTypeService.selectAllEssayType(currentUserDetails);
        log.info("获取完成, 准备返回.");
        return BaseActionResult.from(result, resultCodeTools);
    }

    @PostMapping("/getEssayTypeByForm")
    public BaseActionResult<PageBean<EssayType>> getEssayTypeByForm(@RequestBody PageForm<EssayType> form) {
        log.info("分页查询随笔类型信息.");
        form.getConditions().setCreatorId(securityTools.getCurrentUserDetails().getCreator().getId());
        log.info(String.format("page: %d, count: %d, creator: %d", form.getPage(), form.getCount(), form.getConditions().getCreatorId()));
        BaseServiceResult<PageBean<EssayType>> result = essayTypeService.getEssayTypeByForm(form);
        return BaseActionResult.from(result, resultCodeTools);
    }

    @PostMapping("/createEssayType")
    public BaseActionResult<EssayType> createEssayType(@RequestBody EssayType type) {
        log.info("创建新的类型.");
        log.info("加载用户信息.");
        UserDetailsBean currentUserDetails = securityTools.getCurrentUserDetails();
        BaseServiceResult<EssayType> result = essayTypeService.createEssayType(type, currentUserDetails);
        log.info("创建完成, 准备返回.");
        return BaseActionResult.from(result, resultCodeTools);
    }

    @PutMapping("/updateEssayType")
    public BaseActionResult<EssayType> updateEssayType(@RequestBody EssayType type) {
        log.info("更新现有的类型.");
        log.info("加载用户信息.");
        UserDetailsBean currentUserDetails = securityTools.getCurrentUserDetails();
        BaseServiceResult<EssayType> result = essayTypeService.updateEssayType(type, currentUserDetails);
        log.info("更新完成, 准备返回.");
        return BaseActionResult.from(result, resultCodeTools);
    }

    @DeleteMapping("/deleteEssayTypeById/{id}")
    public BaseActionResult<Void> deleteEssayTypeById(@PathVariable("id") Integer id) {
        log.info("删除一条类型信息.");
        log.info("加载用户信息.");
        UserDetailsBean currentUserDetails = securityTools.getCurrentUserDetails();
        BaseServiceResult<Void> result = essayTypeService.deleteEssayTypeById(id, currentUserDetails);
        log.info("删除完成, 准备返回.");
        return BaseActionResult.from(result, resultCodeTools);
    }

}
