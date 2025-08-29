package de.clean.similarity;

import de.clean.data.Record;
/**
 * Simple heuristic to compare two Records
 * It compares two Records for equality based only on a single attribute
 */
public class SingleAttributeEquality implements RecordSimilarity {

    int attributeIndex;

    /**
     * @param attributeIndex Position of Record content at which to check for equality
     */
    public SingleAttributeEquality(int attributeIndex) {
        this.attributeIndex = attributeIndex;
    }

    /**
     * @param r1
     * @param r2
     * @return 1 if r1 and r2 are equal at position {@link #attributeIndex}, else 0
     */
    @Override
    public double compare(Record r1, Record r2) {
        double res = 0;
        // BEGIN SOLUTION
        String v1 = r1.getContent().get(attributeIndex);
        String v2 = r2.getContent().get(attributeIndex);

        return v1.equals(v2) ? 1.0 : 0.0;


        // END SOLUTION

    }
}




/* Prüft, ob v1 und v2 genau gleich sind
Wenn gleich → 1.0 (maximale Ähnlichkeit)
Wenn ungleich → 0.0 (keine Ähnlichkeit) */