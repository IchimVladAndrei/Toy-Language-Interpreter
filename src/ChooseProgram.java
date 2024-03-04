import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import exceptions.ToyException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.adt.MyDict;
import model.statements.IStmt;

public class ChooseProgram {
    private List<IStmt> statements = new LinkedList<>();
    private Stage menuStage;

    public void setMenuStage(Stage stage) {
        menuStage = stage;
    }

    public void addStatement(IStmt statement) {
        statements.add(statement);
        programsList.setItems(getProgramStatesList());
    }

    @FXML
    private Button proceedBtn;

    @FXML
    private ListView<String> programsList;

    @FXML
    private void initialize() {
        programsList.getItems().addAll("ab", "cd", "ef");
        programsList.setItems(getProgramStatesList());
    }

    @FXML
    void selectProgram(ActionEvent event) {
        int index = programsList.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            raiseAlert("Please select a program!");
            return;
        }

        try {
            launchProgram(index + 1);
        } catch (ToyException | IOException e) {

            e.printStackTrace();
        }
    }

    private void raiseAlert(String string) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(string);
        alert.show();
    }

    private ObservableList<String> getProgramStatesList() {
        return FXCollections.observableList(statements.stream().map(Object::toString).collect(Collectors.toList()));
    }

    private void launchProgram(int index) throws ToyException, IOException {
        IStmt statement = statements.get(index - 1);
        statement.typecheck(new MyDict<>());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("RunProgram.fxml"));
        GridPane layout = (GridPane) loader.load();// casted to GridPane
        RunProgram runProgramController = loader.getController();
        runProgramController.setProgramState(statement, index);
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        Scene secondScene = new Scene(layout, screenBounds.getWidth() * 2 / 3, screenBounds.getHeight());
        Stage newWindow = new Stage();
        newWindow.setTitle("Run program");
        newWindow.setScene(secondScene);
        newWindow.setX(menuStage.getX() + screenBounds.getWidth() / 3);
        newWindow.setY(menuStage.getY());
        newWindow.show();
    }

}
