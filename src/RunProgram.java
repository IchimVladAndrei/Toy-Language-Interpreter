import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import java.util.stream.Collectors;

import controller.Controller;
import exceptions.ToyException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.adt.MyDict;
import model.adt.MyHeap;
import model.adt.MyIHeap;
import model.adt.MyIStack;
import model.adt.MyList;
import model.adt.MyStack;
import model.adt.PrgState;
import model.statements.IStmt;
import model.values.Value;
import repo.IRepository;
import repo.Repository;

class Pair<T1, T2> {
    T1 first;
    T2 second;

    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }
}

public class RunProgram {

    private Controller controller;
    List<PrgState> programStateList;

    @FXML
    private ListView<String> fileListView;

    @FXML
    private TableColumn<Pair<Integer, Value>, Integer> heapAddressColumn;

    @FXML
    private TableView<Pair<Integer, Value>> heapTableView;

    @FXML
    private TableColumn<Pair<Integer, Value>, String> heapValueColumn;

    @FXML
    private TextField idTextField;

    @FXML
    private Button oneStepBttn;

    @FXML
    private ListView<String> outputListView;

    @FXML
    private ListView<Integer> prgStateID;

    @FXML
    private ListView<String> stackListView;

    @FXML
    private TableView<Pair<String, Value>> symTableView;

    @FXML
    private TableColumn<Pair<String, Value>, String> symbolValueColumn;

    @FXML
    private TableColumn<Pair<String, Value>, String> symbolVarColumn;

    @FXML
    void runOneStep(ActionEvent event) {

        oneStep();
    }

    @FXML
    void initialize() {
        idTextField.setEditable(false);
        heapAddressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().first).asObject());
        heapValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().second.toString()));
        symbolVarColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().first));
        symbolValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().second.toString()));
        prgStateID.setOnMouseClicked(mouseEvent -> populate());
    }

    @FXML
    private void raiseAlert(String string) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(string);
        alert.show();
    }

    public void setProgramState(IStmt statement, int index) {
        String file = "log" + index + ".txt";
        MyIStack<IStmt> stack = new MyStack<>();
        stack.push(statement);
        PrgState state = new PrgState(stack, new MyDict<>(), new MyList<>(), new MyDict<>(), new MyHeap<>());
        IRepository repo = new Repository(state, file);
        repo.add(state);
        controller = new Controller(repo);
        programStateList = controller.getRepo().getPrgList();
    }

    private void oneStep() {
        if (controller == null) {
            raiseAlert("the program was not selected");
            return;
        }
        try {
            boolean programStatesLeft = Objects.requireNonNull(getCrtPrgState()).getExeStack().isEmpty();
            if (programStatesLeft) {
                raiseAlert("Nothing left to execute");

                return;
            }

            controller.oneStepForAllPrg(programStateList);
            populate();

        } catch (ToyException | IOException | InterruptedException | NullPointerException e) {
            raiseAlert(e.getMessage());

            return;
        }

    }

    private PrgState getCrtPrgState() {
        if (controller.getRepo().getPrgList().size() == 0)
            return null;
        if (prgStateID.getSelectionModel().getSelectedItem() == null) {
            return controller.getRepo().getPrgList().get(0);// by default daca nu ai cv selectat
        } else {
            return controller.getRepo().getPrgList().stream()
                    .filter(p -> p.getId() == prgStateID.getSelectionModel().getSelectedItem())
                    .collect(Collectors.toList())
                    .get(0);
        }

    }

    private void populate() {
        populateHeapTable();
        populatePrgStateID();
        populateFileTable();
        populateOutput();
        populateSymTable();
        populateExeStack();

    }

    private void populateHeapTable() {
        MyIHeap<Value> heapTable;
        if (controller.getRepo().getPrgList().size() > 0)
            heapTable = controller.getRepo().getPrgList().get(0).getHeapTable();
        else
            heapTable = new MyHeap<>();
        List<Pair<Integer, Value>> heapTableList = new ArrayList<>();
        for (Map.Entry<Integer, Value> entry : heapTable.getContent().entrySet())
            heapTableList.add(new Pair<>(entry.getKey(), entry.getValue()));
        heapTableView.setItems(FXCollections.observableList(heapTableList));
        heapTableView.refresh();
    }

    private void populateFileTable() {
        List<String> files;
        if (controller.getRepo().getPrgList().size() > 0)
            files = controller.getRepo().getPrgList().get(0).getFileTable().getContent().keySet().stream()
                    .map(Object::toString).collect(Collectors.toList());
        else
            files = new ArrayList<>();
        fileListView.setItems(FXCollections.observableList(files));
    }

    private void populateOutput() {
        List<String> output;
        if (controller.getRepo().getPrgList().size() > 0)
            output = controller.getRepo().getPrgList().get(0).getOut().getList().stream().map(Object::toString)
                    .collect(Collectors.toList());
        else
            output = controller.getRepo().getPrgState().getOut().getList().stream().map(Object::toString)
                    .collect(Collectors.toList());

        outputListView.setItems(FXCollections.observableList(output));
        outputListView.refresh();
    }

    private void populateSymTable() {
        PrgState state = getCrtPrgState();
        List<Pair<String, Value>> symTableList = new ArrayList<>();
        if (state != null)
            for (Map.Entry<String, Value> entry : state.getSymTable().getContent().entrySet())
                symTableList.add(new Pair<>(entry.getKey(), entry.getValue()));
        symTableView.setItems(FXCollections.observableList(symTableList));
        symTableView.refresh();
    }

    private void populateExeStack() {
        PrgState state = getCrtPrgState();
        List<String> execStackString = new ArrayList<>();

        if (state != null) {
            List<IStmt> statements = state.getExeStack().getReversed();
            for (IStmt statement : statements)
                execStackString.add(statement.toString());
        }
        stackListView.setItems(FXCollections.observableList(execStackString));
        stackListView.refresh();
    }

    private void populatePrgStateID() {
        List<PrgState> prgStates = controller.getRepo().getPrgList();
        List<Integer> idList = prgStates.stream().filter(p -> !p.getExeStack().isEmpty()).map(p -> p.getId())
                .collect(Collectors.toList());
        prgStateID.setItems(FXCollections.observableList(idList));
        idTextField.setText("" + prgStates.size());
    }
}
