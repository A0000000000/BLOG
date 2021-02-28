package xyz.a00000.blog.service;

import javax.servlet.http.HttpServletResponse;

public interface ImageService {

    void getImageById(Integer id, String password, HttpServletResponse response);

    void downloadImageById(Integer id, String password, HttpServletResponse response);

}
