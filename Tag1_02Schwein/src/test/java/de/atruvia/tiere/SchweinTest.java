package de.atruvia.tiere;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchweinTest {

    private Schwein objectUnderTest;
    private final String startName = "Nobody";
    private final int startGewicht = 10;
    @BeforeEach
    void init() {
        objectUnderTest = new Schwein();
    }

    @Test
    void ctor_default_objectIsInitialized() {
        assertEquals(startGewicht, objectUnderTest.getGewicht());
        assertEquals(startName, objectUnderTest.getName());
    }
    @Test
    void ctor_withNameParameter_objectIsInitialized() {
        final String validName = "Miss Piggy";
        objectUnderTest = new Schwein(validName);
        assertEquals(startGewicht, objectUnderTest.getGewicht());
        assertEquals(validName, objectUnderTest.getName());
    }
    @Test
    void ctor_wrongNameElsa_throwsIllegalArgumentException() {
        final String invalidName = "Elsa";
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->objectUnderTest = new Schwein(invalidName));
        assertEquals("Häh",ex.getMessage());
    }
    @Test
    void setName_wrongNameElsa_throwsIllegalArgumentException() {
        final String invalidName = "Elsa";
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->objectUnderTest.setName(invalidName));
        assertEquals("Häh",ex.getMessage());
    }
    @Test
    void setName_validName_nameIsSet() {
        final String validName = "Miss Piggy";
        assertDoesNotThrow( () ->objectUnderTest.setName(validName));
        assertEquals(validName,objectUnderTest.getName());
    }
    @Test
    void fuettern_happyDay_weightIncresed() {
       objectUnderTest.fuettern();
       assertEquals(startGewicht + 1, objectUnderTest.getGewicht());
    }

}