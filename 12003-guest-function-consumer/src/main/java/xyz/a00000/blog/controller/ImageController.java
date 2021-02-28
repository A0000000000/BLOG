package xyz.a00000.blog.controller;

import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.a00000.blog.service.ImageService;

import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequestMapping("/api/guest/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private HttpServletResponse response;

    @GetMapping("/getImageById")
    public void getImageById(@RequestParam("id") Integer id, @RequestParam(value = "password", required = false) String password) {
        log.info("根据id获取一张图片, 不触发浏览器下载功能.");
        imageService.getImageById(id, password, response);
        log.info("操作完成.");
    }

    @GetMapping("/downloadImageById")
    public void downloadImageById(@RequestParam("id") Integer id, @RequestParam(value = "password", required = false) String password) {
        log.info("根据id获取一张图片, 触发浏览器下载功能.");
        imageService.downloadImageById(id, password, response);
        log.info("操作完成.");
    }

}
