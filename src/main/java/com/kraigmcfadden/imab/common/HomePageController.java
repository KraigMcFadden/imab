package com.kraigmcfadden.imab.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RestController
public class HomePageController {

    private static final Log log = LogFactory.getLog(HomePageController.class);

    private final String HOME_PAGE_HTML;

    public HomePageController() {
        try {
            Resource resource = new ClassPathResource("index.html");
            InputStream inputStream = resource.getInputStream();
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            this.HOME_PAGE_HTML = new String(bdata, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't find homepage HTML file");
        }
    }

    @RequestMapping(path = "/", method = RequestMethod.GET, produces = "text/html")
    public ResponseEntity<String> getHomePage() {
        log.info("Home page requested");
        return ResponseEntity.ok(HOME_PAGE_HTML);
    }
}
