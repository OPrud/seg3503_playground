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

    @Test
    void multiples_of_five_return_Buzz() {
        assertEquals("Buzz", Fizzbuzz.fizzbuzz(5));
        assertEquals("Buzz", Fizzbuzz.fizzbuzz(100));
    }

    @Test
    void multiples_of_fifteen_return_FizzBuzz() {
        assertEquals("FizzBuzz", Fizzbuzz.fizzbuzz(15));
        assertEquals("FizzBuzz", Fizzbuzz.fizzbuzz(90));
    }
}