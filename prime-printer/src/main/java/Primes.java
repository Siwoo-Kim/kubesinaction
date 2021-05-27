import java.util.Arrays;

public class Primes {
    private static final int MAX = 100;
    
    public static void main(String[] args) {
        boolean[] primes = new boolean[MAX+1];
        Arrays.fill(primes, true);
        primes[0] = primes[1] = false;
        for (int i=2; i<=MAX; i++) 
            if (primes[i]) {
                for (int j=i+i; j<=MAX; j+=i)
                    primes[j] = false;
            }
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<=MAX; i++)
            sb.append(i).append(": ")
                .append(!primes[i] ? "not ": "")
                .append("prime\n");
        System.out.println(sb.toString());
    }
}
