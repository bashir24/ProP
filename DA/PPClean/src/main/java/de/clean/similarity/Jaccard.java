package de.clean.similarity;

import java.util.HashSet;
import java.util.Set;

/**
 * Jaccard String similarity
 */
public class Jaccard implements StringSimilarity {

    int n;

    /**
     * @param n Length of substrings
     */
    public Jaccard(int n) {
        this.n = n;
    }

    /**
     * Calculates Jaccard String similarity for x and y, using ngrams of length {@link #n}
     * @param x
     * @param y
     * @return Similarity score in range [0,1]
     */
    @Override
    public double compare(String x, String y) {
        double res = 0;
        Set<String> ngramsX = new HashSet<>();
        Set<String> ngramsY = new HashSet<>();
        // BEGIN SOLUTION

        if (x != null && y != null) {
            // Generiere n-Gramme für x
            for (int i = 0; i <= x.length() - n; i++) {
                ngramsX.add(x.substring(i, i + n));
            }

            // Generiere n-Gramme für y
            for (int i = 0; i <= y.length() - n; i++) {
                ngramsY.add(y.substring(i, i + n));
            }

            // Berechne Schnittmenge
            Set<String> intersection = new HashSet<>(ngramsX);
            intersection.retainAll(ngramsY);

            // Berechne Vereinigung
            Set<String> union = new HashSet<>(ngramsX);
            union.addAll(ngramsY);

            // Jaccard-Ähnlichkeit
            if (!union.isEmpty()) {
                res = (double) intersection.size() / union.size();
            }
        }

        // END SOLUTION
        return res;
    }

}


/*Aufgabe 2: Jaccard Similarity

Strings in n-Gramme zerlegen
Ähnlichkeit:
(𝐴 ∩ 𝐵)/(𝐴∪𝐵)
Gut für: längere Strings, Abkürzungen, Schreibvarianten
z.B Adresse
 */