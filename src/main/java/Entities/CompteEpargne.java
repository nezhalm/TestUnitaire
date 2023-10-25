package Entities;
import java.time.LocalDate;
import Enum.*;
public class CompteEpargne extends Compte{
    private Double tauxInterer;
    private String accountType1;
    public CompteEpargne(Double solde, String numero, LocalDate dateCreation, Etat etat, Employe employe, Client client, Double tauxInterer,Agence agence) {
        super(solde, numero, dateCreation, etat, employe, client,agence);
        setTauxInterer(tauxInterer);
    }

    public CompteEpargne(String number, Double balance, String accountType) {
        setSolde(balance);
        setNumero(number);
        setAccountType1(accountType);
    }

    public Double getTauxInterer() {
        return tauxInterer;
    }
    public void setTauxInterer(Double tauxInterer) {
        this.tauxInterer = tauxInterer;
    }

    public String getAccountType1() {
        return accountType1;
    }

    public void setAccountType1(String accountType1) {
        this.accountType1 = accountType1;
    }


}
