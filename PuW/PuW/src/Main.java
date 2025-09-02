import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        // JSON-Datei einlesen
        String json3 = Util.readFile("JSON files/example3.json");



        // In Shape umwandeln
        Shape shape3 = ShapeParser.parseFromJson(json3);


        // Ausgabe
        System.out.println(shape3);

        // Alle Shapes in einem String sammeln
        String allShapes = shape3.toString() ;

        // Auch in Datei speichern (zur Kontrolle)
        Util.writeToFile(allShapes, "parsed_shape.txt");

    }
}
