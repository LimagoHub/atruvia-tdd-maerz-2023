package de.atruvia.service;

import de.atruvia.dependency.Dependency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MyServiceUsingDependencyImplTest {

    @InjectMocks
    private MyServiceUsingDependencyImpl objectUnderTest;
    @Mock
    private Dependency dependencyMock;

//    @BeforeEach
//    void init() {
//        dependencyMock = mock(Dependency.class);
//        objectUnderTest = new MyServiceUsingDependencyImpl(dependencyMock);
//    }

    @Test
    void abc_test() {

        //doNothing().when(dependencyMock).foobar(anyString());
        objectUnderTest.abc();
        // Assert
        verify(dependencyMock, times(1)).foo("Mein String");
        verify(dependencyMock, times(1)).foo("Mein anderer String");
    }

    @Test
    void bcd_test() {

        // Recordmode
        when(dependencyMock.bar()).thenReturn(30, 50);

        // Replaymode
        var result = objectUnderTest.bcd();
        // Assert
        assertEquals(90, result);
        verify(dependencyMock, times(2)).bar();
    }

    @Test
    void cde_test() {
        // Arrange

        when(dependencyMock.foobar(anyString())).thenReturn(20);
        //doReturn(20).when(dependencyMock).foobar(anyString());
        // act
        var result = objectUnderTest.cde("hallo");
        // Assert
        assertEquals(2000, result);
        verify(dependencyMock).foobar("HALLO");

    }

    @Test
    void fireEvent_test() {
        Event event = new Event(UUID.randomUUID().toString(), LocalDateTime.now(), "Hallo");

        doAnswer(invocationOnMock -> {
            Event e = invocationOnMock.getArgument(0);
            assertEquals(36, event.getId().length());
            assertNotNull(event.getTimestamp());
            assertEquals("Hallo", event.getPayload());
            return null;
        }).when(dependencyMock).send(any());

        objectUnderTest.fireEvent("Hallo");
        //verify(dependencyMock).send(event);
    }

}