package presenter;

import components.Vector2d;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import maps.MapCell;
import worldElements.Animal;

public class CellBox {

    private final MapCell mapCell;

    private final Ellipse element = new Ellipse();

    private boolean ableToClick = false;


    public CellBox(MapCell mapCell){
        this.mapCell = mapCell;
    }

    public void configureElement(int cellHeight, int cellWidth) {
        element.setRadiusX((double) (cellWidth)/2*0.85);
        element.setRadiusY((double) (cellHeight)/2*0.85);
        setColor();

        if (ableToClick){
            configureClickEvent();
        }
    }

    private void configureClickEvent() {
        element.setOnMouseClicked(event -> {
            System.out.println("Hello");
        });
    }

    private void setColor(){
        int red = getRed();
        Color color;

        if (mapCell.animalNumber() > 1){
            int blue = Math.min(255,mapCell.animalNumber()*50);
            red = red/mapCell.animalNumber();
            color = Color.rgb(red, 0, blue);
        } else {
            color = Color.rgb(red, 0, 0);
        }
        element.setFill(color);
    }

    private int getRed() {
        Animal animal = mapCell.getAnimals().get(0);
        return Math.min(255,animal.getEnergy()*10);
    }

    public Vector2d getPosition(){
        return mapCell.getCellPosition();
   }

   public Ellipse getElement(){
        return element;
   }

    public void setAbleToClick(boolean isClickAble) {
        ableToClick = isClickAble;
    }
}
