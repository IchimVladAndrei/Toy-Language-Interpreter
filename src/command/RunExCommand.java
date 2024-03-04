package command;

import java.io.IOException;

import controller.Controller;
import exceptions.ToyException;

public class RunExCommand extends Command {
    private Controller controller;

    public RunExCommand(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            controller.allStep();

        } catch (ToyException | IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

}
