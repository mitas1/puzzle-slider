package UiObjects;

import Global.NumericalRepository;
import Global.StringRepository;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;

public class NewGameDialogUiObjects {
	
	public ButtonType confirmButton;
	public ButtonType cancelButton;
	public Button imageChooserButton;

	public RadioButton numberedTilesRB;
	public RadioButton imageTilesRB;

	public Slider gameSizeSlider;
	
	public SimpleBooleanProperty imageChosen;

	public NewGameDialogUiObjects() {
		initializeSlider();
		initializeRadiobuttons();

		confirmButton = new ButtonType( StringRepository.CONFIRM, ButtonData.OK_DONE );
		cancelButton = new ButtonType( StringRepository.CANCEL, ButtonData.CANCEL_CLOSE );
		imageChooserButton = new Button();

		initializeButtonDependencies();
	}

	protected void initializeSlider() {
		gameSizeSlider = new Slider( 
				NumericalRepository.GAME_SIZE_MIN, 
				NumericalRepository.GAME_SIZE_MAX, 
				NumericalRepository.GAME_SIZE_MIN 
				);

		gameSizeSlider.valueProperty().addListener( (obs, oldval, newVal) ->
		gameSizeSlider.setValue(newVal.intValue())
				);

		gameSizeSlider.setShowTickLabels(true);
		gameSizeSlider.setShowTickMarks(true);
		gameSizeSlider.setBlockIncrement(1);

		gameSizeSlider.setMajorTickUnit(2);
		gameSizeSlider.setMinorTickCount(1);
	}

	protected void initializeRadiobuttons() {
		ToggleGroup radioGroup = new ToggleGroup();
		numberedTilesRB = new RadioButton();
		imageTilesRB = new RadioButton();
		numberedTilesRB.setToggleGroup( radioGroup );
		imageTilesRB.setToggleGroup( radioGroup );
		numberedTilesRB.setSelected( true );
	}

	protected void initializeButtonDependencies() {
		imageChosen = new SimpleBooleanProperty( false );
		imageChooserButton.disableProperty().bind( imageTilesRB.selectedProperty().not() );
	}
}
