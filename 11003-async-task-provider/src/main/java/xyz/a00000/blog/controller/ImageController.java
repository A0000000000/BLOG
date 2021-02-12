package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.a00000.blog.component.SecurityTools;

@RestController
@Slf4j
@RequestMapping("/api/async/image")
public class ImageController {

    @Autowired
    private SecurityTools securityTools;


}
