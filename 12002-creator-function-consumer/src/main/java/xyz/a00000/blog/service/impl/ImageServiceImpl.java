package xyz.a00000.blog.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.orm.Image;
import xyz.a00000.blog.feign.ImageFeign;
import xyz.a00000.blog.service.ImageService;

import java.util.List;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageFeign imageFeign;

    @Override
    public BaseActionResult<List<Image>> uploadImage(MultipartFile[] images, Integer essayId, String password) {
        log.info("向远程服务上传图片.");
        BaseActionResult<List<Image>> result = imageFeign.uploadImage(images, essayId, password);
        log.info("请求完成, 准备返回.");
        return result;
    }

    @Override
    public BaseActionResult<Void> deleteImageById(Integer id) {
        log.info("向远程服务删除一张图片.");
        BaseActionResult<Void> result = imageFeign.deleteImageById(id);
        log.info("请求完成, 准备返回.");
        return result;
    }

    @Override
    public BaseActionResult<Void> deleteImageByEssayId(Integer essayId) {
        log.info("向远程服务请求删除一篇随笔下的全部图片.");
        BaseActionResult<Void> result = imageFeign.deleteImageByEssayId(essayId);
        log.info("请求完成, 准备返回.");
        return result;
    }

}
