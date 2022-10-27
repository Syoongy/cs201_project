import java.io.*;
import java.util.*;

public class App {

    private static ChainHashMap<DNASequence, Integer> map = new ChainHashMap<>();
    private static ArrayList<TestEntry> result = new ArrayList<>();
    private static int errorThreshold = 6;
    private static String dataFile = "data10000.txt";
    private static String verifyFile = "verify1000.txt";
    private static int numSequencesBucketNotFound = 0;

    public static void main(String[] args) {
        readData();
        verifyData();
        computeScore();
    }

    public static void readData() {
        try (Scanner sc = new Scanner(new File(dataFile))) {
            while (sc.hasNext()) {
                map.put(new DNASequence(sc.next()), null);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Data File not found");
        }
    }

    public static void verifyData() {
        try (Scanner sc = new Scanner(new File(verifyFile))) {
            while (sc.hasNext()) {
                DNASequence testSequence = new DNASequence(sc.next());
                Map<DNASequence, Integer> bucket = map.getObjectsInSameBucket(testSequence);
                int numSimilar = 0;
                if (bucket != null) {
                    for (DNASequence dnaSequence : bucket.keySet()) {
                        numSimilar += computeSimilarity(dnaSequence, testSequence);
                    }
                    result.add(new TestEntry(testSequence, numSimilar, bucket.size()));
                } else {
                    numSequencesBucketNotFound++;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Verify File not found");
        }
    }

    public static int computeSimilarity(DNASequence x, DNASequence y) {
        String s1 = x.getSequence();
        String s2 = y.getSequence();

        int numCharMisMatched = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                numCharMisMatched++;
            }
        }
        if (numCharMisMatched > errorThreshold) {
            return 0;
        }
        return 1;
    }

    public static void computeScore() {
        double total = 0;
        double score = 0;
        int size = result.size();
        int numTotalSimilarEntries = 0;
        int numTotalEntries = 0;
        String outputFile = "output_" + dataFile + "_" + verifyFile + "_errorThreshold_" + errorThreshold + ".txt";

        try (FileWriter myWriter = new FileWriter(outputFile)) {
            for (TestEntry te : result) {
                System.out.print(te + " = ");
                myWriter.write(te + " = ");
                score = (double) (te.getNumSimilar()) / (te.getNumInSameBucket());
                System.out.println("Similarity Score : " + score);
                myWriter.write("Similarity Score : " + score + "\n");
                numTotalSimilarEntries += te.getNumSimilar();
                numTotalEntries += te.getNumInSameBucket();
                total += score;
            }
            System.out.println();
            System.out.println("-------------------------------------------------------------------");
            System.out.println("Summary of Results for " + dataFile + " and " + verifyFile);
            System.out.println("-------------------------------------------------------------------");
            System.out.println("Total Number of Verify Sequences (bucket found): " + size);
            System.out.println("Total Number of Verify Sequences (bucket not found): " + numSequencesBucketNotFound);
            System.out.println("Error Threshold : " + errorThreshold);
            System.out.print(String.format("Total Similarity Score : %.2f \n", total));
            System.out.print(String.format("Quality Indicator (Max 1.00) : %.2f \n", total / size));
            System.out.println("Total Number of Similar : " + numTotalSimilarEntries);
            System.out.println("Total Number of Sequences (in same Bucket) : " + numTotalEntries);

            myWriter.write("-------------------------------------------------------------------" + "\n");
            myWriter.write("Summary of Results for " + dataFile + " and " + verifyFile + "\n");
            myWriter.write("-------------------------------------------------------------------" + "\n");
            myWriter.write("Total Number of Verify Sequences (bucket found): " + size + "\n");
            myWriter.write("Total Number of Verify Sequences (bucket not found): " + numSequencesBucketNotFound + "\n");
            myWriter.write("Error Threshold : " + errorThreshold + "\n");
            myWriter.write(String.format("Total Similarity Score : %.2f \n", total));
            myWriter.write(String.format("Quality Indicator (Max 1.00) : %.2f \n", total / size));
            myWriter.write("Total Number of Similar : " + numTotalSimilarEntries + "\n");
            myWriter.write("Total Number of Sequences (in same Bucket) : " + numTotalEntries + "\n");

            System.out.println();
            System.out.println("Successfully written to " + outputFile);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}