package fizzbuzz;

public class Fizzbuzz {
    public static String fizzbuzz(int n) {
        if (n % 3 == 0) return "Fizz";
        return String.valueOf(n);
    }
}