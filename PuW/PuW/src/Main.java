import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Shape shape = new Shape(
                Shape.ShapeType.CIRCLE,
                "rgba(255,0,0,0.5)",
                10,
                20,
                Arrays.asList("Lieblingsobjekt", "halbtransparent")
        );

        String shapeString = shape.toString();
         // ========== A2d========
        // Konsolenausgabe
        System.out.println(shapeString);

         // ==3e==============
        // In Datei speichern
        Util.writeToFile(shapeString, "shape_output.txt");




    }
}
