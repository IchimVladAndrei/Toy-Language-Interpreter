package repo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import exceptions.ToyException;
import model.adt.PrgState;

public class Repository implements IRepository {

    List<PrgState> repo;
    String logFilePath;
    PrgState prgState;

    public Repository(PrgState prgState, String logFilePath) {
        repo = new LinkedList<PrgState>();
        this.logFilePath = logFilePath;
        this.prgState = prgState;
    }

    @Override
    public void add(PrgState e) {
        repo.add(e);
    }

    public List<PrgState> getPrgList() {
        return repo;
    }

    @Override
    public void clear() {
        this.repo.clear();
    }

    @Override
    public String toString() {
        return "Repository [repo=" + repo + "]";
    }

    @Override
    public void logPrgStateExec(PrgState program) throws ToyException, IOException {

        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
        logFile.println(program.toString());
        logFile.flush();
        logFile.close();
    }

    @Override
    public void setPrgList(List<PrgState> another) {
        this.repo = another;
    }

    public PrgState getPrgState() {
        return prgState;
    }

}
