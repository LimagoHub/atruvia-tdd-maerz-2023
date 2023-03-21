package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DateDemoTest {

    @InjectMocks
    private DateDemo objectUnderTest;

    @Spy
    private LocalDateTime time = LocalDateTime.now();

    @Test

    void demo() {
        when(time.getHour()).thenReturn(5);
        objectUnderTest.foo();
        verify(time).getMinute();
    }
}