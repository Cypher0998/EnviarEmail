package dad.enviarEmail.app;

import dad.enviarEmail.controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application{

	private Controller controlador;
	
	public void start(Stage primaryStage) throws Exception {
		
		controlador=new Controller();
		
		Scene escena=new Scene(controlador.getView());
		
		primaryStage.setScene(escena);
		primaryStage.setTitle("Enviar Email");
		primaryStage.getIcons().add(new Image("images/email-send-icon-32x32.png"));
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}