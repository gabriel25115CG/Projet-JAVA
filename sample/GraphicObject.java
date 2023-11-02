package sample;

import javafx.scene.Node;
import javafx.scene.image.Image;

public class GraphicObject {
    protected Node body;
    protected boolean isDisabled = false;
    public Node getBody() {
        return body;
    }
 public boolean touch(GraphicObject p){
        return body.getBoundsInParent().intersects(p.getBody().getBoundsInParent());
    }
    public void setBody(Node body) {
        this.body = body;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }
}