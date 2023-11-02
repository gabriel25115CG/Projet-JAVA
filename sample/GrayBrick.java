package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GrayBrick extends Brick {
    private static String imgPath = "assets/grayBrick";
    private static Image[] images = new Image[3];
    public GrayBrick(int x, int y) {
        super(x, y, 3);
        try {
            for(int i = 0;i<hardnessLevel;i++){
            images[i] = new Image(new FileInputStream(imgPath + (i+1) + ".png"));

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        body = new ImageView(images[hardnessLevel-1]);
        body.setTranslateX(x);
        body.setTranslateY(y);
    }
       public void updateImage(){
        body = new ImageView(images[hardnessLevel-1]);
        body.setTranslateX(x);
        body.setTranslateY(y);
    }
}