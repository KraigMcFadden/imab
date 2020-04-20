package com.kraigmcfadden.imab.timeblock;

import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryTimeBlockRepository implements TimeBlockRepository {

    private final Map<String, TimeBlock> database = Maps.newHashMap();

    @Override
    public Optional<TimeBlock> getTimeBlock(String id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<TimeBlock> getAllTimeBlocksForAccount(String accountId) {
        return database.values().stream()
                .filter(timeBlock -> accountId.equals(timeBlock.getAccountId()))
                .collect(Collectors.toList());
    }

    @Override
    public void createTimeBlock(TimeBlock timeBlock) {
        database.put(timeBlock.getId(), timeBlock);
    }

    @Override
    public void updateTimeBlock(TimeBlock timeBlock) {
        database.put(timeBlock.getId(), timeBlock);
    }

    @Override
    public void deleteTimeBlock(String id) {
        database.remove(id);
    }
}
