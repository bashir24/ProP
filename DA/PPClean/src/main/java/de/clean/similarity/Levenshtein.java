package de.clean.similarity;

/**
 * Levenshtein String similarity
 */
public class Levenshtein implements StringSimilarity {
    public Levenshtein() {
    }

    /**
     * Calculates Levenshtein String similarity for x and y
     * @param x
     * @param y
     * @return Similarity score in range [0,1]
     */
    @Override
    public double compare(String x, String y) {
        double res = 0;
        int m = x.length();
        int n = y.length();
        // BEGIN SOLUTION

        // 1. Matrix D erstellen (m+1) x (n+1)
        int[][] D = new int[m + 1][n + 1];

        // 2. Basisfälle füllen
        for (int i = 0; i <= m; i++) {
            D[i][0] = i; // Löschen von x
        }
        for (int j = 0; j <= n; j++) {
            D[0][j] = j; // Einfügen in x
        }

        // 3. Rekursiv restliche Zellen füllen
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int cost = (x.charAt(i - 1) == y.charAt(j - 1)) ? 0 : 1;
                D[i][j] = Math.min(
                        Math.min(D[i - 1][j] + 1,      // löschen
                                D[i][j - 1] + 1),     // einfügen
                        D[i - 1][j - 1] + cost         // ersetzen
                );
            }
        }

        // 4. Levenshtein-Distanz steht in D[m][n], Ähnlichkeit berechnen
        int dist = D[m][n];
        int maxLen = Math.max(m, n);
        if (maxLen == 0) { // Schutz vor Division durch 0
            res = 1.0;
        } else {
            res = 1.0 - ((double) dist / maxLen);
        }

        // END SOLUTION
        return res;
    }

}
