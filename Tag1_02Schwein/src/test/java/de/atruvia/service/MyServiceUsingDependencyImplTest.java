package de.atruvia.service;

import de.atruvia.dependency.Dependency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
        objectUnderTest.abc();
        // Assert
        verify(dependencyMock, times(1)).foo("Mein String");
        verify(dependencyMock, times(1)).foo("Mein anderer String");
    }

    @Test
    void bcd_test() {

        // Recordmode
        when(dependencyMock.bar())
                .thenReturn(30, 50)
                ;

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
        // act
        var result = objectUnderTest.cde("hallo");
        // Assert
        assertEquals(2000, result);
        verify(dependencyMock).foobar("HALLO");

    }

}