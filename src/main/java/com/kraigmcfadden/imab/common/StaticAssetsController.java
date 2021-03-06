package com.kraigmcfadden.imab.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RestController
public class StaticAssetsController {

    private static final Log log = LogFactory.getLog(StaticAssetsController.class);

    @RequestMapping(path = "/static/js/{fileName}", method = RequestMethod.GET, produces = "text/javascript")
    public ResponseEntity<String> getJavaScriptFile(@PathVariable String fileName) {
        return getFile("js/" + fileName);
    }

    @RequestMapping(path = "/static/css/{fileName}", method = RequestMethod.GET, produces = "text/css")
    public ResponseEntity<String> getCssFile(@PathVariable String fileName) {
        return getFile("css/" + fileName);
    }

    private ResponseEntity<String> getFile(String fileName) {
        log.info(fileName + " requested");
        try {
            Resource resource = new ClassPathResource(fileName);
            InputStream inputStream = resource.getInputStream();
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            String data = new String(bdata, StandardCharsets.UTF_8);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            log.error("Failed to retrieve " + fileName, e);
            return ResponseEntity.badRequest().build();
        }
    }
}
