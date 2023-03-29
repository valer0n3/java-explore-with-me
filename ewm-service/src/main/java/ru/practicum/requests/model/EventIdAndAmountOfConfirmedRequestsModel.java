package ru.practicum.requests.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventIdAndAmountOfConfirmedRequestsModel {
    private Long eventId;
    private Long amountConfirmedRequests;
}
