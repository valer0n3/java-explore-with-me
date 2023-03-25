package ru.practicum.events.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.events.enums.RequestStatusEnum;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventRequestStatusUpdateRequestDto {
    private List<Integer> requestIds;
    private RequestStatusEnum status;
}