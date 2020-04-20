package com.kraigmcfadden.imab.timeblock;

import com.kraigmcfadden.imab.common.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class TimeBlockWorker {

    private final TimeBlockRepository timeBlockRepository;

    @Autowired
    public TimeBlockWorker(TimeBlockRepository timeBlockRepository) {
        this.timeBlockRepository = timeBlockRepository;
    }

    public TimeBlock createTimeBlock(LocalDate startDate, LocalDate endDate, double openingBalance, String accountId) {
        TimeBlock timeBlock = TimeBlock.newBuilder()
                .withId(UUID.randomUUID().toString())
                .withStartDate(startDate)
                .withEndDate(endDate)
                .withOpeningBalance(openingBalance)
                .withAccountId(accountId)
                .build();
        timeBlockRepository.createTimeBlock(timeBlock);
        return timeBlock;
    }

    public TimeBlock getTimeBlock(String id) throws NotFoundException {
        Optional<TimeBlock> timeBlock = timeBlockRepository.getTimeBlock(id);
        return timeBlock.orElseThrow(() -> { throw new NotFoundException("TimeBlock with id " + id + " not found in database"); });
    }

    public List<TimeBlock> getAllTimeBlocksForAccount(String accountId) {
        return timeBlockRepository.getAllTimeBlocksForAccount(accountId);
    }
}
