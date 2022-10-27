// Group Name (e.g. GXTX) : 
// 
// Instructions  
// 1. You will only submit this file and output_data10000.txt_verify1000.txt.txt in eLearn
// 2. You are only allowed to use the Java Standard Library classes
// 3. Do not modify the variables and methods given except for the implementation of HashCode()
// 4. You can create additional Private variables and methods
// 5. Do not modify any other files to execute the application
// 6. Refer to the project documents write-up and Piazza for clarifications

public class DNASequence {

    private String sequence;

    public DNASequence(String sequence) {
        this.sequence = sequence;
    }

    public String getSequence() {
        return sequence;
    }

    public String toString() {
        return sequence;
    }

    public int hashCode() {
        double[] sequenceAsBinaryArray = new double[16];
        // StringBuilder sequenceAsBinaryBuilder = new StringBuilder();
        // for (char DNAChar : sequence.toCharArray()) {
        // switch (DNAChar) {
        // case 'A':
        // sequenceAsBinaryBuilder.append("00");
        // break;
        // case 'C':
        // sequenceAsBinaryBuilder.append("01");
        // break;
        // case 'G':
        // sequenceAsBinaryBuilder.append("10");
        // break;
        // case 'T':
        // sequenceAsBinaryBuilder.append("11");
        // break;
        // default:
        // break;
        // }
        // }
        for (int i = 0; i < sequence.length(); i++) {
            sequenceAsBinaryArray[i] = (int) sequence.charAt(i);
        }
        // for (double i : sequenceAsBinaryArray) {
        // System.out.print(i);
        // }
        // System.out.println();
        // System.out.println("=================================================");
        // cooleyTukeyFFT(sequenceAsBinaryArray);
        // for (double i : sequenceAsBinaryArray) {
        // System.out.print(i);
        // }
        // double finalVal = 0.0;
        // for (double i : sequenceAsBinaryArray) {
        //     finalVal += i;
        // }

        double[] cos = new double[16];
        // double[] sin = new double[16];
        for (int i = 0; i < 16; i++) {

            // Here cos term is real part which is
            // multiplied into 2ikpie/N
            cos[i] = Math.cos((2 * i * 2196 * Math.PI) / 16);

            // Here sin term is imaginary part which is also
            // multiplied into 2ikpie/N
            // sin[i] = Math.sin((2 * i * 8 * Math.PI) / 16);
        }

        double real = 0;

        // Now let us iterate it till N

        for (int i = 0; i < 16; i++) {

            // real part can be calculated by adding it
            // with newvar and multiplying it with cosine
            // array
            real += sequenceAsBinaryArray[i] * cos[i];

        }
        // System.out.println(real);

        return (int) real ^ 2;
    }

    public static void cooleyTukeyFFT(double[] coefficients) {
        final int size = coefficients.length;
        if (size <= 1)
            return;

        final double[] even = new double[size / 2];
        final double[] odd = new double[size / 2];
        for (int i = 0; i < size; i++) {
            if (i % 2 == 0) {
                even[i / 2] = coefficients[i];
            } else {
                odd[(i - 1) / 2] = coefficients[i];
            }
        }
        cooleyTukeyFFT(even);
        cooleyTukeyFFT(odd);
        for (int k = 0; k < size / 2; k++) {
            double t = Math.cos((2 * Math.PI * k / size) * odd[k]);
            coefficients[k] = even[k] + t;
            coefficients[k + size / 2] = even[k] - t;
        }
    }

}