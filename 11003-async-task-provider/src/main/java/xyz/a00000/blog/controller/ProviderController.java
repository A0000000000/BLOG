package xyz.a00000.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.common.ImageBean;
import xyz.a00000.blog.component.ResultCodeTools;
import xyz.a00000.blog.service.ImageService;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("/api/async/provider")
@Slf4j
@Controller
public class ProviderController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ResultCodeTools resultCodeTools;

    @GetMapping("/getImageById")
    public void getImageById(@RequestParam("id") Integer id, @RequestParam(value = "password", required = false) String password, HttpServletResponse response) {
        log.info("访问图片, 但是不触发浏览器的下载功能.");
        BaseServiceResult<ImageBean> result = imageService.getImageById(id, password);
        try {
            if (result.getCode() == 0) {
                ImageBean bean = result.getData();
                response.setContentType(bean.getType());
                response.getOutputStream().write(bean.getData());
                response.getOutputStream().close();
            } else {
                BaseActionResult<ImageBean> res = BaseActionResult.from(result, resultCodeTools);
                res.setData(null);
                ObjectMapper mapper = new ObjectMapper();
                String jsonResult = mapper.writeValueAsString(res);
                response.setContentType("application/json;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(jsonResult);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/downloadImageById")
    public void downloadImageById(@RequestParam("id") Integer id, @RequestParam(value = "password", required = false) String password, HttpServletResponse response) {
        log.info("下载图片, 触发浏览器的下载功能.");
        BaseServiceResult<ImageBean> result = imageService.getImageById(id, password);
        try {
            if (result.getCode() == 0) {
                ImageBean data = result.getData();
                response.setContentType(data.getType());
                response.setHeader("content-disposition", "attachment;filename=" + data.getFilename());
                response.getOutputStream().write(data.getData());
                response.getOutputStream().close();
            } else {
                BaseActionResult<ImageBean> res = BaseActionResult.from(result, resultCodeTools);
                res.setData(null);
                ObjectMapper mapper = new ObjectMapper();
                String jsonResult = mapper.writeValueAsString(res);
                response.setContentType("application/json;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(jsonResult);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
