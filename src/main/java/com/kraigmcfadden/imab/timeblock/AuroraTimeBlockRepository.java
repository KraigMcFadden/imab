package com.kraigmcfadden.imab.timeblock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Optional;

public class AuroraTimeBlockRepository implements TimeBlockRepository {

    private static final Log log = LogFactory.getLog(AuroraTimeBlockRepository.class);

    @Override
    public Optional<TimeBlock> getTimeBlock(String id) {
        return Optional.empty();
    }

    @Override
    public List<TimeBlock> getAllTimeBlocksForAccount(String accountId) {
        return null;
    }

    @Override
    public void createTimeBlock(TimeBlock timeBlock) {

    }

    @Override
    public void updateTimeBlock(TimeBlock timeBlock) {

    }

    @Override
    public void deleteTimeBlock(String id) {

    }
}
