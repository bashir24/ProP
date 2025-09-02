import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Util {
    public static String readFile(String filename) {
        try {
            return new String(Files.readAllBytes(Paths.get(filename)));
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Einlesen der Datei: " + filename, e);
        }
    }

    public static void writeToFile(String text, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(text);
            System.out.println("Erfolgreich in Datei geschrieben: " + filename);
        } catch (IOException e) {
            System.err.println("Fehler beim Schreiben: " + e.getMessage());
        }
    }
}
