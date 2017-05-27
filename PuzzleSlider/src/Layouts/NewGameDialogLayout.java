package Layouts;

import Global.StringRepository;
import Renderer.JavaFxUtils;
import UiObjects.NewGameDialogUiObjects;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class NewGameDialogLayout {

	public void setLayout( NewGameDialogUiObjects uiObjects, Dialog<ButtonType> parent ) {
		DialogPane dialogPane = parent.getDialogPane();
		GridPane contentPane = new GridPane();
		
		setButtonLabels( uiObjects );
		addContentLabels( contentPane );
		initDialogButtons( dialogPane, uiObjects );
		setVisible( uiObjects, contentPane );
		
		dialogPane.setContent( contentPane );
	}
	
	protected void setButtonLabels( NewGameDialogUiObjects uiObjects ) {
		JavaFxUtils.setObjectLabel( uiObjects.imageChooserButton, StringRepository.CHOOSE_IMAGE );
		JavaFxUtils.setObjectLabel( uiObjects.imageTilesRB, StringRepository.LABEL_NG_DIALOG_RB_IMG );
		JavaFxUtils.setObjectLabel( uiObjects.numberedTilesRB, StringRepository.LABEL_NG_DIALOG_RB_NUM );
	}
	
	protected void addContentLabels( GridPane contentPane ) {
		contentPane.setPadding(new Insets(10));
        contentPane.setHgap(10);
        contentPane.setVgap(10);

        contentPane.add(new Label(StringRepository.LABEL_NG_DIALOG_SIZE),0,0);
        contentPane.add(new Label(StringRepository.LABEL_NG_DIALOG_TYPE),0,1);
        contentPane.add(new Label(StringRepository.LABEL_NG_DIALOG_PIC),0,3);
	}
	
	protected void setVisible( NewGameDialogUiObjects uiObjects, GridPane contentPane ) {
		contentPane.add(uiObjects.gameSizeSlider,1,0);
		contentPane.add(uiObjects.numberedTilesRB,1,1);
	    contentPane.add(uiObjects.imageTilesRB,1,2);
	    contentPane.add(uiObjects.imageChooserButton,1,3);
	}
	
	protected void initDialogButtons( DialogPane pane, NewGameDialogUiObjects uiObjects ) {
		pane.getButtonTypes().setAll(uiObjects.confirmButton, uiObjects.cancelButton);
        Button confirmButton = (Button) pane.lookupButton(uiObjects.confirmButton);
		confirmButton.disableProperty().bind( uiObjects.imageTilesRB.selectedProperty().and( uiObjects.imageChosen.not() ) );
	}
	
}
