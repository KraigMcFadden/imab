package com.kraigmcfadden.imab.controllers;

import com.google.common.base.Throwables;
import com.kraigmcfadden.imab.model.Account;
import com.kraigmcfadden.imab.services.AccountDisplayService;
import com.kraigmcfadden.imab.services.AccountManagementService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

    private static final Log log = LogFactory.getLog(BasicController.class);

    private static final String INJECTION_TOKEN = "${inject}";
    private static final String HTML_TEMPLATE = "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <title>IMAB</title>\n" +
            "    <meta charset=\"utf-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "    <link rel = \"stylesheet\" type = \"text/css\" href = \"style.css\" />\n" +
            "</head>\n" +
            "<body>\n" +
            INJECTION_TOKEN + "\n" +
            "</body>\n" +
            "</html>";

    private final AccountDisplayService accountDisplayService;
    private final AccountManagementService accountManagementService;

    @Autowired
    public BasicController(AccountDisplayService accountDisplayService, AccountManagementService accountManagementService) {
        this.accountDisplayService = accountDisplayService;
        this.accountManagementService = accountManagementService;
    }

    @RequestMapping(value = "/account", method = RequestMethod.GET, produces = "text/html")
    public ResponseEntity<String> showMeDefaultAccount() {
        log.info("Incoming request");
        try {
            Account defaultAccount = accountManagementService.createDefaultAccount("Kraig", 5077.96);
            return ResponseEntity.ok(
                    HTML_TEMPLATE.replace(INJECTION_TOKEN, accountDisplayService.accountToHtmlString(defaultAccount))
            );
        } catch (Exception e) {
            log.error("Failed to show default account", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Throwables.getStackTraceAsString(e));
        }
    }
}
