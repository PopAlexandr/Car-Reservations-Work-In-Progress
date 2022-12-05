package repo.file;

import domain.Reservation;
import repo.memory.IdentifiableRepoMem;

import java.io.*;
import java.util.ArrayList;

public class ReservationRepoBIN extends FileRepository<String, Reservation> {
    public ReservationRepoBIN(String fileName) {
        super(fileName);
    }

    @Override
    public void readFromFile() {
        var tr = new IdentifiableRepoMem<Reservation>();
        try (var in = new ObjectInputStream(new FileInputStream(this.fileName))) {
            var apps = (ArrayList<Reservation>) in.readObject();
            for (Reservation a : apps)
                tr.add(a.getID(), a);
            this.repo = tr.repo;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeToFile() {
        var apps = new ArrayList<>(this.repo.values());
        try (var out = new ObjectOutputStream(new FileOutputStream(this.fileName))) {
            out.writeObject(apps);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
