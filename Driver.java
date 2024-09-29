import java.io.File;

public class Driver {
    public static void main(String[] args) {
        // Test 1: Creating a polynomial from coefficients and exponents
        double[] coefficients1 = {6, -2, 5};  // Represents 6 - 2x + 5x^3
        int[] exponents1 = {0, 1, 3};
        Polynomial poly1 = new Polynomial(coefficients1, exponents1);
        System.out.println("Polynomial 1: " + polyToString(poly1));

        // Test 2: Creating a polynomial from a file
        File file = new File("polynomial.txt");
        Polynomial poly2 = new Polynomial(file);
        System.out.println("Polynomial 2 from file: " + polyToString(poly2));

        // Test 3: Adding two polynomials
        Polynomial sum = poly1.add(poly2);
        System.out.println("Sum: " + polyToString(sum));

        // Test 4: Multiplying two polynomials
        Polynomial product = poly1.multiply(poly2);
        System.out.println("Product: " + polyToString(product));

        // Test 5: Evaluating a polynomial
        double valueToEvaluate = 2.0;
        double evaluationResult = poly1.evaluate(valueToEvaluate);
        System.out.println("Evaluation of Polynomial 1 at x = " + valueToEvaluate + ": " + evaluationResult);

        // Test 6: Checking for a root
        boolean hasRoot = poly1.hasRoot(1.0);
        System.out.println("Does Polynomial 1 have a root at x = 1.0? " + hasRoot);

        // Test 7: Saving polynomial to a file
        poly1.saveToFile("outputPolynomial.txt");
    }

    // Helper method to convert polynomial to a string representation
    private static String polyToString(Polynomial poly) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < poly.coeff.length; i++) {
            if (i > 0) {
                result.append(poly.coeff[i] >= 0 ? "+" : "");
            }
            if (poly.exponents[i] == 0) {
                result.append(poly.coeff[i]);
            } else if (poly.coeff[i] == 1) {
                result.append(poly.coeff[i]).append("x");
            } else {
                result.append(poly.coeff[i]).append("x").append(poly.exponents[i]);
            }
        }
        return result.toString();
    }
}
