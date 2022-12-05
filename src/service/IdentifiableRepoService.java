package service;

import domain.Identifiable;
import exception.RepoException;
import repo.IRepository;

import java.util.ArrayList;

abstract public class IdentifiableRepoService<T extends Identifiable<String>> {

    private final IRepository<String,T> link;

    public IdentifiableRepoService(IRepository<String,T> link) {
        this.link = link;
    }

    @SafeVarargs
    public final void add(T... entities) throws RepoException {
        for (T e : entities)
            if (!link.add(e.getID(), e))
                throw new RepoException("Entity already exists");
    }

    @SafeVarargs
    public final void remove(T... entities) throws RepoException {
        for (T e : entities)
            if (!link.remove(e.getID()))
                throw new RepoException("Entity does not exist");
    }


    public void update(T entity) throws RepoException {
        if (link.update(entity.getID(), entity))
            throw new RepoException("Entity does not exist");
    }

    public T get(String id) throws RepoException {
        T e = link.findById(id);
        if (e == null)
            throw new RepoException("Entity does not exist");
        return e;
    }

    public ArrayList<T> get() {
        return (ArrayList<T>) link.getAll();
    }

    public boolean contains(String id) {
        return link.findById(id) != null;
    }

    public Integer size() {
        return link.size();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (T e : link.getAll())
            s.append(e.toString()).append("\n");
        return s.toString();
    }
}
