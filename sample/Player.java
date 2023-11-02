package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends GraphicObject {
    public Player(double initialX,double initialY,double width,double height,Color color){
        body = new Rectangle(initialX,initialY,width,height);
        ((Rectangle)body).setFill(color);
    }
}