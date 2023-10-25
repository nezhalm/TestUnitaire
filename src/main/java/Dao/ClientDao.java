package Dao;
import Entities.Client;
import Enum.*;

import java.util.Optional;

public interface ClientDao extends GlobaleDao<Client> {
    public Optional<Integer> update(Client client);
}
