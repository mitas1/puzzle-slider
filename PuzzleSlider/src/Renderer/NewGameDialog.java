package Renderer;

import Controller.PuzzleSlider;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class NewGameDialog extends Dialog {
    Window owner;
    PuzzleSlider mController;
    public NewGameDialog(Window owner,PuzzleSlider control) {
        this.owner=owner;
        this.mController = control;
        this.initOwner(owner);
        this.setResizable(false);
        this.setTitle("Setup Game");
        this.getDialogPane().setContent(new NewGamePane(this));
    }
    private class NewGamePane extends GridPane {
        public NewGamePane(NewGameDialog dialog){
            DialogPane pane = dialog.getDialogPane();

            Slider slider = new Slider(Renderer.minGameSize,Renderer.maxGameSize,4);
            setupSlider(slider);

            Button loadPicBtn = new Button("Load Picture");
            loadPicBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override //TODO
                public void handle(ActionEvent event) {
                    final FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Choose a Picture");
                    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Pictures", "*.jpg","*.png","*.jpeg"));
                    final File file = fileChooser.showOpenDialog(owner);
                    if (file == null) {
                        return;
                    }
                    if (file.canRead() || file.setReadable(true)) {
                        //TODO
                    } else {
                        //TODO some exception ?
                    }


                }
            });


            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            pane.getButtonTypes().setAll(okButton, cancelBtn);
            Button okBtn = (Button) pane.lookupButton(okButton);
            //TODO ??
            okBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //Start new Game with slider value
                    int sliderValue = ((Double)slider.getValue()).intValue();
                    mController.startNewGame(sliderValue);
                    //mController.startNewGame(sliderValue,new File(""));

                }
            });


            this.setPadding(new Insets(10));
            this.setHgap(10);
            this.setVgap(10);

            this.add(new Label("Game size"),0,0);
            this.add(slider,1,0);
            this.add(new Label("Choose a picture"),0,1);
            this.add(loadPicBtn,1,1);


        }
        private void setupSlider(Slider slider){
            slider.valueProperty().addListener((obs, oldval, newVal) ->
                    slider.setValue(newVal.intValue()));

            slider.setShowTickLabels(true);
            slider.setShowTickMarks(true);
            slider.setBlockIncrement(1);

            slider.setMajorTickUnit(2);
            slider.setMinorTickCount(1);

        }



    }


}
