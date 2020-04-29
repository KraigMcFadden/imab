package com.kraigmcfadden.imab.budget;

import com.kraigmcfadden.imab.common.ValidationException;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.utils.StringUtils;

@Component
public class BudgetValidator {

    public void validateCreateBudget(CreateBudgetRequest request) {
        // do nothing - opening balance can be anything
    }

    public void validateGetBudget(String id) throws ValidationException {
        if (StringUtils.isBlank(id)) {
            throw new ValidationException("Cannot lookup budget with blank id");
        }
    }
}
