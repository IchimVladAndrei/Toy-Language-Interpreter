import exceptions.ToyException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.adt.MyDict;
import model.expressions.ArithmeticExpr;
import model.expressions.LogicExpr;
import model.expressions.ReadHeapExpr;
import model.expressions.RelationalExpr;
import model.expressions.ValueExpr;
import model.expressions.VariableExpr;
import model.statements.AllocateHeapStmt;
import model.statements.AssignStmt;
import model.statements.CloseRFile;
import model.statements.CompStmt;
import model.statements.CondStmt;
import model.statements.ForkStmt;
import model.statements.IStmt;
import model.statements.IfStatement;
import model.statements.OpenRFile;
import model.statements.PrintStmt;
import model.statements.ReadFile;
import model.statements.VarDeclIStmt;
import model.statements.WhileStmt;
import model.statements.WriteHeapStmt;
import model.types.BoolType;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.values.BooleanValue;
import model.values.IntValue;
import model.values.StringValue;

public class Main extends Application {
        ChooseProgram chooseProgramController;

        @Override
        public void start(Stage primaryStage) throws Exception {
                try {
                        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChooseProgram.fxml"));

                        GridPane root = (GridPane) loader.load();
                        chooseProgramController = loader.getController();
                        addStatementsController();
                        chooseProgramController.setMenuStage(primaryStage);

                        Scene scene = new Scene(root, screenBounds.getWidth() / 3, screenBounds.getHeight());
                        // css on scene
                        primaryStage.setTitle("Toy Languange Interpreter");
                        primaryStage.setScene(scene);
                        primaryStage.setX(0);
                        primaryStage.setY(0);
                        primaryStage.show();
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        public static void main(String[] args) {
                launch(args);
        }

        private void addStatementsController() {
                IStmt[] loopArray = new IStmt[] { ex2, ex3, ex4, exFile, exRelation, exWhile, exAllocate,
                                exHeapRead,
                                exHeapWrite, exGarbage, exFork, exFork2, exCond };
                for (IStmt stmt : loopArray) {
                        try {
                                stmt.typecheck(new MyDict<>());
                                chooseProgramController.addStatement(stmt);
                        } catch (ToyException e) {
                                raiseAlert(e.getMessage());
                        }

                }
        }

        @FXML
        private void raiseAlert(String string) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(string);
                alert.show();
        }

        IStmt exCond = new CompStmt(new VarDeclIStmt("a", new RefType(new IntType())), new CompStmt(
                        new VarDeclIStmt("b", new RefType(new IntType())),
                        new CompStmt(new VarDeclIStmt("v", new IntType()), new CompStmt(
                                        new AllocateHeapStmt("a", new ValueExpr(new IntValue(0))),
                                        new CompStmt(new AllocateHeapStmt("b", new ValueExpr(new IntValue(0))),
                                                        new CompStmt(new WriteHeapStmt("a",
                                                                        new ValueExpr(new IntValue(1))),
                                                                        new CompStmt(new WriteHeapStmt("b",
                                                                                        new ValueExpr(new IntValue(2))),
                                                                                        new CompStmt(new CondStmt(
                                                                                                        new RelationalExpr(
                                                                                                                        new ReadHeapExpr(
                                                                                                                                        new VariableExpr(
                                                                                                                                                        "a")),
                                                                                                                        new ReadHeapExpr(
                                                                                                                                        new VariableExpr(
                                                                                                                                                        "b")),
                                                                                                                        "<"),
                                                                                                        new ValueExpr(new IntValue(
                                                                                                                        100)),
                                                                                                        new ValueExpr(new IntValue(
                                                                                                                        200)),
                                                                                                        "v"),
                                                                                                        new CompStmt(new PrintStmt(
                                                                                                                        new VariableExpr(
                                                                                                                                        "v")),
                                                                                                                        new CompStmt(new CondStmt(
                                                                                                                                        new RelationalExpr(
                                                                                                                                                        new ArithmeticExpr(
                                                                                                                                                                        new ReadHeapExpr(
                                                                                                                                                                                        new VariableExpr(
                                                                                                                                                                                                        "b")),
                                                                                                                                                                        new ValueExpr(new IntValue(
                                                                                                                                                                                        2)),
                                                                                                                                                                        '-'),
                                                                                                                                                        new ReadHeapExpr(
                                                                                                                                                                        new VariableExpr(
                                                                                                                                                                                        "a")),
                                                                                                                                                        ">"),
                                                                                                                                        new ValueExpr(new IntValue(
                                                                                                                                                        100)),
                                                                                                                                        new ValueExpr(new IntValue(
                                                                                                                                                        200)),
                                                                                                                                        "v"),
                                                                                                                                        new PrintStmt(new VariableExpr(
                                                                                                                                                        "v"))))))))))));

        IStmt ex = new CompStmt(new VarDeclIStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("v", new ValueExpr(new BooleanValue(false))),
                                        new PrintStmt(new VariableExpr("v"))));

