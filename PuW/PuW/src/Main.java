import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // JSON-Datei einlesen
        String json1b = Util.readFile("JSON files/example1b.json");
        String json1c =Util.readFile("JSON files/example1c.json");



        // In Shape umwandeln
        Shape shape1b = ShapeParser.parseFromJson(json1b);
        Shape shape1c = ShapeParser.parseFromJson(json1c);


        // Ausgabe
        System.out.println(shape1b);
        System.out.println(shape1c);

        // Alle Shapes in einem String sammeln
        String allShapes = shape1b.toString() + "\n" + shape1c.toString();

        // Auch in Datei speichern (zur Kontrolle)
        Util.writeToFile(allShapes, "parsed_shape.txt");

    }
}
