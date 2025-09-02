import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        // Testobjekt mit allen Feldern
        Shape shape1 = new Shape(
                Shape.ShapeType.CIRCLE,
                "red",
                "yellow",
                10,
                20,
                2,
                1,
                1,
                45,
                Arrays.asList("favorite", "bright")
        );

        System.out.println("Original:");
        System.out.println(shape1);

        // Drei weitere Objekte über Copy-Konstruktor
        Shape shape2 = new Shape(shape1);
        shape2.setColor("blue");
        shape2.setRotation(90);

        Shape shape3 = new Shape(shape1);
        shape3.setFillColor("green");
        shape3.setScaleX(2);

        Shape shape4 = new Shape(shape1);
        shape4.setPosX(50);
        shape4.getTags().add("modified");

        System.out.println("\nKopien:");
        System.out.println(shape2);
        System.out.println(shape3);
        System.out.println(shape4);
    }
}
