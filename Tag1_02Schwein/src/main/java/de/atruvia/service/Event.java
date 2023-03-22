package de.atruvia.service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Event {
    private String id;
    private LocalDateTime timestamp;

    private String payload;

}
