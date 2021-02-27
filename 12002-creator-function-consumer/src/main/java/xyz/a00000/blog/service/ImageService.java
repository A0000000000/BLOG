package xyz.a00000.blog.service;

import org.springframework.web.multipart.MultipartFile;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.orm.Image;

import java.util.List;

public interface ImageService {

    BaseActionResult<List<Image>> uploadImage(MultipartFile[] images, Integer essayId, String password);

    BaseActionResult<Void> deleteImageById(Integer id);

    BaseActionResult<Void> deleteImageByEssayId(Integer essayId);

}
