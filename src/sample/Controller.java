package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.util.Optional;


public class Controller {

    @FXML
    private TextArea text;
    @FXML
    private MenuItem copiar;
    @FXML
    private MenuItem tallar;
    @FXML
    private MenuItem copiar1;
    @FXML
    private MenuItem tallar1;


    public void clickSortir(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void clickCopiar(ActionEvent actionEvent) {
        text.copy();
    }

    public void clickTallar(ActionEvent actionEvent) {
        text.cut();
    }

    public void clickEnganxar(ActionEvent actionEvent) {
        text.paste();
    }

    public void clickDesfer(ActionEvent actionEvent) {
        text.undo();
    }

    public void clickFont(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quina font de text vols?");
        alert.setHeaderText("Aqui tens 3 opcions!");
        alert.setContentText("Escull la teva opcio.");

        ButtonType freeMono = new ButtonType("FreeMono");
        ButtonType freeSans = new ButtonType("FreeSans");
        ButtonType rsfs10 = new ButtonType("rsfs10");
        ButtonType cancel = new ButtonType("Cancel" , ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(freeMono , freeSans , rsfs10 , cancel);

        Optional<ButtonType> result = alert.showAndWait();
        double tamany = text.getFont().getSize();

        if (result.get() == freeMono) {
            text.setFont(Font.font("FreeMono" , tamany));
        } else if (result.get() == freeSans) {
            text.setFont(Font.font("FreeSans" , tamany));
        } else if (result.get() == rsfs10) {
            text.setFont(Font.font("rsfs10" ,  tamany));
        }
    }

    public void clickTamany(ActionEvent actionEvent) {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Tamany del text");
        dialog.setHeaderText("Entra el tamany del text que vols");
        dialog.setContentText("Entra el nombre: ");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String font = text.getFont().getFamily();
            text.setFont(Font.font(font , Double.parseDouble(result.get())));
        }
    }

    public void clickAjuda(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dialeg d'informacio");
        alert.setHeaderText("Sobre l'Editor de text");
        alert.setContentText("Espero que us agradi, encara que esta a fase alpha encara.");

        alert.showAndWait();
    }

    public void clickEditar(Event actionEvent) {

        if (text.getSelectedText().isEmpty()) {
            copiar.setDisable(true);
            tallar.setDisable(true);
            copiar1.setDisable(true);
            tallar1.setDisable(true);
        } else {
            copiar.setDisable(false);
            tallar.setDisable(false);
            copiar1.setDisable(false);
            tallar1.setDisable(false);
        }
    }

    public void clickOpen(ActionEvent actionEvent) throws IOException {

        Stage stage = (Stage) text.getScene().getWindow();
        String totText = "";

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Obrir un arxiu");
        File file = fileChooser.showOpenDialog(stage);

        FileReader fr = new FileReader(file);
        BufferedReader bf = new BufferedReader(fr);

        while (bf.ready()) {
            totText += bf.readLine() + "\n";
        }

        text.setText(totText);

        stage.setTitle(file.getName());


    }

    public void clickGuardar(ActionEvent actionEvent) {

        Stage stage = (Stage) text.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar com...");

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("txt" , "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showSaveDialog(stage);

        String totText = text.getText();

        try {
            file.createNewFile();
            stage.setTitle(file.getName());

            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(totText);

            bw.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


    }
}
