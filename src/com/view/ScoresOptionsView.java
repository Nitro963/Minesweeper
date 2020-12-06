package com.view;

import com.jfoenix.controls.JFXButton;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ScoresOptionsView extends AnchorPane {

	private int markMine, unmarkMine, markSafe, unmarkSafe, revealEmpty, revealMine, autoReveal, timeOut, initShields;

	private final VBox vbox_advancedoption1;
	private final VBox vbox_advancedoption2;
	private final HBox hbox_alladvancedoptions;
	private final JFXButton resetButton;
	private final JFXButton saveButton;
	private final ArrayList<Integer> scores;
	private final Label l_advanced;
	private final Spinner<Integer> markMineSpinner;
	private final Spinner<Integer> markSafeSpinner;
	private final Spinner<Integer> unmarkMineSpinner;
	private final Spinner<Integer> revealEmptySpinner;
	private final Spinner<Integer> unmarkSafeSpinner;
	private final Spinner<Integer> autoRevealSpinner;
	private final Spinner<Integer> revealMineSpinner;
	private final Spinner<Integer> timerSpinner;
	private final Spinner<Integer> initShieldsSpinner;

	protected void save() {
		markMine = markMineSpinner.getValue();
		markSafe = markSafeSpinner.getValue();
		unmarkMine = unmarkMineSpinner.getValue();
		unmarkSafe = unmarkSafeSpinner.getValue();
		revealEmpty = revealEmptySpinner.getValue();
		revealMine = revealMineSpinner.getValue();
		autoReveal = autoRevealSpinner.getValue();
		initShields = initShieldsSpinner.getValue();
		timeOut = timerSpinner.getValue();

		scores.clear();
		scores.add(markMine);
		scores.add(unmarkMine);
		scores.add(markSafe);
		scores.add(unmarkSafe);
		scores.add(revealEmpty);
		scores.add(revealMine);
		scores.add(autoReveal);
	}

	public void reset() {
		markMine = 5;
		unmarkMine = 1;
		markSafe = 2;
		unmarkSafe = 2;
		revealEmpty = 10;
		revealMine = 250;
		autoReveal = 1;
		initShields = 0;
		timeOut = 10;

		markMineSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 50, markMine));
		markSafeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 20, markSafe));
		unmarkMineSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, unmarkMine));
		unmarkSafeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 15, unmarkSafe));
		revealEmptySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, revealEmpty));
		revealMineSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(50, 500, revealMine));
		autoRevealSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, autoReveal));
		timerSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, timeOut));
		initShieldsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, initShields));
	}

	public boolean isDefualt() {
		return markMine == 5 && unmarkMine == 1 && markSafe == 2 && unmarkSafe == 2 && revealEmpty == 10
				&& revealMine == 250 && autoReveal == 1 && timeOut == 10 && initShields == 0;
	};

	public ScoresOptionsView() {
		vbox_advancedoption1 = new VBox(10);
		vbox_advancedoption2 = new VBox(10);
		hbox_alladvancedoptions = new HBox(10);
		scores = new ArrayList<>();

		l_advanced = new Label("Scores Configurations:");
		l_advanced.setFont(Font.font("Aharoni", FontWeight.BOLD, 27));
		Label space = new Label("                   ");
		space.setPadding(new Insets(0, 50, 30, 0));

		Label space1 = new Label("                   ");
		space1.setPadding(new Insets(0, 50, 30, 0));
		Label space2 = new Label("                   ");
		Label space3 = new Label("                   ");
		Label space4 = new Label("                   ");
		Label space5 = new Label("                   ");
		Label space6 = new Label("                   ");
		Label space7 = new Label("                   ");
		Label space8 = new Label("                   ");
		Label space9 = new Label("                   ");

		Label markMineScore = new Label("Marking a mine");
		markMineScore.setFont(Font.font("Aharoni", FontWeight.NORMAL, 20));
		// markMineScore.setPadding(new Insets(0, 50, 30, 0));

		markMineSpinner = new Spinner<Integer>();
		markMineSpinner.setEditable(true);

		Label markSafeScore = new Label("Penality of marking an empty cell");
		markSafeScore.setFont(Font.font("Aharoni", FontWeight.NORMAL, 20));
		// markSafeScore.setPadding(new Insets(0, 50, 30, 0));

		markSafeSpinner = new Spinner<Integer>();
		markSafeSpinner.setEditable(true);
		Label unmarkMineScore = new Label("Penality of unmarking a mine");
		unmarkMineScore.setFont(Font.font("Aharoni", FontWeight.NORMAL, 20));
		// unmarkMineScore.setPadding(new Insets(0, 50, 30, 0));

		unmarkMineSpinner = new Spinner<Integer>();
		unmarkMineSpinner.setEditable(true);

		Label revealempty = new Label("Revealing an empty cell");
		revealempty.setFont(Font.font("Aharoni", FontWeight.NORMAL, 20));
		// revealempty.setPadding(new Insets(0, 50, 30, 0));

		revealEmptySpinner = new Spinner<Integer>();
		revealEmptySpinner.setEditable(true);
		Label unmarkSafeLabel = new Label("Unmarking an empty cell");
		unmarkSafeLabel.setFont(Font.font("Aharoni", FontWeight.NORMAL, 20));
		// unmarkSafeLabel.setPadding(new Insets(0, 50, 30, 0));

		unmarkSafeSpinner = new Spinner<Integer>();
		unmarkSafeSpinner.setEditable(true);
		Label autoRevealLabel = new Label("Auto revealing");
		autoRevealLabel.setFont(Font.font("Aharoni", FontWeight.NORMAL, 20));
		// autoRevealLabel.setPadding(new Insets(0, 50, 30, 0));

		autoRevealSpinner = new Spinner<Integer>();
		autoRevealSpinner.setEditable(true);

		Label revealMineLabel = new Label("Penality of revealing a mine");
		revealMineLabel.setFont(Font.font("Aharoni", FontWeight.NORMAL, 20));
		// revealMineLabel.setPadding(new Insets(0, 50, 30, 0));

		revealMineSpinner = new Spinner<Integer>();
		revealMineSpinner.setEditable(true);

		Label timerLabel = new Label("Time Out");
		timerLabel.setFont(Font.font("Aharoni", FontWeight.NORMAL, 20));
		// timerLabel.setPadding(new Insets(0, 50, 30, 0));

		timerSpinner = new Spinner<Integer>();
		timerSpinner.setEditable(true);

		Label notetimerLabel = new Label("if you picked up 0 then you chose to wait for an endless time");
		notetimerLabel.setFont(Font.font("Aharoni", FontWeight.NORMAL, 13));
		// notetimerLabel.setPadding(new Insets(0, 50, 30, 0));

		initShieldsSpinner = new Spinner<Integer>();
		initShieldsSpinner.setEditable(true);

		Label initshields = new Label("Initial Shields");
		initshields.setFont(Font.font("Aharoni", FontWeight.NORMAL, 20));

		reset();
		resetButton = new JFXButton("Reset");
		resetButton.setPrefSize(60, 20);
		resetButton.setTextFill(Color.BLUE);
		resetButton.setStyle("-fx-background-color: orange;");
		resetButton.setFont(Font.font("Aharoni", FontWeight.NORMAL, 15));

		resetButton.setOnMouseClicked(event -> {
			reset();
		});
		resetButton.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				reset();
			}
		});

		saveButton = new JFXButton("Save");
		saveButton.setFont(Font.font("Aharoni", FontWeight.NORMAL, 15));
		saveButton.setTextFill(Color.BLUE);
		saveButton.setStyle("-fx-background-color: orange;");
		saveButton.setPrefSize(60, 30);

		saveButton.setOnMouseClicked(event -> {
			save();
		});
		saveButton.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				save();
			}
		});

		scores.add(markMine);
		scores.add(unmarkMine);
		scores.add(markSafe);
		scores.add(unmarkSafe);
		scores.add(revealEmpty);
		scores.add(revealMine);
		scores.add(autoReveal);

		vbox_advancedoption1.getChildren().addAll(markMineScore, markMineSpinner, space2, unmarkSafeLabel,
				unmarkSafeSpinner, space3, revealempty, revealEmptySpinner, space4, autoRevealLabel, autoRevealSpinner,
				space5, initshields, initShieldsSpinner);

		vbox_advancedoption2.getChildren().addAll(revealMineLabel, revealMineSpinner, space6, markSafeScore,
				markSafeSpinner, space7, unmarkMineScore, unmarkMineSpinner, space8, timerLabel, timerSpinner,
				notetimerLabel);

		hbox_alladvancedoptions.getChildren().addAll(vbox_advancedoption1, space9, vbox_advancedoption2);

		Label space10 = new Label("         ");
		Label space11 = new Label("         ");
		getChildren().addAll(l_advanced);
		HBox hbox = new HBox(50, resetButton, saveButton);
		getChildren().add(hbox);
		getChildren().addAll(space10, space11, hbox_alladvancedoptions);

		AnchorPane.setTopAnchor(hbox_alladvancedoptions, 60.);
		AnchorPane.setLeftAnchor(hbox, 350.);
		AnchorPane.setTopAnchor(hbox, 10.);
	}

	public final ArrayList<Integer> getScores() {
		return scores;
	}
	

	public final int getInitShields() {
		return initShields;
	}

	public final void setInitShields(int initShields) {
		this.initShields = initShields;
	}

	public final int getTimeOut() {
		return timeOut;
	}

	public final VBox getVbox_advancedoption1() {
		return vbox_advancedoption1;
	}

	public final VBox getVbox_advancedoption2() {
		return vbox_advancedoption2;
	}

	public final HBox getHbox_alladvancedoptions() {
		return hbox_alladvancedoptions;
	}

	public final Button getBackbutton() {
		return resetButton;
	}

	public final Label getL_advanced() {
		return l_advanced;
	}

}
