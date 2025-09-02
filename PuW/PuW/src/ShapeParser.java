import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShapeParser {

    public static Shape parseFromJson(String json) {
        // Entferne { }, " → einfacher zu splitten
        json = json.replaceAll("[{}\"]", "");
        String[] lines = json.split(",");

        // Ergebnisvariablen
        Shape.ShapeType shapeType = null;
        String color = "";
        String fillColor = "";
        int posX = 0;
        int posY = 0;
        int lineWidth = 0;
        int scaleX = 0;
        int scaleY = 0;
        int rotation = 0;
        List<String> tags = new ArrayList<>();

        for (String line : lines) {
            String[] kv = line.split(":");
            if (kv.length < 2) continue;

            String key = kv[0].trim();
            String value = kv[1].trim();

            switch (key) {
                case "shapeType":
                    shapeType = Shape.ShapeType.valueOf(value);
                    break;
                case "color":
                    color = value;
                    break;
                case "fillColor":
                    fillColor = value;
                    break;
                case "posX":
                    posX = Integer.parseInt(value);
                    break;
                case "posY":
                    posY = Integer.parseInt(value);
                    break;
                case "lineWidth":
                    lineWidth = Integer.parseInt(value);
                    break;
                case "scaleX":
                    scaleX = Integer.parseInt(value);
                    break;
                case "scaleY":
                    scaleY = Integer.parseInt(value);
                    break;
                case "rotation":
                    rotation = Integer.parseInt(value);
                    break;
                case "tags":
                    value = value.replace("[", "").replace("]", "").trim();
                    if (!value.isEmpty()) {
                        tags = new ArrayList<>(Arrays.asList(value.split("\\s*,\\s*")));
                    }
                    break;
            }
        }

        // Neues Shape-Objekt mit allen Eigenschaften erstellen
        return new Shape(shapeType, color, fillColor, posX, posY, lineWidth, scaleX, scaleY, rotation, tags);
    }
}
