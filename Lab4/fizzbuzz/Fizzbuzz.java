package fizzbuzz;

public class Fizzbuzz {
    public static String fizzbuzz(int n) {
        if (n % 3 == 0) return "Fizz";
        if (n % 5 == 0) return "Buzz";
        return String.valueOf(n);
    }
}