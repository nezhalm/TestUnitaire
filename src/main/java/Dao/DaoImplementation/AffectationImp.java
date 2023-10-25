package Dao.DaoImplementation;

import Dao.AffectationDoa;
import Entities.Affectation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class AffectationImp implements AffectationDoa {

    @Override
    public List<Affectation> getallAffectations() {
        return null;
    }

    @Override
    public Optional<Boolean> deleteAffectationByCode(Integer affectationCodeToDelete, String affectationMatriculeToDelete, LocalDate affectationStartDateToDelete, LocalDate affectationEndDateToDelete) {
        return Optional.empty();
    }

    @Override
    public Optional<Integer> getAccountManagersCount() {
        return Optional.empty();
    }

    @Override
    public Optional<Integer> getDirectorsCount() {
        return Optional.empty();
    }

    @Override
    public Optional<Affectation> ajouter(Affectation affectation){
        return Optional.empty();
    }

    @Override
    public Optional<Integer> supprimer(Affectation affectation)  {
        return Optional.empty();
    }

    @Override
    public Optional<Affectation> chercher(String var) {
        return Optional.empty();
    }

    @Override
    public List<Affectation> afficheList()  {
        return null;
    }
}
