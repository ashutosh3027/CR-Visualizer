package application;

import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextArea;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class SampleController implements Initializable {
	@FXML
	private Circle sphere; // Our ball

	@FXML
	private AnchorPane Top;
	@FXML
	private JFXSlider slider1; // It is for coefficient of restitution.

	@FXML
	private JFXSlider slider2; // It is for height change
	@FXML
	private JFXButton btn; // start button
	@FXML
	private JFXButton resetBtn; // resetButton
	@FXML
	private JFXTextArea showHeight; // textArea to show heights
	@FXML
	private JFXTextArea heightLabel; // textArea to show height label
	@FXML
	private JFXTextArea scale;
	@FXML
	private Label eChange;

	@FXML
	private Label heightChange;

	private double height = 400, topBorder = 250; // height is a height of ball from bottom topBorder is a height of
													// upper bound from top.

//	private double y = 200; 
	private double ce = 1; // coefficientofRestitution
	private boolean resetAll = false; // to reset screen
	private String printHeight = ""; // this string will get updated whenever ball is at the peak height.
	private String textHeightLabel = ""; // this string is use to label height number.
	private int label = 1; // height number
	private int printCount = 1; // number of print count

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Ball ball = new Ball();
		ball.setX(650);// x-coordinate of ball
		ball.setY(200);// y- coordinate f ball
		scale.setText("Slider-1:\nHeight:0 to 400\nSlider-2:\nCoefficient of Restitution: 0 to 1\ne = "
				+ (double) Math.round(ce * 100) / 100);
		slider2.setValue(400); // set initial height
		slider1.setValue(1.0); // set initial coefficient of restitution
		height = slider2.getValue(); // get initial height
		ce = slider1.getValue(); // get initial coefficient of restitution.
		topBorder = 600 - height; // Upper bound of ball
		ball.setY(topBorder); // y-coordinate of ball
		ball.ballPosition(sphere); // To relocate the sphere.
		// To detect changes in height;
		slider2.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				height = (double) newValue;
				topBorder = 600 - height;
				ball.setY(topBorder);
				ball.ballPosition(sphere);
				double temp = (double) Math.round(height * 100) / 100;
				heightChange.setText(Double. toString(temp));
			}
		});
		// to detect changes in value of CoefficientoRestitution;
		slider1.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				ce = (double) newValue;
				scale.setText("Slider-1:\nHeight:0 to 400\nSlider-2:\nCoefficient of Restitution: 0 to 1\ne = "
						+ (double) Math.round(ce * 100) / 100);
				double temp = (double) Math.round(ce * 100) / 100;
				eChange.setText(Double. toString(temp));
			}
		});
		// To start the bouncing of ball;
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				resetAll = false;
				printHeight = "";
				label = 1;
				printCount = 1;
				textHeightLabel = "Height " + label + ": \n";
				double tempHeight = (double) Math.round(height * 100) / 100;
				printHeight += String.valueOf(tempHeight) + "\n";
				showHeight.setText(String.valueOf(printHeight));
				heightLabel.setText(String.valueOf(textHeightLabel));
				slider1.setDisable(true);
				slider2.setDisable(true);
				Timeline timeline = new Timeline();
				KeyFrame kf = new KeyFrame(Duration.seconds(0), new EventHandler<ActionEvent>() {
					int speedY = 5;

					@Override
					public void handle(ActionEvent actionEvent) {
						if (resetAll == true || printCount == 10) {

							setToInitial(ball);
							speedY = 5;
							timeline.stop();
						}
						ball.setY(ball.getY() + speedY);

						if (ball.getY() >= 600 && !resetAll) {
							speedY = (-1) * speedY;
							double temp = height;
							height = height * ce * ce;
							topBorder += temp - height;
						}
						if (ball.getY() <= topBorder && !resetAll) {
							speedY = (-1) * speedY;
							label++;
							double tempHeight = (double) Math.round(height * 100) / 100;
							printHeight += String.valueOf(tempHeight) + "\n";
							textHeightLabel += "Height " + label + ": \n";
							heightLabel.setText(String.valueOf(textHeightLabel));
							showHeight.setText(printHeight);
							printCount++;
						}
						ball.ballPosition(sphere);

					}
				});
				timeline.getKeyFrames().addAll(kf, new KeyFrame(Duration.millis(20)));
				timeline.setCycleCount(Timeline.INDEFINITE);
				timeline.play();
				btn.setDisable(true); // Whenever we start our simulation start button get disabled.
			}
		};
		btn.setOnAction(event); // Start button action
		// Rest button function;
		EventHandler<ActionEvent> reset = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent r) {
				setToInitial(ball);
				printHeight = "";
				textHeightLabel = "";
				heightLabel.setText(String.valueOf(textHeightLabel));
				showHeight.setText(String.valueOf(printHeight));
				btn.setDisable(false);
				resetAll = true;
			}
		};
		resetBtn.setOnAction(reset); // reset button action;

	}

	public void setToInitial(Ball ball) {
		this.slider1.setDisable(false);
		this.slider2.setDisable(false);
		this.slider2.setValue(400);
		this.slider1.setValue(1);
		this.ce = 1;
		this.height = 400;
		this.topBorder = 600 - this.height;
		ball.setY(topBorder);
		ball.ballPosition(sphere);
		printCount = 1;
		label = 1;
	}
}
