package com.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSlider;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class OptionsView extends StackPane {

	protected int rows = 9, columns = 9, mines = 10, shields = 2;
    private final AnchorPane anchorPane;
    private final VBox levelVBox;
    private final VBox customBox;
    private final ToggleGroup toggle;
    private final Label gameConfigLabel;
    private final Label levelLabel;
    private final JFXRadioButton easyButton;
    private final JFXRadioButton normalButton;
    private final JFXRadioButton customButton;
    private final Label rowsLabel;
    private final Label columnsLabel;
    private final Label minesLabel;
    private final Label shieldsLabel;
    private final JFXButton saveButton;
    private final JFXButton backButton;
    private final JFXSlider rowsSlider;
    private final JFXSlider columnsSlider;
    private final JFXSlider minesSlider;
    private final JFXCheckBox defualtCheckBox;
    private final JFXCheckBox customCheckBox;
    private final ScoresOptionsView scoresOptions;
    private final JFXCheckBox normalCheckBox;
    private final JFXCheckBox shieldedCheckBox;
    private final VBox vboxShieldedCheck;
    private final JFXSlider shieldsSlider;
    private final JFXCheckBox humanShield;
    private final JFXCheckBox dumpComShield;
    private final JFXCheckBox normalComShield;
    private final JFXCheckBox smartComShield;
    private final JFXButton saveDir;
	protected void adjustShieldsSlider() {
		if (shieldedCheckBox.isSelected()) {
			int val1 = (int) (20 * minesSlider.getValue()) / 100;
			shieldsSlider.setMax((int) (50 * minesSlider.getValue()) / 100);
			shieldsSlider.setMin((int) (20 * minesSlider.getValue()) / 100);
			if (shieldsSlider.getMax() <= 0)
				shieldsSlider.setDisable(true);
			else
				shieldsSlider.setDisable(false);
			shieldsSlider.setValue(shieldsSlider.getValue() < val1 ? val1 : (int) shieldsSlider.getValue());
		}

	}

	protected void adjustSliders() {
		minesSlider.setMin((int) (10 * rowsSlider.getValue() * columnsSlider.getValue()) / 100);
		minesSlider.setMax((int) (30 * rowsSlider.getValue() * columnsSlider.getValue()) / 100);
		int val = (int) (10 * rowsSlider.getValue() * columnsSlider.getValue()) / 100;
		minesSlider.setValue(minesSlider.getValue() < val ? val : (int) minesSlider.getValue());
		adjustShieldsSlider();
	}

	protected boolean ok(KeyEvent event) {
		return event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT;
	}
	
	protected boolean ok1(KeyEvent event) {
		return event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN || ok(event);
	}
	protected void saveConstrains() {
		rows = (int) rowsSlider.getValue();
		columns = (int) columnsSlider.getValue();
		mines = (int) minesSlider.getValue();
		shields = (int) shieldsSlider.getValue();
	}
	protected void customButtonAction() {
		customBox.setVisible(true);
		rowsSlider.setValue(rows);
		columnsSlider.setValue(columns);
		adjustSliders();
		minesSlider.setValue(mines);
		if(shieldedCheckBox.isSelected())
			shieldsSlider.setValue(shields);
	}
	protected void easyButtonAction() {
		customBox.setVisible(false);
		rows = 9;
		columns = 9;
		mines = 10;
		shields = 2;
	}
	protected void normalButtonAction() {
		customBox.setVisible(false);
		rows = 16;
		columns = 16;
		mines = 25;
		shields = 5;		
	}
	
	public OptionsView() {
		super();
        scoresOptions = new ScoresOptionsView();
        levelVBox = new VBox();
        levelVBox.setPadding(new Insets(0, 20, 10, 20));
        customBox = new VBox(10);
        customBox.setPadding(new Insets(0, 20, 10, 30));
        toggle = new ToggleGroup();
        saveDir = new JFXButton("save Dir");
        gameConfigLabel = new Label("Game Configurations:");
        gameConfigLabel.setPadding(new Insets(10, 30, 30, 20));
        gameConfigLabel.setFont(Font.font("Aharoni", FontWeight.BOLD, 25));

        levelLabel = new Label("Choose the level:");
        levelLabel.setFont(Font.font("Aharoni", FontWeight.BOLD, 20));

        easyButton = new JFXRadioButton("Easy: 9 x 9 with 10 Mines");
        easyButton.setFont(Font.font("Aharoni", FontWeight.NORMAL, 20));
        easyButton.setOnAction(e -> {
            easyButtonAction();
        });
        easyButton.setOnKeyReleased(event -> {
            if (ok1(event)) {
                easyButtonAction();
            }
        });
        normalButton = new JFXRadioButton("Normal: 16 x 16 with 25 Mines");
        normalButton.setFont(Font.font("Aharoni", FontWeight.NORMAL, 20));
        normalButton.setOnAction(e -> {
            normalButtonAction();
        });
        normalButton.setOnKeyReleased(event -> {
            if (ok1(event)) {
                normalButtonAction();
            }
        });
        customButton = new JFXRadioButton("Custom");

        normalCheckBox = new JFXCheckBox("Normal Game");
        shieldedCheckBox = new JFXCheckBox("Shielded Game");
        // determine who will have a shield
        Label who = new Label("Who won't have shields?");
        who.setFont(Font.font("Aharoni", FontWeight.NORMAL, 20));
        humanShield = new JFXCheckBox("Human Players");
        humanShield.setFont(Font.font("Aharoni", FontWeight.NORMAL, 20));
        dumpComShield = new JFXCheckBox("Computer Dumb Players");
        dumpComShield.setFont(Font.font("Aharoni", FontWeight.NORMAL, 20));
        normalComShield = new JFXCheckBox("Computer Easy Players");
        normalComShield.setFont(Font.font("Aharoni", FontWeight.NORMAL, 20));
        smartComShield = new JFXCheckBox("Computer Smart Players");
        smartComShield.setFont(Font.font("Aharoni", FontWeight.NORMAL, 20));

        vboxShieldedCheck = new VBox();
        vboxShieldedCheck.getChildren().addAll(who, humanShield, dumpComShield, normalComShield, smartComShield);
        vboxShieldedCheck.setDisable(true);
        rowsLabel = new Label("Rows:");
        rowsLabel.setFont(Font.font("Aharoni", FontWeight.NORMAL, 20));
        rowsSlider = new JFXSlider(4, 16, 9);
        rowsSlider.setShowTickLabels(true);
        rowsSlider.setShowTickMarks(true);
        rowsSlider.setBlockIncrement(1);
        rowsSlider.setMajorTickUnit(2);

        columnsLabel = new Label("Columns:");
        columnsLabel.setFont(Font.font("Aharoni", FontWeight.NORMAL, 20));

        columnsSlider = new JFXSlider(5, 30, 9);
        columnsSlider.setShowTickLabels(true);
        columnsSlider.setShowTickMarks(true);
        columnsSlider.setBlockIncrement(1);
        columnsSlider.setMajorTickUnit(2);

        minesLabel = new Label("Mines:");
        minesLabel.setFont(Font.font("Aharoni", FontWeight.NORMAL, 20));
        minesSlider = new JFXSlider((10 * rows * columns) / 100, (30 * rows * columns) / 100,
                (10 * rows * columns) / 100);
        minesSlider.setBlockIncrement(1);
        shieldsSlider = new JFXSlider((20 * mines) / 100, (50 * mines) / 100, (20 * mines) / 100);
        shieldsSlider.setDisable(true);
        shieldsSlider.setBlockIncrement(1);

        shieldsLabel = new Label("Shields:");
        shieldsLabel.setFont(Font.font("Aharoni", FontWeight.NORMAL, 20));
        shieldsLabel.setDisable(true);

        rowsSlider.setOnMouseDragged(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                adjustSliders();
            }
        });
        rowsSlider.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                adjustSliders();
            }
        });
        rowsSlider.setOnKeyPressed(event -> {
            if (ok(event)) {
                adjustSliders();
            }
        });

        columnsSlider.setOnMouseDragged(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                adjustSliders();
            }
        });
        columnsSlider.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                adjustSliders();
            }
        });
        columnsSlider.setOnKeyPressed(event -> {
            if (ok(event)) {
                adjustSliders();
            }
        });

        minesSlider.setOnMouseDragged(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                adjustShieldsSlider();
            }
        });

        minesSlider.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                adjustShieldsSlider();
            }
        });

        minesSlider.setOnKeyPressed(event -> {
            if (ok(event)) {
                adjustShieldsSlider();
            }
        });

        saveButton = new JFXButton("Save");
        saveButton.setFont(Font.font("Aharoni", FontWeight.NORMAL, 15));
        saveButton.setStyle("-fx-background-radius: 3em; " + "-fx-background-color: orange;");
        saveButton.setPrefSize(60, 30);
        saveButton.setOnAction(event -> {
            saveConstrains();
        });
        saveButton.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                saveConstrains();
            }
        });

        customBox.getChildren().addAll(rowsLabel, rowsSlider, columnsLabel, columnsSlider, minesLabel, minesSlider);
        customBox.setVisible(false);

        customButton.setFont(Font.font("Aharoni", FontWeight.NORMAL, 20));

        customButton.setOnAction(e -> {
            customButtonAction();
        });
        customButton.setOnKeyReleased(event -> {
            if (ok1(event)) {
                customButtonAction();
            }
        });
        backButton = new JFXButton("Back");
        backButton.setPrefSize(70, 50);
        backButton.setTextFill(Color.BLUE);
        backButton.setFont(Font.font("Aharoni", FontWeight.BOLD, 15));
        backButton.setStyle("-fx-background-radius: 5em; " + "-fx-background-color: orange;");

        
        
        saveDir.setPrefSize(100, 50);
        saveDir.setTextFill(Color.BLUE);
        saveDir.setFont(Font.font("Aharoni", FontWeight.BOLD, 15));
        saveDir.setStyle("-fx-background-radius: 5em; " + "-fx-background-color: orange;");
        
        
        toggle.getToggles().addAll(easyButton, normalButton, customButton);

        levelVBox.getChildren().addAll(levelLabel, easyButton, normalButton, customButton, customBox);

        defualtCheckBox = new JFXCheckBox("Defualt Rules");
        customCheckBox = new JFXCheckBox("Custom Rules");

        defualtCheckBox.setFont(Font.font("Aharoni", FontWeight.BOLD, 15));
        customCheckBox.setFont(Font.font("Aharoni", FontWeight.BOLD, 15));

        defualtCheckBox.setSelected(true);
        scoresOptions.setDisable(true);

        defualtCheckBox.setOnAction(event -> {
            customCheckBox.setSelected(false);
            defualtCheckBox.setSelected(true);
            scoresOptions.setDisable(true);
        });

        customCheckBox.setOnAction(event -> {
            defualtCheckBox.setSelected(false);
            customCheckBox.setSelected(true);
            scoresOptions.setDisable(false);
        });

        VBox help = new VBox(5);
        Label space1 = new Label("      ");
        Label space2 = new Label("      ");
        help.getChildren().addAll(defualtCheckBox, customCheckBox, space1, space2, scoresOptions);

        normalCheckBox.setFont(Font.font("Aharoni", FontWeight.BOLD, 15));

        shieldedCheckBox.setFont(Font.font("Aharoni", FontWeight.BOLD, 15));

        normalCheckBox.setSelected(true);

        normalCheckBox.setOnAction(event -> {
            vboxShieldedCheck.setDisable(true);
            shieldedCheckBox.setSelected(false);
            normalCheckBox.setSelected(true);
            dumpComShield.setSelected(false);
            normalComShield.setSelected(false);
            smartComShield.setSelected(false);
            humanShield.setSelected(false);
            shieldsLabel.setDisable(true);
            shieldsSlider.setDisable(true);
            shieldsSlider.setValue(shieldsSlider.getMin());
            easyButton.setText("Easy: 9 X 9 with 10 Mines");
            normalButton.setText("Normal: 16 X 16 with 25 Mines");
            adjustSliders();
        });

        shieldedCheckBox.setOnAction(event -> {
            vboxShieldedCheck.setDisable(false);
            shieldedCheckBox.setSelected(true);
            normalCheckBox.setSelected(false);
            shieldsLabel.setDisable(false);
            shieldsSlider.setDisable(false);
            easyButton.setText("Easy: 9 X 9 with 10 Mines & 2 Shields");
            normalButton.setText("Normal: 16 X 16 with 25 Mines & 5 Shields");
            adjustSliders();
        });

        VBox help1 = new VBox(5);
        help1.getChildren().addAll(normalCheckBox, shieldedCheckBox, vboxShieldedCheck);

        customBox.getChildren().addAll(shieldsLabel, shieldsSlider, saveButton);

        anchorPane = new AnchorPane();
        HBox hbox = new HBox(5);
        anchorPane.getChildren().addAll(gameConfigLabel, levelVBox, help, help1, hbox);
        hbox.getChildren().addAll(saveDir,backButton);
        AnchorPane.setTopAnchor(levelVBox, 250.);
        AnchorPane.setLeftAnchor(help, 600.);
        AnchorPane.setLeftAnchor(help1, 30.);
        AnchorPane.setTopAnchor(help1, 50.);
        AnchorPane.setTopAnchor(help, 50.);
        AnchorPane.setRightAnchor(hbox, 50.);
        AnchorPane.setTopAnchor(hbox, 25.);
        getChildren().add(anchorPane);
        setBackground(new Background(new BackgroundImage(new Image("new_background.jpg"), BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
	
	}

	public final int getRows() {
		return rows;
	}

	public final int getColumns() {
		return columns;
	}

	public final int getMines() {
		return mines;
	}

	public final VBox getLevelVBox() {
		return levelVBox;
	}

	public final VBox getCustomBox() {
		return customBox;
	}

	public final ToggleGroup getToggle() {
		return toggle;
	}

	public final Label getGameConfigLabel() {
		return gameConfigLabel;
	}

	public final Label getLevelLabel() {
		return levelLabel;
	}

	public final RadioButton getEasyButton() {
		return easyButton;
	}

	public final RadioButton getNormalButton() {
		return normalButton;
	}

	public final RadioButton getCustomButton() {
		return customButton;
	}

	public final Label getRowsLabel() {
		return rowsLabel;
	}

	public final Label getColumnsLabel() {
		return columnsLabel;
	}

	public final Label getMinesLabel() {
		return minesLabel;
	}

	public final Button getSaveButton() {
		return saveButton;
	}

	public final Button getBackButton() {
		return backButton;
	}

	public final int getShields() {
		return shields;
	}

	public final void setShields(int shields) {
		this.shields = shields;
	}

	public final AnchorPane getAnchorPane() {
		return anchorPane;
	}

	public final Label getShieldsLabel() {
		return shieldsLabel;
	}

	public final JFXSlider getRowsSlider() {
		return rowsSlider;
	}

	public final JFXSlider getColumnsSlider() {
		return columnsSlider;
	}

	public final JFXSlider getMinesSlider() {
		return minesSlider;
	}

	public final JFXCheckBox getDefualtCheckBox() {
		return defualtCheckBox;
	}

	public final JFXCheckBox getCustomCheckBox() {
		return customCheckBox;
	}

	public final ScoresOptionsView getScoresOptions() {
		return scoresOptions;
	}

	public final JFXCheckBox getNormalCheckBox() {
		return normalCheckBox;
	}

	public final JFXCheckBox getShieldedCheckBox() {
		return shieldedCheckBox;
	}

	public final JFXSlider getShieldsSlider() {
		return shieldsSlider;
	}

	public final void setRows(int rows) {
		this.rows = rows;
	}

	public final void setColumns(int columns) {
		this.columns = columns;
	}

	public final void setMines(int mines) {
		this.mines = mines;
	}

	public final JFXCheckBox getHumanShield() {
		return humanShield;
	}

	public final JFXCheckBox getDumbComShield() {
		return dumpComShield;
	}

	public final JFXCheckBox getNormalComShield() {
		return normalComShield;
	}

	public final JFXCheckBox getSmartComShield() {
		return smartComShield;
	}

	public final JFXButton getSaveDir() {
		return saveDir;
	}
	
}
