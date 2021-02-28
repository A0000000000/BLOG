package xyz.a00000.blog.service.impl;

import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.a00000.blog.feign.ImageFeign;
import xyz.a00000.blog.service.ImageService;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageFeign imageFeign;

    @Override
    public void getImageById(Integer id, String password, HttpServletResponse response) {
        try {
            log.info("向远程服务请求数据.");
            Response image = imageFeign.getImageById(id, password);
            log.info("将数据写回输出.");
            for (Map.Entry<String, Collection<String>> item : image.headers().entrySet()) {
                for (String value : item.getValue()) {
                    response.setHeader(item.getKey(), value);
                }
            }
            response.getOutputStream().write(image.body().asInputStream().readAllBytes());
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    @Override
    public void downloadImageById(Integer id, String password, HttpServletResponse response) {
        try {
            log.info("向远程服务请求数据.");
            Response image = imageFeign.downloadImageById(id, password);
            log.info("将数据写回输出.");
            image.headers().forEach((key, value) -> value.forEach(item -> response.setHeader(key, item)));
            response.getOutputStream().write(image.body().asInputStream().readAllBytes());
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

}
