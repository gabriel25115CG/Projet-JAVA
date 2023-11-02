package sample;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Ability  extends GraphicObject{
    private int ability = 0;
    private static Image image;

    static {
        try {
            image = new Image(new FileInputStream("assets/ability.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getAbility() {
        return ability;
    }

    public void setAbility(int ability) {
        this.ability = ability;
    }

    public Ability(double x, double y,int ability){
        this.ability = ability;
//        body = new Rectangle(x,y,10,20);
//        ((Rectangle)body).setFill(Color.BLUE);
        body = new ImageView(image);
//        body.setScaleX(10);
//        body.setScaleY(20);
        body.setScaleX(0.5);
        body.setScaleY(0.5);
        body.setTranslateX(x);
        body.setTranslateY(y);
    }
    public void move(double speed){
        body.setTranslateY(body.getTranslateY() + speed);
    }

    @Override
    public boolean isDisabled() {
        return isDisabled;
    }

    @Override
    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }
}
