package ru.practicum.enums;

import ru.practicum.exceptions.EwmServiceUnsupportedStatusEnum;

public enum StateActionEnum {
    SEND_TO_REVIEW,
    CANCEL_REVIEW,
    PUBLISH_EVENT,
    REJECT_EVENT;

    public static void checkIfUpdatedStateActionIsCancelOrSend(StateActionEnum state) {
        if (state != CANCEL_REVIEW && state != SEND_TO_REVIEW) {
            throw new EwmServiceUnsupportedStatusEnum(String.format("State action can only be: %s, or %s", StateActionEnum.CANCEL_REVIEW));
        }
    }

    public static void checkIfUpdatedStateActionIsPublishOrRejectEvent(StateActionEnum state) {
        if (state != PUBLISH_EVENT && state != REJECT_EVENT) {
            throw new EwmServiceUnsupportedStatusEnum(String.format("State action can only be: %s, or %s", StateActionEnum.CANCEL_REVIEW));
        }
    }
}
