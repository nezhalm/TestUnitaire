package Entities;

import java.time.LocalDate;

import Enum.*;

public class CompteCourant extends Compte{
    private Double decouvert;
    private  String accountType1;
    public CompteCourant(Double solde, String numero, LocalDate dateCreation, Etat etat, Employe employe, Client client, Double decouvert,Agence agence) {
        super(solde, numero, dateCreation, etat, employe, client ,agence);
        setDecouvert(decouvert);
    }
    public CompteCourant() {
        super();
    }
    public CompteCourant(String number, Double balance,String accountType) {
            setSolde(balance);
            setNumero(number);
            setAccountType1(accountType);
    }

    public Double getDecouvert() {
        return decouvert;
    }
    public void setDecouvert(Double decouvert) {
        this.decouvert = decouvert;
    }
    public String getAccountType1() {
        return accountType1;
    }
    public void setAccountType1(String accountType1) {
        this.accountType1 = accountType1;
    }
}
