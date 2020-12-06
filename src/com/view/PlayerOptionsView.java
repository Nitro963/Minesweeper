 package com.view;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PlayerOptionsView extends VBox {

    private final JFXRadioButton humanButton;
    private final VBox humanName;
    private final Label label;
    private final JFXTextField textField;
    private final JFXRadioButton comButton;
    private final VBox computerType;
    private final JFXRadioButton dumpButton;
    private final JFXRadioButton normalButton;
    private final JFXRadioButton smartButton;
    private final ToggleGroup typeToggle;
    private final ToggleGroup comToggle;
    private final Label labelhumancolorpicker;
    private final Label labelcompcolorpicker;
    private final JFXColorPicker humancolorpicker;
    private final JFXColorPicker compcolorpicker;

    public PlayerOptionsView(int i) {

        humanButton = new JFXRadioButton();
        humanName = new VBox(5);
        label = new Label();
        textField = new JFXTextField();
        comButton = new JFXRadioButton();
        computerType = new VBox(5);
        dumpButton = new JFXRadioButton();
        normalButton = new JFXRadioButton();
        smartButton = new JFXRadioButton();
        typeToggle = new ToggleGroup();
        comToggle = new ToggleGroup();
        labelhumancolorpicker = new Label("Pick a color   ");
        labelcompcolorpicker = new Label("Pick a color   ");
        humancolorpicker = new JFXColorPicker(Color.BLUE);
        humancolorpicker.setPrefHeight(12);
        humancolorpicker.setPrefWidth(50);
        compcolorpicker = new JFXColorPicker(Color.BLUE);
        compcolorpicker.setPrefHeight(12);
        compcolorpicker.setPrefWidth(50);
        humanButton.setMnemonicParsing(false);
        humanButton.setText("Human");
        humanButton.setFont(Font.font("Aharoni", FontWeight.NORMAL, 20));
        label.setText("Player" + String.valueOf(i) + " Name");
        label.setFont(Font.font("Aharoni", FontWeight.NORMAL, 15));
        textField.setPrefHeight(24.0);
        textField.setPrefWidth(5.0);
        labelhumancolorpicker.setFont(Font.font("Aharoni", FontWeight.NORMAL, 14));
        labelcompcolorpicker.setFont(Font.font("Aharoni", FontWeight.NORMAL, 14));

        comButton.setMnemonicParsing(false);
        comButton.setText("Computer");
        comButton.setFont(Font.font("Aharoni", FontWeight.NORMAL, 20));

        dumpButton.setMnemonicParsing(false);
        dumpButton.setText("Dump");
        dumpButton.setFont(Font.font("Aharoni", FontWeight.NORMAL, 17));

        normalButton.setMnemonicParsing(false);
        normalButton.setText("Normal");
        normalButton.setFont(Font.font("Aharoni", FontWeight.NORMAL, 17));

        smartButton.setMnemonicParsing(false);
        smartButton.setText("Smart");
        smartButton.setFont(Font.font("Aharoni", FontWeight.NORMAL, 17));

        humanName.setVisible(false);
        computerType.setVisible(false);

        typeToggle.getToggles().addAll(humanButton, comButton);
        comToggle.getToggles().addAll(dumpButton, normalButton, smartButton);

        humanButton.setOnAction(Event -> {
            humanName.setVisible(true);
            computerType.setVisible(false);
            textField.clear();
        });

        comButton.setOnAction(Event -> {
            humanName.setVisible(false);
            computerType.setVisible(true);
            dumpButton.setSelected(true);
        });
        getChildren().add(humanButton);
        humanName.getChildren().add(label);
        humanName.getChildren().add(textField);
        HBox v = new HBox();
        v.getChildren().add(labelhumancolorpicker);
        v.getChildren().add(humancolorpicker);
        humanName.getChildren().add(v);
        getChildren().add(humanName);

        getChildren().add(comButton);
        computerType.getChildren().add(dumpButton);
        computerType.getChildren().add(normalButton);
        computerType.getChildren().add(smartButton);
        HBox vv = new HBox();
        vv.getChildren().add(labelcompcolorpicker);
        vv.getChildren().add(compcolorpicker);
        computerType.getChildren().add(vv);
        getChildren().add(computerType);

    }

    public JFXRadioButton getHumanButton() {
        return humanButton;
    }

    public VBox getHumanName() {
        return humanName;
    }

    public Label getLabel() {
        return label;
    }

    public JFXTextField getTextField() {
        return textField;
    }

    public JFXRadioButton getComButton() {
        return comButton;
    }

    public VBox getComputerType() {
        return computerType;
    }

    public JFXRadioButton getDumpButton() {
        return dumpButton;
    }

    public JFXRadioButton getNormalButton() {
        return normalButton;
    }

    public JFXRadioButton getSmartButton() {
        return smartButton;
    }

    public ToggleGroup getTypeToggle() {
        return typeToggle;
    }

    public ToggleGroup getComToggle() {
        return comToggle;
    }

    public Label getLabelhumancolorpicker() {
        return labelhumancolorpicker;
    }

    public Label getLabelcompcolorpicker() {
        return labelcompcolorpicker;
    }

    public int getColor() {
        if (comButton.isSelected()) {
            return (int)Long.parseLong(compcolorpicker.getValue().toString().substring(2, 10) ,16);
        }
        return (int)Long.parseLong(humancolorpicker.getValue().toString().substring(2, 10) ,16);
    }

}
