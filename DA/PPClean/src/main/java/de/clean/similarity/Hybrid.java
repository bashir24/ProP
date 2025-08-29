package de.clean.similarity;

import java.util.List;
import de.clean.data.Record;

/**
 * Record similarity for comparing two Records attribute by attribute either with {@link Levenshtein} or {@link Jaccard}
 */
public class Hybrid implements RecordSimilarity {

    private List<String> policies;
    private final int JACCARD_N = 3;
    private StringSimilarity jaccard;
    private StringSimilarity levenshtein;

    /**
     * @param policies List of comparison policies, write "L" for {@link Levenshtein},
     *                 "J" {@link Jaccard}, and null to skip an attribute.
     *                 The policies are applied in order of attributes (e.g., first policy to first attribute)
     */
    public Hybrid(List<String> policies) {
        this.policies = policies;
        this.jaccard = new Jaccard(JACCARD_N);
        this.levenshtein = new Levenshtein();
    }

    /**
     * Compares two Records attribute by attribute according to {@link #policies}.
     * For Jaccard similarity, a default window size of {@link #JACCARD_N} is used
     * @param r1
     * @param r2
     * @return Similarity score in range [0,1] (1=same, 0=very different)
     */
    @Override
    public double compare(Record r1, Record r2) {
        double res = 0;
        int count = 0; // Zählt die verglichenen Attribute
        // BEGIN SOLUTION

        List<String> values1 = r1.getContent();
        List<String> values2 = r2.getContent();

        int len = Math.min(values1.size(), values2.size());

        for (int i = 0; i < len && i < policies.size(); i++) {
            String policy = policies.get(i);
            String v1 = values1.get(i);
            String v2 = values2.get(i);

            if (policy == null) {
                continue; // Attribut ignorieren
            } else if (policy.equalsIgnoreCase("L")) {
                res += levenshtein.compare(v1, v2);
                count++;
            } else if (policy.equalsIgnoreCase("J")) {
                res += jaccard.compare(v1, v2);
                count++;
            }
        }

        if (count > 0) {
            res /= count; // Durchschnitt berechnen
        } else {
            res = 0; // Keine Attribute verglichen
        }

        // END SOLUTION
        return res;
    }

}

