package Dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import Enum.*;
public interface GlobaleDao<T> {

        Optional<T> ajouter(T t) ;
        Optional<Integer>  supprimer(T t);
        Optional<T> chercher(String var) ;
        List<T> afficheList();
}

