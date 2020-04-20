package com.kraigmcfadden.imab.timeblock;

import java.util.List;
import java.util.Optional;

public interface TimeBlockRepository {

    Optional<TimeBlock> getTimeBlock(String id);
    List<TimeBlock> getAllTimeBlocksForAccount(String accountId);
    void createTimeBlock(TimeBlock timeBlock);
    void updateTimeBlock(TimeBlock timeBlock);
    void deleteTimeBlock(String id);
}
