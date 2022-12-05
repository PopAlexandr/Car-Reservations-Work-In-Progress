package repo.file;

import domain.Car;
import repo.memory.IdentifiableRepoMem;

import java.io.*;
import java.util.ArrayList;

public class CarRepoBIN extends FileRepository<String, Car> {
    public CarRepoBIN(String fileName) {
        super(fileName);
    }

    @Override
    public void readFromFile() {
        var tr = new IdentifiableRepoMem<Car>();
        try (var in = new ObjectInputStream(new FileInputStream(this.fileName))) {
            ArrayList<Car> pans = (ArrayList<Car>) in.readObject();
            for (Car a : pans)
                tr.add(a.getID(), a);
            this.repo = tr.repo;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeToFile() {
        var pans = new ArrayList<>(this.repo.values());
        try (var out = new ObjectOutputStream(new FileOutputStream(this.fileName))) {
            out.writeObject(pans);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
