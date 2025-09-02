import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShapeParser{

    public static Shape parseFromJson(String json) {
        json = json.replaceAll("[{}\"]", ""); // { } und " entfernen
        String[] lines = json.split(",");

        Shape.ShapeType shapeType = null;
        String color = "";
        int posX = 0;
        int posY = 0;
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
                case "posX":
                    posX = Integer.parseInt(value);
                    break;
                case "posY":
                    posY = Integer.parseInt(value);
                    break;
                case "tags":
                    // Array: [tag1, tag2]
                    value = value.replace("[", "").replace("]", "").trim();
                    if (!value.isEmpty()) {
                        tags = new ArrayList<>(Arrays.asList(value.split("\\s*,\\s*")));
                    }
                    break;
            }
        }

        return new Shape(shapeType, color, posX, posY, tags);
    }
}


// Die Methode trim() entfernt alle führenden und nachgestellten Leerzeichen (Spaces, Tabs, Zeilenumbrüche) aus einem String.