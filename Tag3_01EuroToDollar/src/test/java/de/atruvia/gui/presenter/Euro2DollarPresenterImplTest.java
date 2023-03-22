package de.atruvia.gui.presenter;

import de.atruvia.gui.Euro2DollarRechnerView;
import de.atruvia.model.Euro2DollarRechner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
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
}