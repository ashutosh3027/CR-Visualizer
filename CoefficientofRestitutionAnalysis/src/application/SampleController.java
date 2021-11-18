package application;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class SampleController implements Initializable {
    @FXML
    private Circle sphere;

    @FXML
    private AnchorPane Top;
    @FXML
    private JFXSlider slider1;
    @FXML
    private JFXSlider slider2;
    @FXML
    private JFXButton btn;
    @FXML
    private JFXButton resetBtn;

    private double height = 400, p=250;
    private  double x = 60;
    private double y = 250;
    private double ce=0.9;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		      slider2.setValue(400);
		      slider1.setValue(0.9);
		      sphere.setRadius(50);
		      height=slider2.getValue();  //height
		      ce = slider1.getValue();
//		      System.out.println(q);
		      p = 600-height;
		      y = p;
		      sphere.relocate(x, y);
		     //To detect changes in height;
		      slider2.valueProperty().addListener(new ChangeListener<Number>() {
		          public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
		        	  height = (double)newValue;
		        	  p = 600-height;
				      y = p;
				      sphere.relocate(x, y);
				     
		              
		          }
		       });
		      //to detect changes in value of CoefficientoRestitution;
		      slider1.valueProperty().addListener(new ChangeListener<Number>() {
		          public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
		        	ce = (double) newValue;
		        	System.out.println(ce);
				     
		              
		          }
		       });
		      // To start the bouncing of ball;
		      EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
		            public void handle(ActionEvent e)
		            {
		            	
		            	Timeline timeline = new Timeline();
		  		        KeyFrame kf = new KeyFrame(Duration.seconds(0),
		  		    		  new EventHandler<ActionEvent>() {
		  		            int speedY = 3;
		  		            @Override
		  		            public void handle(ActionEvent actionEvent) {
		  		              	y = y+speedY;
		  		               
		  		                if(y>=600) {
		  		                	speedY= (-1)*speedY;
		  		                	double temp = height;
		  		                	height = height*ce*ce;
		  		                	p += temp-height;
		  		                }
		  		                if(y<=p) {
		  		                	speedY= (-1)*speedY;
		  		                }
		  		                sphere.relocate(x, y);
		  		                System.out.println((p));
		  		                if(p>=599.9) {
		  		                	
		  		                	
		  		                	sphere.relocate(x, 600);
		  		                	timeline.stop();
		  		                	btn.setDisable(false);
		  		                }
		  		               
		  		            }
		  		        });
		  		      timeline.getKeyFrames().addAll(kf, new KeyFrame(Duration.millis(20)));
		  		        timeline.setCycleCount(Timeline.INDEFINITE);
		  		        timeline.play();
		  		        btn.setDisable(true);
		            }
		        };
		       btn.setOnAction(event);
		     
	}
	
	   
	  

}
