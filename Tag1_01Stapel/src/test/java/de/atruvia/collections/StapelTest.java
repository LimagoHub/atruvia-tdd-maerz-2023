package de.atruvia.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StapelTest {

    /*
        Triple A-Principle
        (A) rrange Vorbereitung. Bringe das Objekt in den Zustand der getestet werden soll
        (A) ction. Method under Test ausführen
        (A) Assertion. Prüfen ob unsere Erwartung eingetreten ist
     */
    private Stapel objectUnderTest;

    @BeforeEach
    void setUp() {
        objectUnderTest = new Stapel();
    }

    @DisplayName("should return true when isEmpty called on emtpty Stack")
    @Test
    void isEmpty_leererStapel_returnsTrue() {
        // Arrange


        // Act
        var result = objectUnderTest.isEmpty();

        // Assertion
        assertTrue(result);
    }
    @Test
    void isEmpty_nichtLeererStapel_returnsFalse() throws Exception{
        // Arrange

        objectUnderTest.push(10);
        // Act
        var result = objectUnderTest.isEmpty();

        // Assertion
        assertFalse(result);
    }
    @Test
    void push_fillUpToLimit_noExceptionIsThrown() throws Exception{
        // Arrange Act + Assertion
        fillUpToLimit();
    }



    @Test
    void push_Overflow_throwsStapelException() throws Exception{
        // Arrange Act + Assertion
        fillUpToLimit();
        StapelException ex = assertThrows(StapelException.class, ()->objectUnderTest.push(10));
        assertEquals("Overflow", ex.getMessage());

    }

    private void fillUpToLimit() {
        for (int i = 0; i < 10; i++) {
            assertDoesNotThrow(()->objectUnderTest.push(10));
        }
    }

}