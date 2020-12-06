package com.view;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SomeGameInfo extends HBox {
	private final RingProgressIndicator ringIndicator;
	private final Label shieldsInGridLabel;
	public SomeGameInfo(int spacing) {
		super(spacing);
		ringIndicator = new RingProgressIndicator();
		shieldsInGridLabel = new Label();
		shieldsInGridLabel.setFont(Font.font("Aharoni", FontWeight.BOLD, 18));
		shieldsInGridLabel.setTextFill(Color.YELLOW);
		shieldsInGridLabel.setPrefSize(400, 10);
		ImageView image=new ImageView("shield1.png");
		image.setFitHeight(50);
		image.setFitWidth(50);
		
		getChildren().addAll(ringIndicator ,image ,shieldsInGridLabel);
	}
	public final RingProgressIndicator getRingIndicator() {
		return ringIndicator;
	}
	public final Label getShieldsInGridLabel() {
		return shieldsInGridLabel;
	}
	public final void updateLabel(int x) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				shieldsInGridLabel.setText("shields in grid " + String.valueOf(x));				
			}
			
		});

	}
}
