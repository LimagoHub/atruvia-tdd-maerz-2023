package de.atruvia;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AbstractGameTest {

    private AbstractGameDummyForTesting objectUnderTest;

    private Writer writerMock;

    private boolean gameover;
    private int counter;

    @BeforeEach
    void init() {
        counter = 0;
        gameover = false;
        writerMock = mock(Writer.class);
        objectUnderTest = new AbstractGameDummyForTesting(writerMock);
    }

    @Test
    void gameloop_test() {

        Mockito.doAnswer(invocationOnMock -> {
            counter ++;
            if(counter == 4) gameover = true;
            return null;
        }).when(writerMock).write(anyString());
        objectUnderTest.gameloop();
        verify(writerMock, times(4)).write("Ich mache einen Zug");
    }

    class AbstractGameDummyForTesting extends AbstractGame {
        public AbstractGameDummyForTesting(Writer writer) {
            super(writer);
        }

        @Override
        public boolean gameover() {
            return gameover;
        }
    }

}