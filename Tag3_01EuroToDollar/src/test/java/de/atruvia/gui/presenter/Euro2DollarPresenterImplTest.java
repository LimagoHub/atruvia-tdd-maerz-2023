package de.atruvia.gui.presenter;

import de.atruvia.gui.Euro2DollarRechnerView;
import de.atruvia.model.Euro2DollarRechner;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
class Euro2DollarPresenterImplTest {

    @InjectMocks
    private Euro2DollarPresenterImpl objectUnderTest;

    @Mock
    private Euro2DollarRechnerView viewMock;

    @Mock
    private Euro2DollarRechner modelMock;

    @Test
    void onBeenden_happyDay_maskClosed() {
        objectUnderTest.onBeenden();
        verify(viewMock).close();
    }

    @Test
    void onPopulateItems_happyDay_maskInitialized() {
        objectUnderTest.onPopulateItems();
        verify(viewMock).setEuro("0");
        verify(viewMock).setDollar("0");
        verify(viewMock).setRechnenEnabled(true);
    }

    @Nested
    class onRechnen {
        @Test
        void onRechnen_nullValueInEuroField_errorMessageInDollarField() {

            // Arrange
            when(viewMock.getEuro()).thenReturn(null);

            objectUnderTest.onRechnen();
            // Assert
            verify(viewMock).setDollar("Eingabe erforderlich");
        }

        @Test
        void onRechnen_NaNValueInEuroField_errorMessageInDollarField() {

            // Arrange
            when(viewMock.getEuro()).thenReturn("Not a Number");

            objectUnderTest.onRechnen();
            // Assert
            verify(viewMock).setDollar("Keine Zahl");
        }

        @Test
        void onRechnen_unexpectedRuntimeExceptionInUnderlyingService_errorMessageInDollarField() {

            // Arrange
            when(viewMock.getEuro()).thenReturn("10");
            when(modelMock.calculateEuro2Dollar(anyDouble())).thenThrow(ArrayIndexOutOfBoundsException.class);

            objectUnderTest.onRechnen();
            // Assert
            verify(viewMock).setDollar("interner Fehler");
        }


        @Test
        void onRechnen_HappyDay_ResultInDollarFiled() {

            // Arrange
            when(viewMock.getEuro()).thenReturn("10");
            when(modelMock.calculateEuro2Dollar(anyDouble())).thenReturn(4711.0);

            objectUnderTest.onRechnen();
            // Assert
            verify(modelMock).calculateEuro2Dollar(10d);
            verify(viewMock).setDollar("4711,00");
        }
    }
    @ParameterizedTest
    @CsvSource({",false","herbert,false","10,true"})
    void updateRechnenActionState(String euroValue, boolean expectedState) {
        when(viewMock.getEuro()).thenReturn(euroValue);
        objectUnderTest.updateRechnenActionState();
        verify(viewMock).setRechnenEnabled(expectedState);
    }
}