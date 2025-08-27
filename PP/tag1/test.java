package tag1;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;




import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StreamingJavaTest {

    //========================
    // Aufgabe 2) a) flatStreamOf
    //========================
    @Test
    void testFlatStreamOf() {
        System.out.println("=== Test: flatStreamOf ===");
        List<List<Integer>> list = List.of(
                List.of(1, 2),
                List.of(3, 4),
                List.of(5)
        );
        List<Integer> result = StreamingJava.flatStreamOf(list).collect(Collectors.toList());
        System.out.println("Result: " + result);
        assertEquals(List.of(1, 2, 3, 4, 5), result);
    }

    //========================
    // Aufgabe 2) b) mergeStreamsOf
    //========================
    @Test
    void testMergeStreamsOf() {
        System.out.println("=== Test: mergeStreamsOf ===");
        Stream<Stream<Integer>> streams = Stream.of(
                Stream.of(1, 2),
                Stream.of(3),
                Stream.of(4, 5)
        );
        List<Integer> result = StreamingJava.mergeStreamsOf(streams).collect(Collectors.toList());
        System.out.println("Result: " + result);
        assertEquals(List.of(1, 2, 3, 4, 5), result);
    }

    //========================
    // Aufgabe 2) c) minOf
    //========================
    @Test
    void testMinOf() {
        System.out.println("=== Test: minOf ===");
        List<List<Integer>> list = List.of(
                List.of(5, 3),
                List.of(7, 1),
                List.of(2)
        );
        int min = StreamingJava.minOf(list);
        System.out.println("Min: " + min);
        assertEquals(1, min);
    }

    //========================
    // Aufgabe 2) d) lastWithOf
    //========================
    @Test
    void testLastWithOf() {
        System.out.println("=== Test: lastWithOf ===");
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6);
        int lastEven = StreamingJava.lastWithOf(stream, x -> x % 2 == 0);
        System.out.println("Last even: " + lastEven);
        assertEquals(6, lastEven);
    }

    //========================
    // Aufgabe 2) e) findOfCount
    //========================
    @Test
    void testFindOfCount() {
        System.out.println("=== Test: findOfCount ===");
        Stream<String> stream = Stream.of("a", "b", "a", "c", "b", "a");
        Set<String> result = StreamingJava.findOfCount(stream, 2);
        System.out.println("Elements with count 2: " + result);
        assertEquals(Set.of("b"), result);
    }

    //========================
    // Aufgabe 2) f) makeStreamOf
    //========================
    @Test
    void testMakeStreamOf() {
        System.out.println("=== Test: makeStreamOf ===");
        String[] strings = {"abc", "d"};
        int[] codes = StreamingJava.makeStreamOf(strings).toArray();
        System.out.println("Char codes: " + Arrays.toString(codes));
        assertArrayEquals(new int[]{'a', 'b', 'c', 'd'}, codes);
    }

    //========================
    // Aufgabe 3) a) fileLines
    //========================
    @Test
    void testFileLines() throws IOException {
        System.out.println("=== Test: fileLines ===");
        Path tempFile = Files.createTempFile("test", ".csv");
        Files.writeString(tempFile,
                "header1,header2,header3,header4\n" +
                        "a,b,c,10.5\n" +
                        "d,e,f,20.5\n");
        Stream<String> lines = StreamingJava.fileLines(tempFile.toString());
        List<String> content = lines.collect(Collectors.toList());
        System.out.println("File lines: " + content);
        assertEquals(List.of("a,b,c,10.5", "d,e,f,20.5"), content);
        Files.deleteIfExists(tempFile);
    }

    //========================
    // Aufgabe 3) b) averageCost
    //========================
    @Test
    void testAverageCost() {
        System.out.println("=== Test: averageCost ===");
        Stream<String> lines = Stream.of(
                "a,b,c,10.5",
                "d,e,f,20.5"
        );
        double avg = StreamingJava.averageCost(lines);
        System.out.println("Average cost: " + avg);
        assertEquals(15.5, avg, 0.001);
    }

    //========================
    // Aufgabe 3) c) countCleanEnergyLevy
    //========================
    @Test
    void testCountCleanEnergyLevy() {
        System.out.println("=== Test: countCleanEnergyLevy ===");
        Stream<String> lines = Stream.of(
                "a,b,c,d,e,f",
                "g,h,i,j,k,",
                "l,m,n,o,p,0"
        );
        long count = StreamingJava.countCleanEnergyLevy(lines);
        System.out.println("Count: " + count);
        assertEquals(2, count);
    }

    //========================
    // Aufgabe 3) d) NaturalGasBilling orderByInvoiceDateDesc
    //========================
    @Test
    void testOrderByInvoiceDateDesc() {
        System.out.println("=== Test: orderByInvoiceDateDesc ===");
        Stream<String> csvLines = Stream.of(
                "2025-01-01,2025-01-01,2025-01-31,31,10.0,5.0,2.0,1.0,3.0,1.0,0.5,0.2,22.7",
                "2025-02-01,2025-02-01,2025-02-28,28,8.0,4.0,1.5,0.8,2.5,0.8,0.4,0.1,17.1"
        );
        List<StreamingJava.NaturalGasBilling> list =
                StreamingJava.NaturalGasBilling.orderByInvoiceDateDesc(csvLines).collect(Collectors.toList());
        System.out.println("Sorted records:");
        list.forEach(System.out::println);
        assertEquals("2025-02-01", list.get(0).invoiceDate());
    }

    //========================
    // Aufgabe 3) f) serialize
    //========================
    @Test
    void testSerialize() {
        System.out.println("=== Test: serialize ===");
        StreamingJava.NaturalGasBilling record = new StreamingJava.NaturalGasBilling(
                "2025-01-01", "2025-01-01", "2025-01-31", 31,
                10.0, 5.0, 2.0, 1.0, 3.0, 1.0, 0.5, 0.2, 22.7
        );
        Stream<StreamingJava.NaturalGasBilling> stream = Stream.of(record);
        Stream<Byte> serialized = StreamingJava.serialize(stream);
        List<Byte> bytes = serialized.collect(Collectors.toList());
        System.out.println("Serialized bytes size: " + bytes.size());
        assertTrue(bytes.size() > 0);
    }

    //========================
    // Aufgabe 3) g) deserialize
    //========================
    @Test
    void testDeserialize() {
        System.out.println("=== Test: deserialize ===");
        StreamingJava.NaturalGasBilling record = new StreamingJava.NaturalGasBilling(
                "2025-01-01", "2025-01-01", "2025-01-31", 31,
                10.0, 5.0, 2.0, 1.0, 3.0, 1.0, 0.5, 0.2, 22.7
        );
        Stream<StreamingJava.NaturalGasBilling> stream = Stream.of(record);
        Stream<Byte> serialized = StreamingJava.serialize(stream);
        Stream<StreamingJava.NaturalGasBilling> deserialized = StreamingJava.deserialize(serialized);
        List<StreamingJava.NaturalGasBilling> list = deserialized.collect(Collectors.toList());
        list.forEach(System.out::println);
        assertEquals(1, list.size());
        assertEquals(record, list.get(0));
    }

    //========================
    // Aufgabe 3) h) findFilesWith
    //========================
    @Test
    void testFindFilesWith() throws IOException {
        System.out.println("=== Test: findFilesWith ===");
        Path tempDir = Files.createTempDirectory("testDir");
        Path file1 = Files.createTempFile(tempDir, "startA", ".txt");
        Path file2 = Files.createTempFile(tempDir, "startB", ".txt");

        List<File> files = StreamingJava.findFilesWith(tempDir.toString(), "start", ".txt", 2)
                .collect(Collectors.toList());
        files.forEach(f -> System.out.println("Found file: " + f.getName()));
        assertTrue(files.contains(file1.toFile()));
        assertTrue(files.contains(file2.toFile()));

        Files.deleteIfExists(file1);
        Files.deleteIfExists(file2);
        Files.deleteIfExists(tempDir);
    }
}
