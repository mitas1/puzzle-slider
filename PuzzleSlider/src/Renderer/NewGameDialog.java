package Renderer;

import Controller.PuzzleSlider;
import javafx.scene.image.Image;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.net.MalformedURLException;

public class NewGameDialog extends Dialog {
    Window owner;
    PuzzleSlider mController;
    Renderer renderer;
    public NewGameDialog(Window owner, PuzzleSlider controller, Renderer renderer) {
        this.owner = owner;
        this.mController = controller;
        this.renderer = renderer;
        this.initOwner(owner);
        this.setResizable(false);
        this.setTitle("Setup Game");
        this.getDialogPane().setContent(new NewGamePane(this));
    }
    private class NewGamePane extends GridPane {
        Image img;
        public NewGamePane(NewGameDialog dialog){
            DialogPane pane = dialog.getDialogPane();

            Slider slider = new Slider(Renderer.minGameSize,Renderer.maxGameSize,4);
            setupSlider(slider);
            ToggleGroup group = new ToggleGroup();
            RadioButton numbers = new RadioButton("numbers");
            numbers.setToggleGroup(group);
            numbers.setSelected(true);
            RadioButton picture = new RadioButton("picture");
            picture.setToggleGroup(group);

            SimpleBooleanProperty pictureProperty = new SimpleBooleanProperty(picture.isSelected());
            SimpleBooleanProperty pictureChosenProperty = new SimpleBooleanProperty(img != null);

            picture.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                    if (isNowSelected) {
                        pictureProperty.set(true);
                    } else {
                        pictureProperty.set(false);
                    }
                }
            });

            Button loadPicBtn = new Button("Load Picture");
            loadPicBtn.disableProperty().bind(pictureProperty.not());
            loadPicBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    final FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Choose a Picture");
                    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Pictures (*.jpg,*.jpeg,*.png)", "*.jpg","*.png","*.jpeg"));
                    final File file = fileChooser.showOpenDialog(owner);
                    if (file != null && file.canRead() && keepFile(file)) {
                        pictureChosenProperty.set(true);
                    }
                }
            });

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            pane.getButtonTypes().setAll(okButton, cancelBtn);
            Button okBtn = (Button) pane.lookupButton(okButton);
            okBtn.disableProperty().bind(pictureProperty.and(pictureChosenProperty.not()));
            okBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int sliderValue = ((Double)slider.getValue()).intValue();
                    mController.startNewGame(sliderValue);
                    renderer.setPictureGame(img);
                }
            });

            this.setPadding(new Insets(10));
            this.setHgap(10);
            this.setVgap(10);

            this.add(new Label("Game size"),0,0);
            this.add(slider,1,0);
            this.add(new Label("Game type"),0,1);
            this.add(numbers,1,1);
            this.add(picture,1,2);
            this.add(new Label("Choose a picture"),0,3);
            this.add(loadPicBtn,1,3);
        }

        private boolean keepFile(File file) {
            try {
                img = new Image(file.toURI().toURL().toExternalForm(),Renderer.canvasWidth,Renderer.canvasHeight, false,false);
            } catch (MalformedURLException e) {
                return false;
            }
            return true;
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
