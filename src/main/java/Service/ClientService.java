package Service;

import Dao.DaoImplementation.ClientImp;
import Entities.Client;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

public class ClientService {

    private final ClientImp clientImp;
    public ClientService(ClientImp clientImp) {
        this.clientImp = clientImp;
    }
    public List<Client> AllClients() {
        List<Client> clientList = clientImp.afficheList();
        return clientList;
    }

    public Optional<Client> chercher(String query) {
        Optional<Client> clientTrouve = clientImp.chercher(query);
        return clientTrouve;
    }
    public Optional<Client> insertClient(Client client) {
        return clientImp.ajouter(client);
    }

    public boolean update(Client client) {
        return clientImp.update(client);
    }

    public boolean chercherPourSupprimer(String code) {
        Optional<Client> clientTrouve = clientImp.chercher(code);
        return clientTrouve.map(clientASupprimer -> {
            clientImp.supprimer(clientASupprimer);
            return true;
        }).orElse(false);
    }







}
