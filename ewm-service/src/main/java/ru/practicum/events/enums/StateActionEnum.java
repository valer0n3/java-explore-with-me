package ru.practicum.events.enums;

import ru.practicum.exceptions.EwmServiceUnsupportedStatusEnum;

public enum StateActionEnum {
    SEND_TO_REVIEW,
    CANCEL_REVIEW,
    PUBLISH_EVENT,
    REJECT_EVENT;

    public static void checkIfUpdatedStateActionEnumIsCorrect(StateActionEnum state) {
        if (state == CANCEL_REVIEW || state == SEND_TO_REVIEW) {
        } else {
            throw new EwmServiceUnsupportedStatusEnum(String.format("State action can only be: %s, or %s", StateActionEnum.CANCEL_REVIEW));
        }
    }
}
