package ru.practicum.comments.conroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.comments.dto.ReturnCommentDto;

import javax.validation.constraints.Min;
import java.util.List;

public class PublicCommentService {
    @GetMapping("/event/{eventId}/user/{userId}")
    public List<ReturnCommentDto> getAllCommentsForCurrentEvent(
            @PathVariable("eventId") Long eventId,
            @PathVariable("userId") Long userId,
            @RequestParam(name = "from", required = false, defaultValue = "0") @Min(0) int from,
            @RequestParam(name = "size", required = false, defaultValue = "10") @Min(1) int size) {
        return null;
    }
}
