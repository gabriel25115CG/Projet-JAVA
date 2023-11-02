package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Brick extends GraphicObject{
    protected int hardnessLevel = 2;
    protected int x,y;

    public Brick(int x, int y,int hardnessLevel) {
        this.x = x;
        this.y = y;
        this.hardnessLevel = hardnessLevel;
    }

    public boolean isBricked(){
        return hardnessLevel == 0;
    }
    public int getHardnessLevel() {
        return hardnessLevel;

    }
    public void decHardness(){
        hardnessLevel--;
    }
    public void setHardnessLevel() {
        this.hardnessLevel = hardnessLevel;

    }
}