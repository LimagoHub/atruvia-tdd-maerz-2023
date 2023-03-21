package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ComputerPlayerTest {

    @InjectMocks
    private ComputerPlayer objectUnderTest;

    @ParameterizedTest
    @CsvSource({"20,3","21,1","22,1","23,2"})
    void doTurn_StonesMod4Gleich0_returns3(int stones, int turn) {
        assertEquals(turn, objectUnderTest.doTurn(stones));
    }


}