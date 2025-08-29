package de.clean.duplicatedetection;

import de.clean.Helper;
import de.clean.data.Duplicate;
import de.clean.data.Table;
import de.clean.data.Record;
import de.clean.similarity.RecordSimilarity;

import java.util.*;

public class LSHDetection implements DuplicateDetection {

    // Hashing
    int HASH_BASE = 17;
    int HASH_PRIME = 19;
    // Tokenization
    int tokenSize;
    List<String> tokenUniverse;
    boolean[][] tokenMatrix;
    // MinHashing
    int numMinHashs;
    int[][] signatureMatrix;
    // Locality Sensitive Hashing
    int numBands;
    ArrayList<Hashtable<Integer, List<Integer>>> LSH;
    // Duplicate Detection
    double threshold;

    /**
     * @param tokenSize Number of characters per token
     * @param numMinHashs Number of min hashes
     * @param numBands Number of bands
     * @param threshold Similarity threshold between 0 and 1 to use for filtering duplicates
     */
    public LSHDetection(int tokenSize, int numMinHashs, int numBands, double threshold) {
        if (numMinHashs % numBands != 0) {
            throw new IllegalArgumentException("numMinHashs needs to be divisible by numBands");
        }
        this.tokenSize = tokenSize;
        this.numMinHashs = numMinHashs;
        this.numBands = numBands;
        this.threshold = threshold;
    }

    /**
     * Calculates {@link LSHDetection#tokenUniverse}: a list of all tokens in the entire table
     * and {@link LSHDetection#tokenMatrix}: a boolean matrix with as many rows as there are tokens in
     * the tokenUniverse and as many columns as there are records in the table. A true boolean value in
     * cell (i, j) means that the i-th token appears in the j-th record.
     * The size of tokens is determined by {@link LSHDetection#tokenSize}.
     * @param table Table to use to calculate tokens
     */
    private void calculateTokens(Table table) {
        // BEGIN SOLUTION
        tokenUniverse = new ArrayList<>();
        List<Record> records = table.getData();
        List<Set<String>> recordTokens = new ArrayList<>();

        for (Record record : records) {
            String recordString = record.toString().replaceAll("\\s", ""); // Leerzeichen entfernen
            Set<String> tokens = new HashSet<>();
            for (int i = 0; i <= recordString.length() - tokenSize; i++) {
                tokens.add(recordString.substring(i, i + tokenSize));
            }
            recordTokens.add(tokens);
            for (String token : tokens) {
                if (!tokenUniverse.contains(token)) {
                    tokenUniverse.add(token);
                }
            }
        }

        // tokenMatrix initialisieren
        tokenMatrix = new boolean[tokenUniverse.size()][records.size()];
        for (int i = 0; i < tokenUniverse.size(); i++) {
            String token = tokenUniverse.get(i);
            for (int j = 0; j < records.size(); j++) {
                tokenMatrix[i][j] = recordTokens.get(j).contains(token);
            }
        }
        // END SOLUTION
    }


    /**
     * Calculates {@link LSHDetection#signatureMatrix}: a matrix with {@link LSHDetection#numMinHashs} many rows
     * and as many columns as there are records in the table.
     * An integer value k at cell (i,j) says that for the i-th permutation of the {@link LSHDetection#tokenMatrix}
     * and for the j-th record in the table, a token of record j is at row k and rows 0 to k-1 have no tokens of record j.
     * @param table Table used to calculate min hashes
     */
    private void calculateMinHashes(Table table) {
        // BEGIN SOLUTION
        int numRecords = table.getData().size();
        signatureMatrix = new int[numMinHashs][numRecords];

        boolean[][] tempMatrix = new boolean[tokenMatrix.length][numRecords];

        for (int hashRow = 0; hashRow < numMinHashs; hashRow++) {
            // erste Reihe: originale tokenMatrix
            if (hashRow == 0) {
                for (int i = 0; i < tokenMatrix.length; i++) {
                    for (int j = 0; j < numRecords; j++) {
                        if (tokenMatrix[i][j]) {
                            signatureMatrix[hashRow][j] = i;
                            break;
                        }
                    }
                }
            } else {
                // permutiere Reihen
                for (int i = 0; i < tokenMatrix.length; i++) {
                    for (int j = 0; j < numRecords; j++) {
                        tempMatrix[i][j] = tokenMatrix[i][j];
                    }
                }
                Helper.shuffleMatrixRows(tempMatrix);

                for (int j = 0; j < numRecords; j++) {
                    for (int i = 0; i < tempMatrix.length; i++) {
                        if (tempMatrix[i][j]) {
                            signatureMatrix[hashRow][j] = i;
                            break;
                        }
                    }
                }
            }
        }
        // END SOLUTION
    }


    private int hash(int[] band) {
        int hash = this.HASH_BASE;
        for (int i : band) {
            hash = hash * this.HASH_PRIME + i;
        }
        return hash;
    }

    /**
     * Calculates a hashtable for every band and adds it to {@link LSHDetection#LSH}. Uses {@link LSHDetection#hash(int[])}
     * to hash a band to an integer. For every hash value we store a list of record ids, these lists represent
     * buckets of duplicate candidates.
     */
    private void calculateHashBuckets() {
        // BEGIN SOLUTION
        int rowsPerBand = numMinHashs / numBands;
        LSH = new ArrayList<>();

        for (int b = 0; b < numBands; b++) {
            Hashtable<Integer, List<Integer>> bandTable = new Hashtable<>();
            for (int j = 0; j < signatureMatrix[0].length; j++) {
                int[] band = new int[rowsPerBand];
                for (int r = 0; r < rowsPerBand; r++) {
                    band[r] = signatureMatrix[b * rowsPerBand + r][j];
                }
                int hashValue = hash(band);
                bandTable.putIfAbsent(hashValue, new ArrayList<>());
                bandTable.get(hashValue).add(j);
            }
            LSH.add(bandTable);
        }
        // END SOLUTION
    }


    /**
     * First calculates tokens, minHashes and hash buckets.
     * Then iterates over all hashtables in {@link LSHDetection#LSH} and over all hash keys to compare all records
     * who share at least one hash bucket.
     * @param table Table to check for duplicates
     * @param recSim Similarity measure to use for comparing two records
     * @return Set of detected duplicates
     */
    @Override
    public Set<Duplicate> detect(Table table, RecordSimilarity recSim) {
        Set<Duplicate> duplicates = new HashSet<>();
        int numComparisons = 0;
        calculateTokens(table);
        calculateMinHashes(table);
        calculateHashBuckets();
        // BEGIN SOLUTION
        for (Hashtable<Integer, List<Integer>> bandTable : LSH) {
            for (List<Integer> bucket : bandTable.values()) {
                for (int i = 0; i < bucket.size(); i++) {
                    for (int j = i + 1; j < bucket.size(); j++) {
                        numComparisons++;
                        Record r1 = table.getData().get(bucket.get(i));
                        Record r2 = table.getData().get(bucket.get(j));
                        if (recSim.compare(r1, r2) >= threshold) {
                            duplicates.add(new Duplicate(r1, r2));
                        }
                    }
                }
            }
        }
        // END SOLUTION
        System.out.printf("LSH Detection found %d duplicates after %d comparisons%n", duplicates.size(), numComparisons);
        return duplicates;
    }

}
