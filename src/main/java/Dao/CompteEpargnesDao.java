package Dao;
import Enum.*;
import Enum.TypeOperation;
import Entities.*;

import java.util.List;
import java.util.Optional;

public interface CompteEpargnesDao extends GlobaleDao<CompteEpargne> {

    public Optional<CompteEpargne> changerStatus(CompteEpargne compteEpargne, Etat etat) throws Exception ;
    public Optional<List<Compte>> chercherParClient(Optional<Client> client) throws Exception;

    public   Optional<CompteEpargne> retrait(CompteEpargne compteEpargne, Employe employe, Double montant, TypeOperation typeOperation) throws Exception ;

    public  Optional<CompteEpargne> versement(CompteEpargne compteEpargne, Employe employe , Double montant, TypeOperation typeOperation) throws Exception ;
}
