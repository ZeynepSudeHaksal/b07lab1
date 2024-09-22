public class Polynomial {
    // Field to store the coefficients of the polynomial
    private double[] coeff;

    // No-argument constructor (polynomial set to zero)
    public Polynomial() {
        coeff = new double[] {0.0};
    }

    public Polynomial(double[] coeff){
        this.coeff = coeff;
    }

    public Polynomial add(Polynomial poly) {
        int maxsize = Math.max(this.coeff.length, poly.coeff.length);
        double[] result = new double[maxsize];

        for (int i = 0; i < maxsize; i++) {
            double thiscoeff = 0;
            double polycoeff = 0;
            if (i < this.coeff.length) {
                thiscoeff = this.coeff[i];
            }
            if (i < poly.coeff.length) {
                polycoeff = poly.coeff[i];
            }
            result[i] = thiscoeff + polycoeff;
        }
        return new Polynomial(result);
    }

    public double evaluate(double x){
        double result = 0;
        for (int i = 0; i < this.coeff.length; i++){
            result += coeff[i] * Math.pow(x, i);
        }
        return result;
    }
    public boolean hasRoot(double y) {
        return evaluate(y) == 0;
    }
}