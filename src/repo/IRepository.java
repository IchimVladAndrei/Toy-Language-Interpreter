package repo;

import java.io.IOException;
import java.util.List;

import exceptions.ToyException;
import model.adt.PrgState;

public interface IRepository {

    void add(PrgState e);

    void logPrgStateExec(PrgState program) throws ToyException, IOException;

    List<PrgState> getPrgList();

    public PrgState getPrgState();

    void setPrgList(List<PrgState> another);

    void clear();
}
