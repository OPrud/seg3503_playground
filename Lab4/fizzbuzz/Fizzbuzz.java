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
        List<String> result = new ArrayList<>();
        for (int i = n; i <= m; i++) {
            result.add(fizzbuzz(i));
        }
        return result;
    }
}