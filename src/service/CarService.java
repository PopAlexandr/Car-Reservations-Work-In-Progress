package service;

import domain.Car;
import repo.IRepository;
import repo.memory.IdentifiableRepoMem;

public class CarService extends IdentifiableRepoService<Car> {
    public CarService(IRepository<String,Car> link) {
        super(link);
    }
}
