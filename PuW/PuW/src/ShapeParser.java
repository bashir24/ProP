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
                case "shapeType":
                case "type":
                    shapeType = Shape.ShapeType.valueOf(value);
                    break;
                case "color":
                case "lineColor":
                    color = value;
                    break;
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
                case "lineWidth":
                case "lw":
                    lineWidth = Integer.parseInt(value);
                    break;
                case "scaleX":
                case "scaleHorizontal":
                    scaleX = Integer.parseInt(value);
                    break;
                case "scaleY":
                case "scaleVertical":
                    scaleY = Integer.parseInt(value);
                    break;
                case "rotation":
                    rotation = Integer.parseInt(value);
                    break;
                case "hidden":
                    hidden = Boolean.parseBoolean(value);
                    break;
            }
        }

        return new Shape(shapeType, color, posX, posY, tags, fillColor, lineWidth, scaleX, scaleY, rotation, hidden);
    }
}