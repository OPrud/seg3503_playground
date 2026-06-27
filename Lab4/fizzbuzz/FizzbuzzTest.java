package fizzbuzz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class FizzbuzzTest {

    @Test
    void number_returns_itself() {
        assertEquals("1", Fizzbuzz.fizzbuzz(1));
        assertEquals("2", Fizzbuzz.fizzbuzz(2));
    }

    @Test
    void multiples_of_three_return_Fizz() {
        assertEquals("Fizz", Fizzbuzz.fizzbuzz(3));
        assertEquals("Fizz", Fizzbuzz.fizzbuzz(99));
    }
}