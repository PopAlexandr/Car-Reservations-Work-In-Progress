package repo.file;

import domain.Reservation;
import domain.Car;
import repo.memory.IdentifiableRepoMem;

import java.io.*;
import java.util.Map;

public class ReservationRepoCSV extends FileRepository<String, Reservation> {
    public ReservationRepoCSV(String fileName) {
        super(fileName);
    }

    @Override
    public void readFromFile() {
        IdentifiableRepoMem<Reservation> tr = new IdentifiableRepoMem<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] elems = line.split(",");
                if (elems.length != 3)
                    continue;
                Reservation a =Reservation.build(elems[0].strip(), elems[1].strip(), elems[2].strip());
                tr.add(a.getID(), a);
            }
            this.repo = tr.repo;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("Error while closing the file " + e);
                }
        }
    }

    @Override
    public void writeToFile() {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(this.fileName));
            for (Map.Entry<String, Reservation> entry : repo.entrySet()) {
                bw.write(entry.getValue().getID() + " , " + entry.getValue().getCarID() + " , " +
                        entry.getValue().getName());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert bw != null;
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

