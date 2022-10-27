public class TestEntry {

    private DNASequence sequence;
    private int numInSameBucket;
    private int numSimilar;

    public int getNumInSameBucket() {
        return numInSameBucket;
    }

    public void setNumInSameBucket(int numInSameBucket) {
        this.numInSameBucket = numInSameBucket;
    }

    public int getNumSimilar() {
        return numSimilar;
    }

    public void setNumSimilar(int numSimilar) {
        this.numSimilar = numSimilar;
    }

    public DNASequence getSequence() {
        return sequence;
    }

    public void setSequence(DNASequence sequence) {
        this.sequence = sequence;
    }

    public TestEntry(DNASequence sequence, int numSimilar, int numInSameBucket) {
        this.sequence = sequence;
        this.numSimilar = numSimilar;
        this.numInSameBucket = numInSameBucket;
    }

    public String toString() {
        return sequence + " -> Similar : " + numSimilar + ", Total(in same Bucket) : " + numInSameBucket;
    }
}