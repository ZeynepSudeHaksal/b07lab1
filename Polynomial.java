import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Polynomial {
    // Field to store the coefficients of the polynomial
    protected double[] coeff;
    protected int[] exponents;

    // No-argument constructor (polynomial set to zero)
    public Polynomial() {
        coeff = new double[] {0.0};
        exponents = new int[] {0};
    }

    public Polynomial(double[] coeff, int[] exponents){
        this.coeff = coeff;
        this.exponents = exponents;
    }

    public Polynomial add(Polynomial poly) {
        int maxSize = this.coeff.length + poly.coeff.length;
        double[] resultCoefficients = new double[maxSize];
        int[] resultExponents = new int[maxSize];
    
        int i = 0, j = 0, k = 0;
    
        while (i < this.coeff.length && j < poly.coeff.length) {
            if (this.exponents[i] == poly.exponents[j]) {
                resultExponents[k] = this.exponents[i];
                resultCoefficients[k] = this.coeff[i] + poly.coeff[j];
                i++;
                j++;
                k++;
            } else if (this.exponents[i] < poly.exponents[j]) {
                resultExponents[k] = this.exponents[i];
                resultCoefficients[k] = this.coeff[i];
                i++;
                k++;
            } else {
                resultExponents[k] = poly.exponents[j];
                resultCoefficients[k] = poly.coeff[j];
                j++;
                k++;
            }
        }
    
        // Copy remaining terms from the first polynomial, if any
        while (i < this.coeff.length) {
            resultExponents[k] = this.exponents[i];
            resultCoefficients[k] = this.coeff[i];
            i++;
            k++;
        }
    
        // Copy remaining terms from the second polynomial, if any
        while (j < poly.coeff.length) {
            resultExponents[k] = poly.exponents[j];
            resultCoefficients[k] = poly.coeff[j];
            j++;
            k++;
        }
    
        // Trim the result arrays to the correct size
        double[] finalCoefficients = new double[k];
        int[] finalExponents = new int[k];
        System.arraycopy(resultCoefficients, 0, finalCoefficients, 0, k);
        System.arraycopy(resultExponents, 0, finalExponents, 0, k);
    
        return new Polynomial(finalCoefficients, finalExponents);
    }
    

    public double evaluate(double x) {
        double result = 0.0;
        for (int i = 0; i < this.coeff.length; i++) {
            result += this.coeff[i] * Math.pow(x, this.exponents[i]);
        }
        return result;
    }
    public boolean hasRoot(double y) {
        return evaluate(y) == 0;
    }

    public Polynomial multiply(Polynomial poly) {
        // Result arrays with maximum possible size
        double[] tempCoeffs = new double[this.coeff.length * poly.coeff.length];
        int[] tempExponents = new int[this.coeff.length * poly.coeff.length];
        int k = 0; // Current index for storing results
    
        // Loop through each coefficient in both polynomials
        for (int i = 0; i < this.coeff.length; i++) {
            for (int j = 0; j < poly.coeff.length; j++) {
                // Calculate new coefficient and exponent
                double coeffProduct = this.coeff[i] * poly.coeff[j];
                int exponentSum = this.exponents[i] + poly.exponents[j];
    
                // Check if the exponent already exists
                boolean found = false;
                for (int m = 0; m < k; m++) {
                    if (tempExponents[m] == exponentSum) {
                        tempCoeffs[m] += coeffProduct; // Add to existing coefficient
                        found = true;
                        break;
                    }
                }
    
                // If this exponent is new, add it to the result
                if (!found) {
                    tempExponents[k] = exponentSum;
                    tempCoeffs[k] = coeffProduct;
                    k++;
                }
            }
        }
    
        // Trim the arrays to the correct size based on k
        double[] finalCoeffs = new double[k];
        int[] finalExponents = new int[k];
        System.arraycopy(tempCoeffs, 0, finalCoeffs, 0, k);
        System.arraycopy(tempExponents, 0, finalExponents, 0, k);
    
        return new Polynomial(finalCoeffs, finalExponents);
    }
    
    
    
    public Polynomial(File file) {
        try {
            // Try reading the file using Scanner
            Scanner scanner = new Scanner(file);
            if (scanner.hasNextLine()) {
                String polyString = scanner.nextLine();
                parsePolynomialString(polyString);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            // Handle the case where the file is not found
            System.err.println("File not found: " + e.getMessage());
            // Initialize with a zero polynomial if the file is not found
            this.coeff = new double[]{0.0};
            this.exponents = new int[]{0};
        }
    }
    private void parsePolynomialString(String polyString) {
        // Use a list to dynamically store coefficients and exponents
        ArrayList<Double> coeffList = new ArrayList<>();
        ArrayList<Integer> expList = new ArrayList<>();

        // Split the string by terms (e.g., "5", "-3x2", "+7x8")
        String[] terms = polyString.split("(?=[+-])");  // Splits on + or - but keeps the sign

        // Process each term
        for (String term : terms) {
            double coefficient;
            int exponent;

            // If the term has an "x" in it
            if (term.contains("x")) {
                // Split by 'x' to separate coefficient and exponent
                String[] parts = term.split("x");

                // Coefficient is the part before "x" (if it's empty, it's 1 or -1)
                if (parts[0].isEmpty() || parts[0].equals("+")) {
                    coefficient = 1.0;
                } else if (parts[0].equals("-")) {
                    coefficient = -1.0;
                } else {
                    coefficient = Double.parseDouble(parts[0]);
                }

                // Exponent is the part after "x" (if absent, it's 1)
                if (parts.length > 1 && !parts[1].isEmpty()) {
                    exponent = Integer.parseInt(parts[1]);
                } else {
                    exponent = 1;  // If no exponent is given (like in "x"), it's x^1
                }
            } else {
                // If the term does not contain "x", it's a constant term
                coefficient = Double.parseDouble(term);
                exponent = 0;  // Constant term is x^0
            }

            // Add the parsed coefficient and exponent to the lists
            coeffList.add(coefficient);
            expList.add(exponent);
        }

        // Convert the lists to arrays
        this.coeff = new double[coeffList.size()];
        this.exponents = new int[expList.size()];
        for (int i = 0; i < coeffList.size(); i++) {
            this.coeff[i] = coeffList.get(i);
            this.exponents[i] = expList.get(i);
        }
    }
    public void saveToFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            StringBuilder polynomialString = new StringBuilder();
            for (int i = 0; i < coeff.length; i++) {
                if (i > 0) {
                    if (coeff[i] >= 0) {
                        polynomialString.append("+");
                    }
                }
                if (exponents[i] == 0) {
                    polynomialString.append(coeff[i]);
                } else if (exponents[i] == 1) {
                    polynomialString.append(coeff[i]).append("x");
                } else {
                    polynomialString.append(coeff[i]).append("x").append(exponents[i]);
                }
            }
            writer.println(polynomialString.toString());
            System.out.println("Polynomial saved to " + fileName);
        } catch (FileNotFoundException e) {
            System.err.println("Error saving to file: " + e.getMessage());
        }
    }
}