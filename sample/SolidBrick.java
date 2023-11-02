package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SolidBrick extends Brick {
    private static String imgPath = "assets/solidBrick";
    private static Image[] images = new Image[1];
    public SolidBrick(int x, int y) {
        super(x, y, -1);
   try {

            images[0] = new Image(new FileInputStream(imgPath + ".png"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        body = new ImageView(images[0]);
        body.setTranslateX(x);
        body.setTranslateY(y);
    }


}