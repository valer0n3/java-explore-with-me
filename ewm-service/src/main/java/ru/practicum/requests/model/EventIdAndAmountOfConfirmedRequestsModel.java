package ru.practicum.requests.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventIdAndAmountOfConfirmedRequestsModel {
    private Long eventId;
    private Long amountConfirmedRequests;
}
