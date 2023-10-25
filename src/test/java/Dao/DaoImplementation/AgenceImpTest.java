package Dao.DaoImplementation;

import Entities.Agence;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class AgenceImpTest {
    private static AgenceImp agenceImp;

    @BeforeAll
    public static void setUp() {
        agenceImp = new AgenceImp();
    }

    @Test
    public void getAllAgencies() {
        List<Agence> agences = agenceImp.afficheList();
        assertNotNull(agences);

        for (Agence a : agences) {
            System.out.println();
        }
    }
}
