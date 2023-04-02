package ru.practicum.constants;

import java.time.LocalDateTime;

public final class ProjectConstants {
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final LocalDateTime DEFAULT_START_DATE = LocalDateTime.of(2000, 01, 01, 00, 00, 00);
    public static final LocalDateTime DEFAULT_END_DATE = LocalDateTime.of(2099, 01, 01, 01, 00, 00);

    private ProjectConstants() {
    }
}

