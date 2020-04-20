package com.kraigmcfadden.imab.account;

import com.kraigmcfadden.imab.common.ValidationException;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.utils.StringUtils;

@Component
public class AccountValidator {

    public void validateGetAccount(String id) throws ValidationException {
        if (StringUtils.isBlank(id)) {
            throw new ValidationException("Cannot lookup account with blank id");
        }
    }
}
