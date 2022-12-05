package repo.memory;

import domain.Car;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemoryRepositoryTest {

    static IdentifiableRepoMem<Car> repo;
    static Car c1;
    static Car c2;
    static Car c3;
    @BeforeAll
    static void setUpCars() {
        repo=new IdentifiableRepoMem<>();
        c1=new Car("1","Dacie","2002");
        c2=new Car("2","Golf","2006");
        c3=new Car("3","Passat","2013");
    }

    @BeforeEach
    void setUp(){
        repo=new IdentifiableRepoMem<>();
        repo.repo.put(c1.getID(), c1);
        repo.repo.put(c2.getID(), c2);

    }
    @Test
    void add() {
        repo.add(c3.getID(),c3);
        assert repo.repo.size()==3;
        assert repo.repo.containsKey(c3.getID());
        assert repo.repo.containsValue(c3);
        assert !repo.add(c3.getID(), c3);
    }

    @Test
    void remove() {
        repo.remove(c2.getID());
        assert repo.repo.size()==1;
        assert !repo.repo.containsKey(c2.getID());
    }

    @Test
    void update() {
        repo.update(c2.getID(), c3);
        assert repo.repo.size()==2;
        assert repo.repo.containsKey(c2.getID());
        assert repo.repo.containsValue(c3);
    }

    @Test
    void findById() {
        assert repo.findById(c1.getID()).equals(c1);
        assert repo.findById(c2.getID()).equals(c2);
        assert repo.findById(c3.getID()) == null;

    }

    @Test
    void getAll() {
        assert repo.getAll().size()==2;
        assert repo.getAll().contains(c1);
        assert repo.getAll().contains(c2);
        assert !repo.getAll().contains(c3);
    }

    @Test
    void size() { assert repo.size()==2;
    }
}