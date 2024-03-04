package controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import exceptions.ADTException;
import exceptions.ToyException;
import model.adt.MyDict;
import model.adt.MyIStack;
import model.adt.MyList;
import model.adt.MyStack;
import model.adt.PrgState;
import model.statements.IStmt;
import model.values.RefValue;
import model.values.Value;
import repo.IRepository;

public class Controller {
    private IRepository repo;
    private Boolean displayFlag;
    private ExecutorService executor;

    private Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getAddrFromSymTable(Collection<Value> symTableVal, Collection<Value> heap) {
        return Stream.concat(heap.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddress();
                }),
                symTableVal.stream()
                        .filter(v -> v instanceof RefValue)
                        .map(v -> {
                            RefValue v1 = (RefValue) v;
                            return v1.getAddress();
                        }))
                .collect(Collectors.toList());
    }

    private void conservativeGarbageCollector(List<PrgState> programStateList) {
        var heap = Objects.requireNonNull(programStateList.stream()
                .map(p -> getAddrFromSymTable(p.getSymTable().getContent().values(),
                        p.getHeapTable().getContent().values()))
                .map(Collection::stream)
                .reduce(Stream::concat).orElse(null)).collect(Collectors.toList());
        programStateList.forEach(programState -> {
            programState.getHeapTable()
                    .setContent(safeGarbageCollector(heap, programStateList.get(0).getHeapTable().getContent()));
        });
    }

    public Controller(IRepository repo) {
        this.repo = repo;
        this.displayFlag = true;
        executor = Executors.newFixedThreadPool(2);
    }

    public void changeDisplayFlag() {
        this.displayFlag = !displayFlag;
    }

    public void newProgram(IStmt exStmt) {
        PrgState state;
        MyIStack<IStmt> stack = new MyStack<IStmt>();
        stack.push(exStmt);
        state = new PrgState(stack, new MyDict<String, Value>(), new MyList<Value>(), exStmt);
        repo.clear();
        repo.add(state);
    }

    public IRepository getRepo() {
        return repo;
    }

    public Boolean getDisplayFlag() {
        return displayFlag;
    }

    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> inPrgList) throws ToyException, IOException, InterruptedException {
        inPrgList.forEach(p -> {
            try {
                repo.logPrgStateExec(p);
            } catch (ToyException | IOException e) {
                e.getMessage();
            }
        });
        List<Callable<PrgState>> callList = inPrgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (() -> {
                    try {
                        return p.oneStep();
                    } catch (ADTException e) {
                        return null;
                    }
                }))
                .collect(Collectors.toList());

        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(p -> p != null)
                .collect(Collectors.toList());
        inPrgList.addAll(newPrgList);
        conservativeGarbageCollector(repo.getPrgList());

        inPrgList.forEach(p -> {
            try {
                repo.logPrgStateExec(p);
            } catch (ToyException | IOException e) {

                e.printStackTrace();
            }
        });
        repo.setPrgList(inPrgList);
    }

    public void allStep() throws ToyException, IOException, InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        while (prgList.size() > 0) {

            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        repo.setPrgList(prgList);
    }

}