        IStmt ex4 = new CompStmt(new VarDeclIStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("v", new ValueExpr(new IntValue(2))),
                                        new PrintStmt(new LogicExpr(new ValueExpr(new BooleanValue(true)),
                                                        new ValueExpr(new BooleanValue(false)), '|'))));

        IStmt ex2 = new CompStmt(new VarDeclIStmt("a", new IntType()), new CompStmt(
                        new VarDeclIStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a",
                                        new ArithmeticExpr(new ValueExpr(new IntValue(2)),
                                                        new ArithmeticExpr(new ValueExpr(new IntValue(3)),
                                                                        new ValueExpr(new IntValue(5)), '*'),
                                                        '+')),
                                        new CompStmt(new AssignStmt("b",
                                                        new ArithmeticExpr(new VariableExpr("a"),
                                                                        new ValueExpr(new IntValue(1)), '+')),
                                                        new PrintStmt(new VariableExpr("b"))))));

        IStmt ex3 = new CompStmt(new VarDeclIStmt("a", new BoolType()),
                        new CompStmt(new VarDeclIStmt("v", new IntType()),
                                        new CompStmt(new AssignStmt("a", new ValueExpr(new BooleanValue(true))),
                                                        new CompStmt(
                                                                        new IfStatement(new AssignStmt("v",
                                                                                        new ValueExpr(new IntValue(
                                                                                                        2))),
                                                                                        new AssignStmt("v",
                                                                                                        new ValueExpr(new IntValue(
                                                                                                                        3))),
                                                                                        new VariableExpr("a")),
                                                                        new PrintStmt(new VariableExpr(
                                                                                        "v"))))));

        IStmt exFile = new CompStmt(new VarDeclIStmt("varf", new StringType()), new CompStmt(
                        new AssignStmt("varf", new ValueExpr(new StringValue("test.in"))),
                        new CompStmt(new OpenRFile(new VariableExpr("varf")), new CompStmt(
                                        new VarDeclIStmt("varc", new IntType()),
                                        new CompStmt(new ReadFile(new VariableExpr("varf"), "varc"),
                                                        new CompStmt(new PrintStmt(new VariableExpr("varc")),
                                                                        new CompStmt(new ReadFile(
                                                                                        new VariableExpr(
                                                                                                        "varf"),
                                                                                        "varc"),
                                                                                        new CompStmt(new PrintStmt(
                                                                                                        new VariableExpr(
                                                                                                                        "varc")),
                                                                                                        new CloseRFile(new VariableExpr(
                                                                                                                        "varf"))))))))));

        IStmt exRelation = new CompStmt(new VarDeclIStmt("a", new IntType()), new CompStmt(
                        new VarDeclIStmt("b", new IntType()),
                        new CompStmt(new VarDeclIStmt("max", new IntType()), new CompStmt(new AssignStmt("a",
                                        new ValueExpr(new IntValue(11))),
                                        new CompStmt(new AssignStmt("b", new ValueExpr(new IntValue(15))),
                                                        new CompStmt(new IfStatement(
                                                                        new AssignStmt("max",
                                                                                        new VariableExpr(
                                                                                                        "a")),
                                                                        new AssignStmt("max",
                                                                                        new VariableExpr(
                                                                                                        "b")),
                                                                        new RelationalExpr(
                                                                                        new VariableExpr("a"),
                                                                                        new VariableExpr("b"),
                                                                                        ">=")),
                                                                        new PrintStmt(new VariableExpr(
                                                                                        "max"))))))));
        IStmt exWhile = new CompStmt(new VarDeclIStmt("x", new IntType()),
                        new CompStmt(new AssignStmt("x", new ValueExpr(new IntValue(6))), new WhileStmt(
                                        new RelationalExpr(new VariableExpr("x"),
                                                        new ValueExpr(new IntValue(0)), "!="),
                                        new CompStmt(new AssignStmt("x",
                                                        new ArithmeticExpr(new VariableExpr("x"),
                                                                        new ValueExpr(new IntValue(1)), '-')),
                                                        new PrintStmt(new VariableExpr("x"))))));

        IStmt exAllocate = new CompStmt(new VarDeclIStmt("v", new RefType(new IntType())), new CompStmt(
                        new AllocateHeapStmt("v", new ValueExpr(new IntValue(20))),
                        new CompStmt(new VarDeclIStmt("a", new RefType(new RefType(new IntType()))),
                                        new CompStmt(new AllocateHeapStmt("a", new VariableExpr("v")),
                                                        new CompStmt(new PrintStmt(new VariableExpr("a")),
                                                                        new PrintStmt(new VariableExpr(
                                                                                        "v")))))));

        IStmt exHeapRead = new CompStmt(new VarDeclIStmt("v", new RefType(new IntType())), new CompStmt(
                        new AllocateHeapStmt("v", new ValueExpr(new IntValue(20))),
                        new CompStmt(new VarDeclIStmt("a", new RefType(new RefType(new IntType()))),
                                        new CompStmt(new AllocateHeapStmt("a", new VariableExpr("v")),
                                                        new CompStmt(new PrintStmt(
                                                                        new ReadHeapExpr(new VariableExpr(
                                                                                        "v"))),
                                                                        new PrintStmt(new ArithmeticExpr(
                                                                                        new ReadHeapExpr(
                                                                                                        new ReadHeapExpr(
                                                                                                                        new VariableExpr(
                                                                                                                                        "a"))),
                                                                                        new ValueExpr(new IntValue(
                                                                                                        5)),
                                                                                        '+')))))));

        IStmt exHeapWrite = new CompStmt(new VarDeclIStmt("v", new RefType(new IntType())), new CompStmt(
                        new AllocateHeapStmt("v", new ValueExpr(new IntValue(20))),
                        new CompStmt(new PrintStmt(new ReadHeapExpr(new VariableExpr("v"))), new CompStmt(
                                        new WriteHeapStmt("v", new ValueExpr(new IntValue(30))),
                                        new PrintStmt(new ArithmeticExpr(
                                                        new ReadHeapExpr(new VariableExpr("v")),
                                                        new ValueExpr(new IntValue(5)), '+'))))));
        IStmt exGarbage = new CompStmt(new VarDeclIStmt("v", new RefType(new IntType())), new CompStmt(
                        new AllocateHeapStmt("v", new ValueExpr(new IntValue(20))),
                        new CompStmt(new VarDeclIStmt("a", new RefType(new RefType(new IntType()))),
                                        new CompStmt(new AllocateHeapStmt("a", new VariableExpr("v")),
                                                        new CompStmt(new AllocateHeapStmt("v",
                                                                        new ValueExpr(new IntValue(30))),
                                                                        new PrintStmt(new ReadHeapExpr(
                                                                                        new ReadHeapExpr(
                                                                                                        new VariableExpr(
                                                                                                                        "a")))))))));

        IStmt exFork = new CompStmt(new VarDeclIStmt("v", new IntType()), new CompStmt(
                        new VarDeclIStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExpr(new IntValue(10))),
                                        new CompStmt(new AllocateHeapStmt("a", new ValueExpr(new IntValue(22))),
                                                        new CompStmt(new ForkStmt(new CompStmt(
                                                                        new WriteHeapStmt("a", new ValueExpr(
                                                                                        new IntValue(30))),
                                                                        new CompStmt(new AssignStmt("v",
                                                                                        new ValueExpr(new IntValue(
                                                                                                        32))),
                                                                                        new CompStmt(new PrintStmt(
                                                                                                        new VariableExpr(
                                                                                                                        "v")),
                                                                                                        new PrintStmt(new ReadHeapExpr(
                                                                                                                        new VariableExpr(
                                                                                                                                        "a"))))))),
                                                                        new CompStmt(new PrintStmt(
                                                                                        new VariableExpr("v")),
                                                                                        new PrintStmt(new ReadHeapExpr(
                                                                                                        new VariableExpr(
                                                                                                                        "a")))))))));

        IStmt exFork2 = new CompStmt(new VarDeclIStmt("a", new RefType(new IntType())), new CompStmt(
                        new VarDeclIStmt("counter", new IntType()),
                        new WhileStmt(new RelationalExpr(new VariableExpr("counter"),
                                        new ValueExpr(new IntValue(10)), "<"),
                                        new CompStmt(new ForkStmt(new ForkStmt(new CompStmt(
                                                        new AllocateHeapStmt("a",
                                                                        new VariableExpr("counter")),
                                                        new PrintStmt(new ReadHeapExpr(
                                                                        new VariableExpr("a")))))),
                                                        new AssignStmt("counter", new ArithmeticExpr(
                                                                        new VariableExpr("counter"),
                                                                        new ValueExpr(new IntValue(1)),
                                                                        '+'))))));
}
