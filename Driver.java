public class Driver {
    public static void main(String[] args) {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3.0));  // Pass as double

        double[] c1 = {6, 0, 0, 5}; // 5x^3 + 6
        Polynomial p1 = new Polynomial(c1);

        double[] c2 = {0, -2, 0, 0, -9}; // -9x^4 - 2x
        Polynomial p2 = new Polynomial(c2);

        // Add p1 and p2
        Polynomial s = p1.add(p2);

        // Evaluate s at x = 0.1
        System.out.println("s(0.1) = " + s.evaluate(0.1));

        // Check if 1 is a root of s
        if (s.hasRoot(1.0)) {  // Pass as double
            System.out.println("1 is a root of s");
        } else {
            System.out.println("1 is not a root of s");
        }
    }
}
