package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AbilityBrick extends Brick {
    private static String imgPath = "assets/abilityBrick";
    private static Image[] images = new Image[1];
    private int ability = 0;
        //ability ==  0 => +1 ball
        //ability ==  1 => playerLength+=20
        //ability ==  2 => playerLength-=20

    public int getAbility() {
        return ability;
    }

    public void setAbility(int ability) {
        this.ability = ability;
    }

    public AbilityBrick(int x, int y,int ability) {
        super(x, y, 1);
        this.ability = ability;
   try {
            for(int i = 0;i<hardnessLevel;i++){
            images[i] = new Image(new FileInputStream(imgPath + (i+1)+".png"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        body = new ImageView(images[hardnessLevel-1]);
        body.setTranslateX(x);
        body.setTranslateY(y);
    }
}