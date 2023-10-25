package Entities;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import Enum.*;
public class Virement extends Operation {
    @Getter
    @Setter
    private Compte destinataire;
    public Virement(Employe employe, Compte compte, String numero, LocalDateTime dateCreation, Double montant, TypeOperation type, CompteCourant destinataire) {
        super(employe, compte, numero, LocalDate.from(dateCreation), montant, type);
        this.destinataire = destinataire;
    }
    public Virement() {

    }

}
