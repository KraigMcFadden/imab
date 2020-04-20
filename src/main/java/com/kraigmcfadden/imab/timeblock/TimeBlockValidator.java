package com.kraigmcfadden.imab.timeblock;

import com.kraigmcfadden.imab.common.ValidationException;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.utils.StringUtils;

import java.time.LocalDate;

@Component
public class TimeBlockValidator {

    public void validateGetTimeBlock(String id) throws ValidationException {
        if (StringUtils.isBlank(id)) {
            throw new ValidationException("Cannot lookup time block with blank id");
        }
    }

    public void validateGetAllTimeBlocksForAccount(String accountId) throws ValidationException {
        if (StringUtils.isBlank(accountId)) {
            throw new ValidationException("Cannot lookup time blocks with blank account id");
        }
    }

    public void validateCreateTimeBlock(String accountId, CreateTimeBlockRequest request) {
        if (StringUtils.isBlank(accountId)) {
            throw new ValidationException("Cannot create a time block with blank account id");
        }
        if (request.getStartDate() == null) {
            throw new ValidationException("Cannot have a null start date");
        }
        if (request.getEndDate() == null) {
            throw new ValidationException("Cannot have a null end date");
        }
        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new ValidationException("Invalid date range - start date is after end date");
        }
    }
}
