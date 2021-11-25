package application;

import javafx.scene.shape.Circle;

public class Ball {
    private double x;
    private double y;
    public void ballPosition( Circle c) {
    	 c.relocate(this.x,this.y);
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }

 
}