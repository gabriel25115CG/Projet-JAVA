package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Ball extends GraphicObject {
    private boolean isMoving = false;
    private double radius = 5;
    private double downDirection=-1,rightDirection=1;
    private double xSpeed=4,ySpeed=4;
    private double maxXSpeed=5,maxYSpeed=5;
    private double maxRightDirection=20;
    private double dx = 5;
    public Ball(double positionX,double positionY,Rectangle playerRect){
        body = new Circle(playerRect.getX() + dx,positionY-radius,radius, Color.LIMEGREEN);
//             body.setTranslateY(playerRect.getTranslateY());
        if(Math.random() > 0.5) rightDirection*=-1;
    ((Circle)(body)).setCenterX(playerRect.getX() + playerRect.getTranslateX() + dx + 10);
        ((Circle)(body)).setCenterY(playerRect.getY() - radius - 1);
//        body.setTranslateX(375+dx);
//        body.setTranslateY(550- radius);
    }
    public void attachToPlayer(Player player){
//        body.setTranslateX(player.getBody().getTranslateX());
//        body.setTranslateY(player.getBody().getTranslateY());
        ((Circle)(body)).setCenterX(((Rectangle)(player.getBody())).getX() + player.getBody().getTranslateX() + dx + 10);
        ((Circle)(body)).setCenterY(((Rectangle)(player.getBody())).getY() - radius);
    }
    private double randomize(double min,double max){
        return Math.random() * (max-min)+min;
    }

    public double getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    public double getySpeed() {
        return ySpeed;
    }

    public void setySpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    public void randomizeSpeed(){
        if(Math.random() > 0.5){
        xSpeed = randomize(2,maxXSpeed);
            if(xSpeed < 3) ySpeed = randomize(5,maxYSpeed);
            else ySpeed = randomize(3,maxYSpeed);
        }
        else {
        ySpeed = randomize(2,maxYSpeed);
        if(ySpeed < 3) xSpeed = randomize(5,maxXSpeed);
            else xSpeed = randomize(3,maxXSpeed);

        }
    }
    public boolean isNotMoving(){
        return !isMoving;
    }
    public boolean isMoving() {
        return isMoving;
    }
    public void move(){
        if(isMoving){
//            double x = ((Circle)body).getCenterX() +body.getTranslateX();
//            double y = ((Circle)body).getCenterY() +body.getTranslateY();
            double x = ((Circle)body).getCenterX();
            double y = ((Circle)body).getCenterY();
            if(x - radius< xSpeed || 800 - (x + radius) < xSpeed)
                rightDirection*=-1;
            if(y - radius < ySpeed)
                downDirection*=-1;
            else if(650 - (y + radius) < ySpeed){
                this.isDisabled = false;
            }
//            System.out.println("(" + (((Circle)body).getCenterX() +body.getTranslateX()) + "," + (((Circle)body).getCenterY() +body.getTranslateY())+ ")");
//        body.setTranslateX(body.getTranslateX()+rightDirection*xSpeed);
//        body.setTranslateY(body.getTranslateY()+downDirection*ySpeed);
            ((Circle)body).setCenterX(x + rightDirection * xSpeed);
            ((Circle)body).setCenterY(y + downDirection * ySpeed);
        }
    }

    public double getDownDirection() {
        return downDirection;
    }

    public void setDownDirection(double downDirection) {
        this.downDirection = downDirection;
    }

    public double getRightDirection() {
        return rightDirection;
    }

    public void setRightDirection(double rightDirection) {
        this.rightDirection = rightDirection;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

}
