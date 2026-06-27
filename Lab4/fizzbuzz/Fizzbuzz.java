package fizzbuzz;

import java.util.ArrayList;
import java.util.List;

public class Fizzbuzz {
    public static String fizzbuzz(int n) {
        if (n % 3 == 0 && n % 5 == 0) return "FizzBuzz";
        if (n % 3 == 0) return "Fizz";
        if (n % 5 == 0) return "Buzz";
        return String.valueOf(n);
    }

    public static List<String> fizzbuzz(int n, int m) {
        return new ArrayList<>(); // not implemented yet
    }
}