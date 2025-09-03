import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Shape {
    public enum ShapeType {
        TRIANGLE,
        CIRCLE,
        QUAD
    }

    // e) 5
    //Methode einlesen. Beschäftigen Sie sich mit Annotations in Jackson und beschreiben Sie wie sie
    //das Problem lösen würden. Die Implementierung müssen sie hierfür nicht machen.

    @JsonAlias ({"shapeType_one_of_three"})
    private ShapeType shapeType;
    private String color;
    private int posX;
    private int posY;
    @JsonAlias ({"tagSet"})
    private List<String> tags;
    private String fillColor;
    private int lineWidth;
    private int scaleX;
    private int scaleY;
    private int rotation;
    private boolean hidden;


    // leer konstrucktru A2d
    public Shape(){

    }

    public Shape(Shape shape){
        this.shapeType = shape.getShapeType();
        this.color = shape.getColor();
        this.posX = shape.getPosX();
        this.posY = shape.getPosY();
        this.tags = new ArrayList<>(shape.getTags());
        this.fillColor = shape.getFillColor();
        this.lineWidth = shape.getLineWidth();
        this.scaleX = shape.getScaleX();
        this.scaleY = shape.getScaleY();
        this.rotation = shape.getRotation();
        this.hidden = shape.getHidden();
    }

    public Shape(ShapeType shapeType, String color, int posX, int posY, List<String> tags, String fillColor, int lineWidth, int scaleX, int scaleY, int rotation, boolean hidden) {
        this.shapeType = shapeType;
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        this.tags = new ArrayList<String>(tags);
        this.fillColor = fillColor;
        this.lineWidth = lineWidth;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        if(0 <= rotation && rotation <= 360){
            this.rotation = rotation;
        }else{
            throw new IllegalArgumentException("Rotation must be between 0 and 360");
        }
        this.hidden = hidden;
    }

    public ShapeType getShapeType() {
        return shapeType;
    }
    public String getColor() {
        return color;
    }
    public int getPosX() {
        return posX;
    }
    public int getPosY() {
        return posY;
    }
    public List<String> getTags() {
        return new ArrayList<>(tags);
    }
    public String getFillColor() {return fillColor;}
    public int getLineWidth() {return lineWidth;}
    public int getScaleX() {return scaleX;}
    public int getScaleY() {return scaleY;}
    public int getRotation() {return rotation;}
    public boolean getHidden() {return hidden;}

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public void setPosX(int posX) {
        this.posX = posX;
    }
    public void setPosY(int posY) {
        this.posY = posY;
    }
    public void setTags(List<String> tags) {
        this.tags = new ArrayList<>(tags);
    }
    public void setFillColor(String fillColor) {this.fillColor = fillColor;}
    public void setLineWidth(int lineWidth) {this.lineWidth = lineWidth;}
    public void setScaleX(int scaleX) {this.scaleX = scaleX;}
    public void setScaleY(int scaleY) {this.scaleY = scaleY;}
    public void setRotation(int rotation) {
        if(0 <= rotation && rotation <= 360){
            this.rotation = rotation;
        }else{
            throw new IllegalArgumentException("Rotation must be between 0 and 360");
        }
    }
    public void setHidden(boolean hidden) {this.hidden = hidden;}

    @Override
    public String toString() {
        return "Shape{" +
                "type=" + shapeType +
                ", color='" + color + '\'' +
                ", posX=" + posX +
                ", posY=" + posY +
                ", tags=" + tags +
                ", fillColor='" + fillColor + '\'' +
                ", lineWidth=" + lineWidth +
                ", scaleX=" + scaleX +
                ", scaleY=" + scaleY +
                ", rotation=" + rotation +
                ", hidden=" + hidden + "}";
    }

}
