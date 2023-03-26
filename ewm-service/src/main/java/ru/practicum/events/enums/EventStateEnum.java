package ru.practicum.events.enums;

import ru.practicum.exceptions.EwmServiceUnsupportedStatusEnum;

public enum EventStateEnum {
    PENDING,
    PUBLISHED,
    CANCELED;

    public static EventStateEnum checkIfEventStateEnumIsCorrect(String state) {
        try {
            return EventStateEnum.valueOf(state.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new EwmServiceUnsupportedStatusEnum(String.format("Unknown state: %s", state));
        }
    }
}
