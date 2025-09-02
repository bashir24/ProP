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





/* Aufgabe 3: Levenshtein Similarity
 * - Misst minimale Änderungen (Einfügen, Löschen, Ersetzen)
 * - Similarity = 1 - (Distanz / max Länge)
 * - Gut für kurze Strings, Tippfehler */


//Wann benutzt man welches Maß?
//Maß	Typischer Einsatz	Warum
//Levenshtein	Kurze Strings, z. B. Namen, Stadt	Erfasst Tippfehler, Buchstaben vertauscht oder ersetzt
//Jaccard	Längere Strings, z. B. Adressen, Beschreibungen	Erfasst Unterschiede in Substrings, Abkürzungen, zusätzliche Wörter


//Erste Zeile = Anzahl der Einfügungen, um leeren String zu y zu machen
//Erste Spalte = Anzahl der Löschungen, um x zu leeren String zu machen


/* Richtung	Operation
Nach unten	Löschen von x
Nach rechts	Einfügen in x
Diagonal	Ersetzen / Übereinstimmung


 Merksatz:

Source = Zeilen (x)
Target = Spalten (y)
Bewegung: nach unten = löschen,
nach rechts = einfügen,
 diagonal = ersetzen oder 0*/

