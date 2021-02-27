package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.orm.Image;
import xyz.a00000.blog.service.ImageService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/creator/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/uploadImage")
    public BaseActionResult<List<Image>> uploadImage(@RequestParam("images") MultipartFile[] images, @RequestParam("essayId") Integer essayId, @RequestParam(value = "password", required = false) String password) {
        log.info("上传图片.");
        BaseActionResult<List<Image>> result = imageService.uploadImage(images, essayId, password);
        log.info("上传完成, 准备返回.");
        return result;
    }

    @DeleteMapping("/deleteImageById/{id}")
    public BaseActionResult<Void> deleteImageById(@PathVariable("id") Integer id) {
        log.info("根据图片id, 删除一张图片.");
        BaseActionResult<Void> result = imageService.deleteImageById(id);
        log.info("删除完成, 准备返回.");
        return result;
    }

    @DeleteMapping("/deleteImageByEssayId/{id}")
    public BaseActionResult<Void> deleteImageByEssayId(@PathVariable("id") Integer essayId) {
        log.info("根据随笔id, 删除随笔下的所有图片.");
        BaseActionResult<Void> result = imageService.deleteImageByEssayId(essayId);
        log.info("删除完成, 准备返回.");
        return result;
    }

}
