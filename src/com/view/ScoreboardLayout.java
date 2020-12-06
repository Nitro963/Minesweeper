package com.view;

import com.jfoenix.controls.JFXButton;
import com.util.upgrade.FileManger;
import com.util.upgrade.GameReg;
import com.util.upgrade.ScoreBoardReg;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
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

public class ScoreboardLayout extends StackPane {

    protected JFXButton backButton;
    protected ObservableList<ScoreBoardReg> info;
    protected TableView<ScoreBoardReg> table;
    
    
    public void updateScoreBoard() {
    	info.clear();
        for (GameReg reg : FileManger.scoreBoardList) 
			info.add(new ScoreBoardReg(reg.getGameId(), reg.getGame()));	
        table.setItems(info);
    }
    
    @SuppressWarnings("unchecked")
	public ScoreboardLayout() {
        super();
        Label label1 = new Label("Score Board");
        table = new TableView<ScoreBoardReg>();
        backButton = new JFXButton("Back");
        backButton.setPrefSize(70, 50);
        backButton.setTextFill(Color.BLUE);
        backButton.setFont(Font.font("Aharoni", FontWeight.BOLD, 15));
        backButton.setStyle("-fx-background-radius: 5em; " + "-fx-background-color: orange;");
        backButton.setFocusTraversable(false);

        label1.setFont(Font.font("Aharoni", FontWeight.NORMAL, 40));

        label1.setTextFill(Color.BROWN);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<ScoreBoardReg, String> playerNameCol
                = new TableColumn<ScoreBoardReg, String>("    Player Name    ");
        playerNameCol.setResizable(false);

        TableColumn<ScoreBoardReg, Integer> playerScore
                = new TableColumn<>("     Score    ");
        
        playerScore.setResizable(false);
        TableColumn<ScoreBoardReg, Integer> idGame
                = new TableColumn<>("    Game ID   ");
        idGame.setResizable(false);
        idGame.setPrefWidth(200.0);
        TableColumn<ScoreBoardReg, Integer> gameduration
                = new TableColumn<>("    Game Duration    ");
        gameduration.setResizable(false);

        TableColumn<ScoreBoardReg, JFXButton> replay
                = new TableColumn<>("Action");
        replay.setResizable(false);

        playerNameCol.setCellValueFactory(cellData->{
        	ScoreBoardReg reg = cellData.getValue();
        	return new SimpleStringProperty(reg.getGame().getWinner().toString());
        });
        
        playerScore.setCellValueFactory(cellData -> {
            ScoreBoardReg reg = cellData.getValue();
            return new SimpleIntegerProperty(reg.getGame().getWinner().getScore().getScore()).asObject();
        });
                
        idGame.setCellValueFactory(cellData->{
        	ScoreBoardReg reg = cellData.getValue();
        	return new SimpleIntegerProperty(reg.getGameId()).asObject();
        });
        
        gameduration.setCellValueFactory(cellData->{
        	ScoreBoardReg reg = cellData.getValue();
        	return new SimpleIntegerProperty((int) (reg.getGame().getGameTime()/1000)).asObject();
        });
        
        replay.setCellValueFactory(new PropertyValueFactory<>("replayButton"));

        playerScore.setSortType(TableColumn.SortType.DESCENDING);
        playerNameCol.setSortType(TableColumn.SortType.ASCENDING);

        playerNameCol.prefWidthProperty().bind(table.widthProperty().divide(5)); // w * 1/4
        playerScore.prefWidthProperty().bind(table.widthProperty().divide(5)); // w * 1/2
        idGame.prefWidthProperty().bind(table.widthProperty().divide(5));
        gameduration.prefWidthProperty().bind(table.widthProperty().divide(5));
        replay.prefWidthProperty().bind(table.widthProperty().divide(5));
        
        info = FXCollections.observableArrayList();
        // Display row data
        updateScoreBoard();
        
        table.getColumns().addAll(playerNameCol, idGame, gameduration, playerScore, replay);
        label1.setAlignment(Pos.CENTER);
        HBox hbox = new HBox(10);
        VBox vbox = new VBox(10);
        Label space = new Label("                                                                 ");
        Label space1 = new Label("                                                ");
        Label space2 = new Label("                                                 ");
        Label space3 = new Label("                                             ");
        hbox.getChildren().addAll(label1, space, space1, space2, space3, backButton);
        vbox.getChildren().addAll(hbox, table);
        setPadding(new Insets(100, 100, 100, 100));
        getChildren().add(vbox);
        
        getStylesheets().add(ScoreboardLayout.class.getResource("tableCSS.css").toExternalForm());
        
        setBackground(new Background(new BackgroundImage(new Image("new_background.jpg"), BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
    }

    public final JFXButton getBackButton() {
        return backButton;
    }


    public final ObservableList<ScoreBoardReg> getInfo() {
		return info;
	}




    
}
