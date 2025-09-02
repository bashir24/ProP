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
                // ShapeType: alte und neue Bezeichnung
                case "shapeType":
                case "type":
                    shapeType = Shape.ShapeType.valueOf(value);
                    break;

                // Farbe: alte und neue Bezeichnung
                case "color":
                case "lineColor":
                    color = value;
                    break;

                // Füllfarbe bleibt gleich
                case "fillColor":
                    fillColor = value;
                    break;

                // Position X: alte und neue Bezeichnung
                case "posX":
                case "x":
                    posX = Integer.parseInt(value);
                    break;

                // Position Y: alte und neue Bezeichnung
                case "posY":
                case "y":
                    posY = Integer.parseInt(value);
                    break;

                // Linienbreite: alte und neue Bezeichnung
                case "lineWidth":
                case "lw":
                    lineWidth = Integer.parseInt(value);
                    break;

                // Skalierung X: alte und neue Bezeichnung
                case "scaleX":
                case "scaleHorizontal":
                    scaleX = Integer.parseInt(value);
                    break;

                // Skalierung Y: alte und neue Bezeichnung
                case "scaleY":
                case "scaleVertical":
                    scaleY = Integer.parseInt(value);
                    break;

                // Rotation
                case "rotation":
                    rotation = Integer.parseInt(value);
                    break;

                case "tags":
                    // Finde den Start und das Ende der Liste
                    int start = json.indexOf("[");
                    int end = json.indexOf("]", start);
                    if (start >= 0 && end > start) {
                        String tagList = json.substring(start + 1, end);
                        tagList = tagList.replace("\"", "").trim();
                        if (!tagList.isEmpty()) {
                            tags = new ArrayList<>();
                            for (String tag : tagList.split(",")) {
                                tags.add(tag.trim());
                            }
                        }
                    }
                    break;



            }
        }

        // Neues Shape-Objekt erstellen
        return new Shape(shapeType, color, fillColor, posX, posY, lineWidth, scaleX, scaleY, rotation, tags);
    }

}
