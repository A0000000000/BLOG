package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.orm.Image;
import xyz.a00000.blog.bean.proxy.UserDetailsBean;
import xyz.a00000.blog.component.ResultCodeTools;
import xyz.a00000.blog.component.SecurityTools;
import xyz.a00000.blog.service.ImageService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/async/image")
public class ImageController {

    @Autowired
    private SecurityTools securityTools;
    @Autowired
    private ResultCodeTools resultCodeTools;

    @Autowired
    private ImageService imageService;

    @PostMapping("/uploadImage")
    public BaseActionResult<List<Image>> uploadImage(@RequestParam("images") MultipartFile[] images, @RequestParam("essayId") Integer essayId, @RequestParam(value = "password", required = false) String password) {
        log.info("上传一张图片.");
        log.info("加载用户信息.");
        UserDetailsBean currentUserDetails = securityTools.getCurrentUserDetails();
        log.info("上传图片.");
        BaseServiceResult<List<Image>> result = imageService.uploadImage(images, essayId, password, currentUserDetails);
        log.info("上传完成, 准备返回.");
        return BaseActionResult.from(result, resultCodeTools);
    }

    @PostMapping("/deleteImage")
    public BaseActionResult<Void> deleteImage(@RequestParam("id") Integer id) {
        log.info("根据图片id删除一张图片.");
        log.info("加载用户信息.");
        UserDetailsBean currentUserDetails = securityTools.getCurrentUserDetails();
        log.info("删除图片.");
        BaseServiceResult<Void> result = imageService.deleteImage(id, currentUserDetails);
        log.info("删除操作完成, 返回结果.");
        return BaseActionResult.from(result, resultCodeTools);
    }

    @PostMapping("/deleteImageByEssayId")
    public BaseActionResult<Void> deleteImageByEssayId(@RequestParam("essayId") Integer essayId) {
        log.info("根据随笔id删除随笔的图片.");
        log.info("加载用户信息.");
        UserDetailsBean currentUserDetails = securityTools.getCurrentUserDetails();
        log.info("删除图片.");
        BaseServiceResult<Void> result = imageService.deleteImageByEssayId(essayId, currentUserDetails);
        log.info("删除完成, 准备返回.");
        return BaseActionResult.from(result, resultCodeTools);
    }

}
