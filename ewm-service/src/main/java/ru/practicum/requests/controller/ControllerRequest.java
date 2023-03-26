package ru.practicum.requests.controller;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.requests.dto.ParticipationRequestDto;
import ru.practicum.requests.service.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
@AllArgsConstructor
@Validated
public class ControllerRequest {
    private final RequestService requestService;

    @GetMapping
    public List<ParticipationRequestDto> getAllRequestsOfcurrentUser(@PathVariable("userId") @Min(0) Long userId) {
        return requestService.getAllRequestsOfcurrentUser(userId);
    }

    @PostMapping
    public ParticipationRequestDto saveNewRequest(@PathVariable("userId") @Min(0) Long userId,
                                                  @Valid @RequestBody ParticipationRequestDto participationRequestDto) {
        return requestService.saveNewRequest(userId, participationRequestDto);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto updateRequestStatusToCancel(
            @PathVariable("userId") @Min(0) Long userId,
            @PathVariable("requestId") @Min(0) Long requestId,
            @Valid @RequestBody ParticipationRequestDto participationRequestDto) {
        return requestService.updateRequestStatusToCancel(userId, requestId, participationRequestDto);
    }
}
