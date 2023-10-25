package Dao;
import Entities.Affectation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AffectationDoa extends GlobaleDao<Affectation> {

    List<Affectation> getallAffectations();
    Optional<Boolean> deleteAffectationByCode(Integer affectationCodeToDelete  , String affectationMatriculeToDelete, LocalDate affectationStartDateToDelete, LocalDate affectationEndDateToDelete);
    Optional<Integer> getAccountManagersCount();
    Optional<Integer> getDirectorsCount();
}