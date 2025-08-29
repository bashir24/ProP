package tag1;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamingJava {
    // Aufgabe 2) a)
    public static <E> Stream<E> flatStreamOf(List<List<E>> list) {
        // TODO
        return list.stream().flatMap(inner -> inner.stream());

        //map → behält die Struktur (Stream von Streams)
        //flatMap → entpackt die innere Struktur und liefert einen flachen Stream aller Elemente
    }

    // Aufgabe 2) b)
    public static <E> Stream<E> mergeStreamsOf(Stream<Stream<E>> stream) {
        // TODO
        return stream.reduce(
                Stream.empty(),      // Startwert: leerer Stream
                Stream::concat       // Kombiniere zwei Streams zu einem
        );

        /*[ [1, 2], [3], [4, 5] ] erste und zweite dann die summe plus die dirtte
                [ 1, 2, 3, 4, 5 ]*/
    }

    // Aufgabe 2) c)
    public static <E extends Comparable<? super E>> E minOf(List<List<E>> list) {
        return list.stream()
                .flatMap(innerList -> innerList.stream())
                .min(Comparator.naturalOrder())
                .orElseThrow();

    }


    // Aufgabe 2) d)
    public static <E> E lastWithOf(Stream<E> stream, Predicate<? super E> predicate) {
        return stream.filter(predicate)                  // Nur Elemente, die das Prädikat erfüllen
                .reduce((first, second) -> second) // Reduziere auf das letzte Element
                .orElseThrow(() -> new NoSuchElementException("Kein Element gefunden"));

        //Eine Bedingung, die überprüft, ob ein Element passt, z. B. x -> x % 2 == 0 für gerade Zahlen.
    }


    // Aufgabe 2) e)
    public static <E> Set<E> findOfCount(Stream<E> stream, int count) {

        //Ein Set speichert eine Menge von Elementen.
        //Wichtig: Ein Set darf keine Duplikate enthalten.

        Map<E, Long> frequencies = stream.collect(
                Collectors.groupingBy(
                        e -> e,
                        Collectors.counting()
                )
        );

        /*groupingBy(e -> e) → Wir gruppieren die gleichen Elemente zusammen.
counting() → Für jede Gruppe zählen wir, wie oft das Element vorkommt.*/

        // entrySet() liefert alle Paare (Key → Value) als Set von Map.Entry.

       // frequencies = { a=3, b=2, c=1 }
        //frequencies.entrySet() = [ a=3, b=2, c=1 ]
        return frequencies.entrySet().stream()
                .filter(entry -> entry.getValue() == count)
                .map(entry -> entry.getKey())  // Lambda statt Method Reference
                .collect(Collectors.toSet());
    }


    // Aufgabe 2) f)

    public static IntStream makeStreamOf(String[] strings) {
        return Arrays.stream(strings)
                .flatMapToInt(str -> str.chars());
    }

//-------------------------------------------------------------------------------------------------

    // Aufgabe 3) a)
    public static Stream<String> fileLines(String path) {

        //BufferedReader erlaubt es, Zeile für Zeile zu lesen
        //Path.of(path) → erstellt ein Path-Objekt aus dem übergebenen Pfad.
        //Files.newBufferedReader(...) → öffnet die Datei zum Lesen und liefert einen BufferedReader.
        //reader.lines() → liefert einen Stream aller Zeilen der Datei (Stream<String>).
        //Jede Zeile der Datei ist ein Element des Streams.
        try {
            BufferedReader reader = Files.newBufferedReader(Path.of(path));
            return reader.lines()
                    .skip(1) // Erste Zeile (Spaltennamen) überspringen
                    .onClose(() -> System.out.println("Stream geschlossen."));
        } catch (IOException e) {
            e.printStackTrace();
            return Stream.empty();
        }
    }

    // Aufgabe 3) b)
    public static double averageCost(Stream<String> lines) {
        return lines.map(line -> line.split(","))
                .mapToDouble(parts -> Double.parseDouble(parts[3]))
                .average()
                .orElse(0.0);
    }

    // Aufgabe 3) c)
    public static long countCleanEnergyLevy(Stream<String> stream) {
        return stream.map(line -> line.split(",")) //"Alice,Book,12.5" -> ["Alice","Book","12.5"]
                .filter(parts -> parts.length <= 5 || parts[5].isBlank() || parts[5].equals("0"))
                .count();
    }


    // Aufgabe 3) d)
    public record NaturalGasBilling(
            String invoiceDate,
            String fromDate,
            String toDate,
            int billingDays,
            double billedGJ,
            double basicCharge,
            double deliveryCharges,
            double storageAndTransport,
            double commodityCharges,
            double tax,
            double cleanEnergyLevy,
            double carbonTax,
            double amount
    ) {
        // Wandelt CSV-Zeilen in NaturalGasBilling-Objekte und sortiert absteigend nach invoiceDate
        public static Stream<NaturalGasBilling> orderByInvoiceDateDesc(Stream<String> stream) {
            return stream
                    .map(line -> line.split(",")) // CSV in Array
                    .map(parts -> new NaturalGasBilling(
                            parts[0], // Invoice Date
                            parts[1], // From Date
                            parts[2], // To Date
                            (int) Double.parseDouble(parts[3]), // Billing Days, korrigiert für Dezimalwerte
                            Double.parseDouble(parts[4]), // Billed GJ
                            Double.parseDouble(parts[5]), // Basic charge
                            Double.parseDouble(parts[6]), // Delivery charges
                            Double.parseDouble(parts[7]), // Storage and transport
                            Double.parseDouble(parts[8]), // Commodity charges
                            Double.parseDouble(parts[9]), // Tax
                            (parts.length <= 10 || parts[10].isBlank()) ? 0 : Double.parseDouble(parts[10]), // Clean energy levy
                            (parts.length <= 11 || parts[11].isBlank()) ? 0 : Double.parseDouble(parts[11]), // Carbon tax
                            Double.parseDouble(parts[12]) // Amount
                    ))
                    .sorted(Comparator.comparing(NaturalGasBilling::invoiceDate).reversed()); // Sortierung absteigend
        }

        // Wandelt ein Objekt in einen CSV-kompatiblen Byte-Stream um
        public Stream<Byte> toBytes() {
            String csvLine = String.join(",",
                    invoiceDate,
                    fromDate,
                    toDate,
                    String.valueOf(billingDays),
                    String.valueOf(billedGJ),
                    String.valueOf(basicCharge),
                    String.valueOf(deliveryCharges),
                    String.valueOf(storageAndTransport),
                    String.valueOf(commodityCharges),
                    String.valueOf(tax),
                    String.valueOf(cleanEnergyLevy),
                    String.valueOf(carbonTax),
                    String.valueOf(amount)
            ) + "\n";

            return csvLine.chars()
                    .mapToObj(c -> (byte) c);
        }
    }



    //  1. Create record "NaturalGasBilling".
    //  2. Implement static method: "Stream<NaturalGasBilling> orderByInvoiceDateDesc(Stream<String> stream)".


    // Aufgabe 3) f)
    // TODO: Implement static method: "Stream<Byte> serialize(Stream<NaturalGasBilling> stream)".
    public static Stream<Byte> serialize(Stream<NaturalGasBilling> stream) {
        // Header
        String header = "InvoiceDate,FromDate,ToDate,BillingDays,BilledGJ,BasicCharge,DeliveryCharges,StorageAndTransport,CommodityCharges,Tax,CleanEnergyLevy,CarbonTax,Amount\n";
        Stream<Byte> headerStream = header.chars().mapToObj(c -> (byte) c);

        // Datensätze
        Stream<Byte> dataStream = stream.flatMap(NaturalGasBilling::toBytes);

        return Stream.concat(headerStream, dataStream);

    }


    // Aufgabe 3) g)
    public static Stream<NaturalGasBilling> deserialize(Stream<Byte> stream) {
        // Alle Bytes zu einem String zusammenfügen
        String all = stream.map(b -> (char)(byte)b)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

        // Jede Zeile (außer Header) in ein NaturalGasBilling-Objekt umwandeln
        return Arrays.stream(all.split("\n"))
                .skip(1) // Header überspringen
                .filter(line -> !line.isBlank())
                .map(line -> line.split(","))
                .map(parts -> new NaturalGasBilling(
                        parts[0], // Invoice Date
                        parts[1], // From Date
                        parts[2], // To Date
                        Integer.parseInt(parts[3]), // Billing Days
                        Double.parseDouble(parts[4]), // Billed GJ
                        Double.parseDouble(parts[5]), // Basic charge
                        Double.parseDouble(parts[6]), // Delivery charges
                        Double.parseDouble(parts[7]), // Storage and transport
                        Double.parseDouble(parts[8]), // Commodity charges
                        Double.parseDouble(parts[9]), // Tax
                        (parts.length <= 10 || parts[10].isBlank()) ? 0 : Double.parseDouble(parts[10]), // Clean energy levy
                        (parts.length <= 11 || parts[11].isBlank()) ? 0 : Double.parseDouble(parts[11]), // Carbon tax
                        Double.parseDouble(parts[12]) // Amount
                ));
    }






    // Aufgabe 3) h)
    public static Stream<File> findFilesWith(String dir, String startsWith, String endsWith, int maxFiles) {
        try {
            return Files.walk(Path.of(dir))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .filter(f -> f.getName().startsWith(startsWith) && f.getName().endsWith(endsWith))
                    .sorted(Comparator.comparingLong(File::length).reversed())
                    .limit(maxFiles);
        } catch (IOException e) {
            e.printStackTrace();
            return Stream.empty();
        }
    }}

