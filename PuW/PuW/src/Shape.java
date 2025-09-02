import java.util.List;

public class Shape {
    public enum ShapeType { TRIANGLE, CIRCLE, QUAD }

    private ShapeType shapeType;
    private String color;
    private int posX;
    private int posY;
    private List<String> tags;

    // Konstruktor
    public Shape(ShapeType shapeType, String color, int posX, int posY, List<String> tags) {
        this.shapeType = shapeType;
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        this.tags = tags;
    }

    // Getter und Setter
    public ShapeType getShapeType() { return shapeType; }
    public void setShapeType(ShapeType shapeType) { this.shapeType = shapeType; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public int getPosX() { return posX; }
    public void setPosX(int posX) { this.posX = posX; }

    public int getPosY() { return posY; }
    public void setPosY(int posY) { this.posY = posY; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }


    @Override
    public String toString() {
        return "Shape{" +
                "type=" + shapeType +
                ", color='" + color + '\'' +
                ", posX=" + posX +
                ", posY=" + posY +
                ", tags=" + tags +
                '}';
    }


}
