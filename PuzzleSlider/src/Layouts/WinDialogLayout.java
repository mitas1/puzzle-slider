package Layouts;

import Global.ImageRepository;
import Global.StringRepository;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;

public class WinDialogLayout {

	public void setupLayout( Dialog<ButtonType> dialog ) {
		dialog.setTitle( StringRepository.LABEL_WIN_DIALOG_HEADER );
		dialog.setHeaderText( StringRepository.LABEL_WIN_DIALOG_HEADER_LONG );
		dialog.setGraphic( new ImageView( ImageRepository.FIREWORKS ) );
		
		ButtonType newGameButton = new ButtonType( StringRepository.NEW_GAME, ButtonData.OK_DONE );
		ButtonType menuButton = new ButtonType( StringRepository.MENU_LABEL, ButtonData.OK_DONE ); 
		dialog.getDialogPane().getButtonTypes().addAll( newGameButton, menuButton );
	}
	
}
