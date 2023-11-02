package sample;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

enum Direction {
    LEFT, RIGHT
}
public class Main extends Application {
    private Pane container = new Pane();
    private double playerSpeed = 50;
    private int score = 0;
    private Timer startTimer;
    private int playerWidth = 90;
    private int playerHeight = 10;
    private int windowWidth = 800;
    private int windowHeight = 600;
    private TimerTask startTimerTask;
    private  boolean toggler = false;
    private  GraphicObject start = new GraphicObject();;
    private  boolean gameStarted = false;
    Text scoreTxt = new Text("Score: 0");
    Pane scorePane = new Pane(scoreTxt);
    private Player player = new Player(375,550,playerWidth,playerHeight,Color.YELLOW);
//    private Rectangle player = new Rectangle(375, 550, 50, 10);
    private ArrayList<Brick> bricks = new ArrayList<>();
    private LinkedList<Ball> balls = new LinkedList<>();

    private LinkedList<Ability> abilities = new LinkedList<>();
    private Ball ball = new Ball(((Rectangle)(player.getBody())).getX(),((Rectangle)(player.getBody())).getY(),(Rectangle) player.getBody());
    private boolean ballAdd = false;
    AnimationTimer animation = new AnimationTimer() {
        private long lastUpdateStamp = 0;
        @Override
        public void handle(long now) {
            if (now - lastUpdateStamp >= (41666666 / 8)) {
                refreshContent();
                lastUpdateStamp = now;
            }
        }
    };
     AnimationTimer startAnimation = new AnimationTimer() {
        private long lastUpdateStamp = 0;
        @Override
        public void handle(long now) {
            if (now - lastUpdateStamp >= (1000000000 / 1.4)) {
                    if(toggler == false){
                                   container.getChildren().add(start.getBody());
                                   toggler = true;

                }else {
                    container.getChildren().remove(start.getBody());
                                   toggler = false;
                }
                lastUpdateStamp = now;
            }
        }
    };
    EventHandler<KeyEvent> event = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getCode() == KeyCode.LEFT) {
                if(((Rectangle)(player.getBody())).getX() > 0)
                ((Rectangle)(player.getBody())).setX(((Rectangle)(player.getBody())).getX() - playerSpeed);
//                player.getBody().setTranslateX(player.getBody().getTranslateX() - playerSpeed);
               for(Ball ball:balls){ if (!ball.isMoving())
                    ball.attachToPlayer(player);}
            }
            if (keyEvent.getCode() == KeyCode.RIGHT) {
                if(((Rectangle)(player.getBody())).getX() + playerWidth < windowWidth)
                ((Rectangle)(player.getBody())).setX(((Rectangle)(player.getBody())).getX() + playerSpeed);
//                player.getBody().setTranslateX(player.getBody().getTranslateX() + playerSpeed);
               for(Ball ball:balls){ if (!ball.isMoving())
                    ball.attachToPlayer(player);}
            }
            if (keyEvent.getCode() == KeyCode.SPACE) {
                if(gameStarted == false){

                       animation.start();
                       gameStarted = true;
                }
                else {
                for(Ball ball:balls){ball.setMoving(true);
                ball.move();
                startAnimation.stop();
                if(container.getChildren().contains(start.getBody())){
                    container.getChildren().remove(start.getBody());
                }
                }

                }
            }
        }
    };
    private void instantiateAbility(double x, double y, int type){
        Ability ability = new Ability(x,y,type);
        abilities.add(ability);
        container.getChildren().add(ability.getBody());
    }
    private void moveAbilities(){
        for(Ability ability:abilities){

            if(ability.getBody().getBoundsInParent().intersects(player.getBody().getBoundsInParent())){
                if(ability.getAbility() == 0 ) ((Rectangle)(player.getBody())).setWidth(((Rectangle)(player.getBody())).getWidth() + 25);
                container.getChildren().remove(ability.getBody());
                ability.setDisabled(true);

            }
            if(ability.getBody().getTranslateY() > 600){
                container.getChildren().remove(ability.getBody());
                ability.setDisabled(true);
            }
            ability.move(5);

        }
                abilities.removeIf(GraphicObject::isDisabled);


    }
    private void refreshContent(){
        for(Ball ball: balls) {
            ball.move();
            if((((Circle)ball.getBody()).getCenterY()) > 650){
                container.getChildren().remove(ball.getBody());
                ball.setDisabled(true);
                ball.setMoving(false);
            }
            if (ball.touch(player)) {

                ball.setDownDirection(ball.getDownDirection() * -1);
                if (Math.random() < 0.3)
                    ball.setRightDirection(ball.getRightDirection() * (-1));
                if (Math.random() < 0.3) ball.randomizeSpeed();
            }
            for (Brick brick : bricks) {

                if (brick.touch(ball)) {

                         if (brick instanceof AbilityBrick) {
//                             System.out.println("Ability instanciation");
                    if (((AbilityBrick) brick).getAbility() == 0 && brick.isDisabled() == false) {
                        instantiateAbility(brick.getBody().getTranslateX(),brick.getBody().getTranslateY(),0);
                    }
                    else if(((AbilityBrick)brick).getAbility() == 1){
                        instantiateAbility(brick.getBody().getTranslateX(),brick.getBody().getTranslateY(),1);
                    }
                    else if(((AbilityBrick)brick).getAbility() == 2){
                        instantiateAbility(brick.getBody().getTranslateX(),brick.getBody().getTranslateY(),2);
                    }
                }
                    if (ball.getBody().getBoundsInParent().getCenterY() > brick.getBody().getBoundsInParent().getMinY() && ball
                            .getBody().getBoundsInParent().getCenterY() < brick.getBody().getBoundsInParent().getMaxY()) {
                        if (ball.getBody().getBoundsInParent().getMinX() > brick.getBody().getBoundsInParent().getMinX()
                                && ball.getBody().getBoundsInParent().getMinX() <= brick.getBody().getBoundsInParent().getMaxX()
                                || ball.getBody().getBoundsInParent().getMaxX() >= brick.getBody().getBoundsInParent().getMinX()
                                && ball.getBody().getBoundsInParent().getMaxX() < brick.getBody().getBoundsInParent()
                                .getMaxX()) {
                            brick.decHardness();
                            if (brick.isBricked()) {
                                brick.setDisabled(true);
                                scoreTxt.setText("Score: " + (++score));

                                container.getChildren().remove(brick.getBody());
                            } else {
                                container.getChildren().remove(brick.getBody());
                                updateBrickImage(brick);
                                container.getChildren().add(brick.getBody());
                            }
                            if (Math.random() > 0.7)
                                ball.randomizeSpeed();
                            ball.setRightDirection(ball.getRightDirection() * -1);
                        }
                    } else if (ball.getBody().getBoundsInParent().getCenterX() > brick.getBody().getBoundsInParent().getMinX()
                            && ball.getBody().getBoundsInParent().getCenterX() < brick.getBody().getBoundsInParent()
                            .getMaxX()) {
                        if (ball.getBody().getBoundsInParent().getMinY() > brick.getBody().getBoundsInParent().getMinY()
                                && ball.getBody().getBoundsInParent().getMinY() <= brick.getBody().getBoundsInParent().getMaxY()
                                || ball.getBody().getBoundsInParent().getMaxY() >= brick.getBody().getBoundsInParent().getMinY()
                                && ball.getBody().getBoundsInParent().getMaxY() < brick.getBody().getBoundsInParent()
                                .getMaxY()) {

                            if (Math.random() > 0.7)
                                ball.randomizeSpeed();
                            ball.setDownDirection(ball.getDownDirection() * -1);
                            brick.decHardness();
                            if (brick.isBricked()) {
                                scoreTxt.setText("Score: " + (++score));
                                brick.setDisabled(true);
                                container.getChildren().remove(brick.getBody());
                            } else {
                                container.getChildren().remove(brick.getBody());
                                updateBrickImage(brick);
                                container.getChildren().add(brick.getBody());
                            }
                        }
                    }
                }
            }
        }
          for(Ability ability:abilities){

            if(ability.getBody().getBoundsInParent().intersects(player.getBody().getBoundsInParent())){
                if(ability.getAbility() == 0 ) ballAdd = true;
                else if(ability.getAbility() == 1){
                    if(((Rectangle)(player.getBody())).getWidth() <=120) ((Rectangle)(player.getBody())).setWidth(((Rectangle)(player.getBody())).getWidth() + 25);
                }
                else if(ability.getAbility() == 2){
                    if(((Rectangle)(player.getBody())).getWidth() >=35) ((Rectangle)(player.getBody())).setWidth(((Rectangle)(player.getBody())).getWidth() - 25);
                }
                container.getChildren().remove(ability.getBody());
                ability.setDisabled(true);

            }
            if(ability.getBody().getTranslateY() > 600){
                container.getChildren().remove(ability.getBody());
                ability.setDisabled(true);
            }
            ability.move(5);

        }
          abilities.removeIf(GraphicObject::isDisabled);
        bricks.removeIf(GraphicObject::isDisabled);
        balls.removeIf(GraphicObject::isDisabled);
        if(ballAdd  == true){
            addBalltoPlayer();
        }
//        System.out.println(balls.size());
        if(balls.size() == 0 && gameStarted == true){
            animation.stop();
            GraphicObject gameover = new GraphicObject();
            try {
                gameover.setBody(new ImageView(new Image(new FileInputStream("assets/gameover.png"))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            gameover.getBody().setTranslateX(100);
        gameover.getBody().setTranslateY(250);
        gameover.getBody().setScaleX(0.4);
        gameover.getBody().setScaleY(0.4);
        container.getChildren().addAll(gameover.getBody());
        }
        ballAdd = false;
    }
    private void updateBrickImage(Brick brick) {
        if (brick instanceof GrayBrick)
            ((GrayBrick) brick).updateImage();
        else if (brick instanceof BlueBrick)
            ((BlueBrick) brick).updateImage();
        else {
        }
    }
    private void initNodes() throws FileNotFoundException {
        balls.add(ball);
        for (int i = 2; i < 15; i++) {
            for (int j = 0; j <= 12; j++)
                if (j % 2 == 0) {
                    if(Math.random() > 0.8){
                        if(Math.random()>0.1){
                            double a = Math.random();
                            if(a< 0.2)
                            bricks.add(new AbilityBrick(i * 46, j * 22,0));
                            else if(a < 0.4)
                            bricks.add(new AbilityBrick(i * 46, j * 22,1));
                            else if(a < 0.6)
                            bricks.add(new AbilityBrick(i * 46, j * 22,2));
                        }
                        else
                            bricks.add(new SolidBrick(i * 46, j * 22));
                    }
                    else{
                    if (Math.random() > 0.5)
                        bricks.add(new BlueBrick(i * 46, j * 22));
                    else
                        bricks.add(new GrayBrick(i * 46, j * 22));
                    }
                }
        }
        scoreTxt.relocate(5, 580);
        scoreTxt.setFont(Font.font("Verdana",20));
        scoreTxt.setFill(Color.WHITE);
        BackgroundImage myBI= new BackgroundImage(new Image(new FileInputStream("assets/background.jpg")),
        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
          BackgroundSize.DEFAULT);
        container.setBackground(new Background(myBI));
        //Start

            try {
                start.setBody(new ImageView(new Image(new FileInputStream("assets/start.png"))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        start.getBody().setTranslateX(0);
        start.getBody().setTranslateY(250);
        start.getBody().setScaleX(0.4);
        start.getBody().setScaleY(0.4);




//        player.setFill(Color.BLACK);
    }
    private void addBalltoPlayer(){
        Ball ball = new Ball(((Rectangle)(player.getBody())).getX(),((Rectangle)(player.getBody())).getY(),(Rectangle) (player.getBody()));
        balls.add(ball);
        container.getChildren().add(ball.getBody());
    }
    private void addtoRoot() {
        container.getChildren().addAll(player.getBody(), ball.getBody(),scorePane);
        for (Brick brick : bricks)
            container.getChildren().add(brick.getBody());
    }

    @Override
    public void start(Stage window) throws Exception {
        window.setTitle("Brick breaker");
        Scene scene = new Scene(container, 800, 600);
        initNodes();
        addtoRoot();
        scene.setOnKeyPressed(event);
        window.setScene(scene);
        startAnimation.start();
        window.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
