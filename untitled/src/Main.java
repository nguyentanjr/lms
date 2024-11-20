import java.util.*;

public class Main {
    private int numerator;
    private int denominator;

    public Solution() {
        this.numerator = 0;
        this.denominator = 1;
    }

    /**
     * constructor.
     *
     * @param numerator   num
     * @param denominator den
     */
    public Solution(int numerator, int denominator) {
        this.numerator = numerator;
        if (denominator == 0) {
            this.denominator = 1;
        } else {
            this.denominator = denominator;
        }
    }

    public int getNumerator() {
        return numerator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }

    /**
     * gcd.
     *
     * @param a para a.
     * @param b para b.
     * @return gcd(a, b)
     */
    public static int gcd(int a, int b) {
        if (a < 0) {
            a = -a;
        }
        if (b < 0) {
            b = -b;
        }
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    /**
     * reduce.
     *
     * @return reduce
     */
    public Solution reduce() {
        int gcd = gcd(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }
        return this;
    }

    /**
     * add.
     *
     * @param x para.
     * @return add
     */
    public Solution add(Solution x) {
        this.numerator = this.numerator * x.denominator + this.denominator * x.numerator;
        this.denominator = this.denominator * x.denominator;
        return this.reduce();
    }

    /**
     * subtract.
     *
     * @param x para
     * @return subtract
     */
    public Solution subtract(Solution x) {
        this.numerator = this.numerator * x.denominator - this.denominator * x.numerator;
        this.denominator = this.denominator * x.denominator;
        return this.reduce();
    }

    /**
     * multiply.
     *
     * @param x para
     * @return multiply
     */
    public Solution multiply(Solution x) {
        this.numerator = this.numerator * x.numerator;
        this.denominator = this.denominator * x.denominator;
        return this.reduce();
    }

    /**
     * divide.
     *
     * @param x para
     * @return divive
     */
    public Solution divide(Solution x) {
        if (x.numerator == 0) {
            return this;
        }
        this.numerator = this.numerator * x.denominator;
        this.denominator = this.denominator * x.numerator;
        return this.reduce();
    }

    /**
     * equals.
     *
     * @param obj para
     * @return compare
     */
    public boolean equals(Object obj) {
        if (obj instanceof Solution) {
            Solution x = (Solution) obj;
            this.reduce();
            x.reduce();
            return this.numerator == x.numerator && this.denominator == x.denominator;
        }
        return false;
    }

    public static void main(String[] args) {
        int n;
        Scanner sc = new Scanner(System.in);
        long m = sc.nextInt();
        System.out.println(fibonacci(m));
    }
}