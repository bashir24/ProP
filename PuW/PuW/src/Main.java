import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

<<<<<<< HEAD
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

=======
        String[] files = {
                "JsonFiles/example2.json",
                "JsonFiles/example2b.json",
                "JsonFiles/example3.json"
        };

// -------------  Aufgabe 2 -------------
//        List<String> attr = new ArrayList<>();
//        attr.add("transparent");
//        attr.add("roundedCorners");
//
//        Shape shape = new Shape(Shape.ShapeType.QUAD, "Red", 0, 0, attr);
//
//        System.out.println(shape.toString());
//
//        writeToFile(shape.toString(), "C:\\Users\\Coolj\\Desktop\\Test\\file.txt");


// -------------  Aufgabe 3 -------------
//        for (String file : files) {
//            try{
//                Shape newShape = ShapeParser.parseFromJson(ReadFile(file));
//                System.out.println(newShape.toString());
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }


// -------------  Aufgabe 4 -------------
        List<String> attr = new ArrayList<>();
        attr.add("transparent");

        Shape shape = new Shape(Shape.ShapeType.QUAD, "Red", 0, 0, attr, "Blue", 3, 0, 0, 25, false);

        System.out.println(shape.toString());

        // 3 weitere Copies:

        Shape copie1 = new Shape(shape);
        copie1.setShapeType(Shape.ShapeType.TRIANGLE);
        Shape copie2 = new Shape(shape);
        copie2.setPosX(40);
        Shape copie3 = new Shape(shape);
        copie3.setPosY(49);

//
//        System.out.println(copie1.toString());
//        System.out.println(copie2.toString());

// -------------  Aufgabe 1 Zettel 2-------------
//        for (String file : files) {
//            try{
//                Shape newShape = ShapeParser.parseFromJson(ReadFile(file));
//                System.out.println(newShape.toString());
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//
// -------------  Aufgabe 2 Zettel 2-------------
//c)
        try{
            writeToFile(ShapeParser.parseToJson(shape), "OutputFiles/shape.json");
            writeToFile(ShapeParser.parseToJson(copie1), "OutputFiles/copie1.json");
            writeToFile(ShapeParser.parseToJson(copie2), "OutputFiles/copie2.json");
            writeToFile(ShapeParser.parseToJson(copie3), "OutputFiles/copie3.json");
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
//d)
        try {
            System.out.println(ShapeParser.jacksonParseToShape("JsonFiles/example4.json").toString());
            System.out.println(ShapeParser.jacksonParseToShape("OutputFiles/shape.json").toString());
            System.out.println(ShapeParser.jacksonParseToShape("OutputFiles/copie1.json").toString());
            System.out.println(ShapeParser.jacksonParseToShape("OutputFiles/copie2.json").toString());
            System.out.println(ShapeParser.jacksonParseToShape("OutputFiles/copie3.json").toString());
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
//e)
        try{
            System.out.println(ShapeParser.jacksonParseToShape("JsonFiles/example5.json").toString());
        }catch (Exception e){
            System.err.println(e.getMessage());
        }

// -------------  Aufgabe 3 Zettel 2-------------
        System.out.println("Read example6.json with:");
        try {
            System.out.print("Jackson: ");
            System.out.println(ShapeParser.jacksonParseToShape("JsonFiles/example6.json").toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.print("Manuel Parsing: ");
        System.out.println(ShapeParser.parseFromJson(ReadFile("JsonFiles/example6.json")).toString());
>>>>>>> tat
    }

    private static String ReadFile(String file){
        try {
            return new String(Files.readAllBytes(Path.of(file)));
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Einlesen der Datei: " + file, e);
        }
    }

    private static void writeToFile(String content, String fileName) {
        try{
            Path path = Path.of(fileName);
            Files.writeString(path, content);
            System.out.println("Wrote content to File.");
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}