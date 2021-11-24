package application;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextArea;

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
    @FXML
    private JFXTextArea showHeight;

    private double height = 400, p=250;
    private double x = 650;
    private double y = 250;
    private double ce=0.9;
    private boolean resetAll=false;
    private String printHeight = "";
    private int printCount = 0;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		      slider2.setValue(400);
		      slider1.setValue(1.0);
		      height=slider2.getValue();  //height
		      ce = slider1.getValue();
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
		        	// System.out.println(ce);  
		          }
		       });
		      // To start the bouncing of ball;
		      EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
		            public void handle(ActionEvent e)
		            {
		            	resetAll = false;
		            	printHeight = "";
			    		showHeight.setText(String.valueOf(printHeight));
		            	Timeline timeline = new Timeline();
		  		        KeyFrame kf = new KeyFrame(Duration.seconds(0),
		  		    		  new EventHandler<ActionEvent>() {
		  		            int speedY = 5;
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
		  		                	double tempHeight = (double)Math.round(height*100)/100;
		  		                	printHeight += String.valueOf(tempHeight) + "\n";
		  		                	showHeight.setText(printHeight);
		  		                	printCount++;
		  		                }
		  		                sphere.relocate(x, y);
		  		                // System.out.println((p));
//		  		                if(p>=599.9) {
//		  		                	sphere.relocate(x, 600);
//		  		                	timeline.stop();
//		  		                	btn.setDisable(false);
//		  		                }
		  		              // reset button
				  		        if(resetAll == true || printCount == 10)
				  		        {
				  		        	btn.setDisable(false);
						    		slider1.setDisable(false);
						    		slider2.setDisable(false);
						    		// System.out.println(resetAll);
						    		slider2.setValue(400);
						    		slider1.setValue(1);
				  		        	p = 600-height;
				  		        	y = p;
				  		        	sphere.relocate(x,y);
				  		        	printCount = 0;
				  		        	timeline.stop();
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
		       
		       EventHandler<ActionEvent> reset = new EventHandler<ActionEvent>()
    		   {
		    	   public void handle(ActionEvent r)
		    	   {
		    		   printHeight = "";
		    		   showHeight.setText(String.valueOf(printHeight));
		    		   resetAll = true;
		    	   }
    		   };
    		   resetBtn.setOnAction(reset);	
		     
	}
	
	   
	  

}
