package dad.enviarEmail.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ResourceBundle;

import dad.enviarEmail.model.Model;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Controller implements Initializable {

	Model model=new Model();
		
	//View
	@FXML
    private HBox view;

    @FXML
    private GridPane gridPane;
    
    @FXML
    private VBox vBox;
    
    @FXML
    private CheckBox conexionCheck;

    @FXML
    private TextArea mensajeTA;

    @FXML
    private TextField servidorTF,puertoTF,remitenteTF,destinatarioTF,asuntoTF;

    @FXML
    private PasswordField contraseñaTF;
    
    @FXML
    private Button enviarB,vaciarB,cerrarB;
    
	boolean selected;
	
	public Controller() throws IOException{
		FXMLLoader loader=new FXMLLoader(getClass().getResource("/View.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		selected=conexionCheck.isSelected();
	}
	@FXML
	private void onEnviarButton(ActionEvent e) {
		try {
		model.getCorreo().setHostName(servidorTF.textProperty().get());
		model.getCorreo().setSmtpPort(Integer.parseInt(puertoTF.textProperty().get()));
		model.getCorreo().setAuthentication(remitenteTF.textProperty().get(), contraseñaTF.textProperty().get());
		model.getCorreo().setSSLOnConnect(selected);
		model.getCorreo().setFrom(remitenteTF.textProperty().get());
		model.getCorreo().setSubject(asuntoTF.textProperty().get());
		model.getCorreo().setMsg(mensajeTA.textProperty().get());
		model.getCorreo().addTo(destinatarioTF.textProperty().get());
		model.correo.send();
		successAlert();
		}catch(Exception ex) {
			errorAlert(ex);
		}
	}
	@FXML
	private void onVaciarButton(ActionEvent e) {
		mensajeTA.textProperty().set("");
		servidorTF.textProperty().set("");
		puertoTF.textProperty().set("");
		remitenteTF.textProperty().set("");
		contraseñaTF.textProperty().set("");
		destinatarioTF.textProperty().set("");
		asuntoTF.textProperty().set("");
		conexionCheck.setSelected(false); 
	}
	@FXML
	private void onCerrarButton(ActionEvent e) {
		Platform.exit();
	}
	
	private void errorAlert(Exception ex) {
		Alert alert=new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("No se pudo enviar el email.");
		alert.setContentText("Abra \"Mostrar detalles\" para obtener la información completa");
		
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("images/email-send-icon-32x32.png"));

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String exceptionText=sw.toString();
		
		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);
		
		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(textArea, 0, 0);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);

		alert.showAndWait();
	}
	private void successAlert() {
		Alert alert=new Alert(AlertType.INFORMATION);
		alert.setHeaderText("Mensaje enviado con éxito a \""+destinatarioTF.textProperty().get()+"\".");
		alert.setTitle("Mensaje enviado");
		
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("images/email-send-icon-32x32.png"));

		alert.showAndWait();

	}
	
	public HBox getView() {
		return this.view;
	}
}