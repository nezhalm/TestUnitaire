package Dao;

import Entities.*;
import Enum.*;
import java.util.List;
import java.util.Optional;

public interface CompteCourantDao extends GlobaleDao<CompteCourant> {
    public Optional<CompteCourant> changerStatus(CompteCourant compteCourant, Etat etat) throws Exception ;
    public Optional<List<Compte>> chercherParClient(Optional<Client> client) throws Exception;

    public  Optional<CompteCourant> retrait(CompteCourant compteCourant, Employe employe, Double montant,TypeOperation typeOperation) throws Exception ;

    public  Optional<CompteCourant> versement(CompteCourant compteCourant, Employe employe , Double montant, TypeOperation typeOperation) throws Exception ;
}