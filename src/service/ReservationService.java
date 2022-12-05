package service;


import domain.*;
import domain.Identifiable;
import repo.IRepository;
import repo.memory.IdentifiableRepoMem;

import java.time.LocalDate;
import java.util.ArrayList;

public class ReservationService extends IdentifiableRepoService<Reservation> {

    public ReservationService(IRepository<String, Reservation> link) {
        super(link);
    }


}
