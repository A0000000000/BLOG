package xyz.a00000.blog.service;

import org.springframework.web.multipart.MultipartFile;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.orm.Image;
import xyz.a00000.blog.bean.proxy.UserDetailsBean;
import xyz.a00000.blog.mapper.ImageMapper;

public interface ImageService extends BaseService<Image, ImageMapper> {

    BaseServiceResult<Image> uploadImage(MultipartFile image, Integer essayId, String password, UserDetailsBean currentUserDetails);

    BaseServiceResult<Void> deleteImage(Integer id, UserDetailsBean currentUserDetails);

}
