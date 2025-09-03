import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShapeParser{

    public static String parseToJson(Shape shape) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(shape);
    }

    public static Shape jacksonParseToShape(String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(fileName), Shape.class);
    }

    public static Shape parseFromJson(String json) {
        json = json.replaceAll("[{}\"]", ""); // { } und " entfernen
        String[] lines = json.split(",");

        Shape.ShapeType shapeType = null;
        String color = "";
        int posX = 0;
        int posY = 0;
        List<String> tags = new ArrayList<>();
        String fillColor = "";
        int lineWidth = 0;
        int scaleX = 0;
        int scaleY = 0;
        int rotation = 0;
        boolean hidden = false;

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
<<<<<<< HEAD

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
=======
                case "posX":
                case "x":
                    posX = Integer.parseInt(value);
                    break;
                case "posY":
                case "y":
                    posY = Integer.parseInt(value);
                    break;
                case "tags":
                    // Array: [tag1, tag2]
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
                case "fillColor":
                    fillColor = value;
                    break;
>>>>>>> tat
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
<<<<<<< HEAD

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
=======
                case "hidden":
                    hidden = Boolean.parseBoolean(value);
>>>>>>> tat
                    break;



            }
        }

<<<<<<< HEAD
        // Neues Shape-Objekt erstellen
        return new Shape(shapeType, color, fillColor, posX, posY, lineWidth, scaleX, scaleY, rotation, tags);
    }

}
=======
        return new Shape(shapeType, color, posX, posY, tags, fillColor, lineWidth, scaleX, scaleY, rotation, hidden);
    }
}
>>>>>>> tat
