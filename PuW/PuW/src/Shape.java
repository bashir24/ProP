import java.util.ArrayList;
import java.util.List;

public class Shape {
    public enum ShapeType { TRIANGLE, CIRCLE, QUAD }

    private ShapeType shapeType;
    private String color;
    private String fillColor;
    private int posX;
    private int posY;
    private int lineWidth;
    private int scaleX;
    private int scaleY;
    private int rotation;
    private List<String> tags;

    // Neuer Konstruktor
    public Shape(ShapeType shapeType, String color, String fillColor, int posX, int posY,
                 int lineWidth, int scaleX, int scaleY, int rotation, List<String> tags) {
        this.shapeType = shapeType;
        this.color = color;
        this.fillColor = fillColor;
        this.posX = posX;
        this.posY = posY;
        this.lineWidth = lineWidth;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.rotation = rotation;
        this.tags = tags;
    }

    // Copy-Konstruktor
    public Shape(Shape other) {
        this.shapeType = other.shapeType;
        this.color = other.color;
        this.fillColor = other.fillColor;
        this.posX = other.posX;
        this.posY = other.posY;
        this.lineWidth = other.lineWidth;
        this.scaleX = other.scaleX;
        this.scaleY = other.scaleY;
        this.rotation = other.rotation;
        // Wichtiger Punkt: Kopie der tags-Liste erzeugen
        this.tags = new ArrayList<>(other.tags);
    }

    // Getter und Setter
    public ShapeType getShapeType() { return shapeType; }
    public void setShapeType(ShapeType shapeType) { this.shapeType = shapeType; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getFillColor() { return fillColor; }
    public void setFillColor(String fillColor) { this.fillColor = fillColor; }

    public int getPosX() { return posX; }
    public void setPosX(int posX) { this.posX = posX; }

    public int getPosY() { return posY; }
    public void setPosY(int posY) { this.posY = posY; }

    public int getLineWidth() { return lineWidth; }
    public void setLineWidth(int lineWidth) { this.lineWidth = lineWidth; }

    public int getScaleX() { return scaleX; }
    public void setScaleX(int scaleX) { this.scaleX = scaleX; }

    public int getScaleY() { return scaleY; }
    public void setScaleY(int scaleY) { this.scaleY = scaleY; }

    public int getRotation() { return rotation; }
    public void setRotation(int rotation) { this.rotation = rotation; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    @Override
    public String toString() {
        return "Shape{" +
                "type=" + shapeType +
                ", color='" + color + '\'' +
                ", fillColor='" + fillColor + '\'' +
                ", posX=" + posX +
                ", posY=" + posY +
                ", lineWidth=" + lineWidth +
                ", scaleX=" + scaleX +
                ", scaleY=" + scaleY +
                ", rotation=" + rotation +
                ", tags=" + tags +
                '}';
    }
}
