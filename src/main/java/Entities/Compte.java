package Entities;
import java.time.LocalDate;
import java.util.List;
import Enum.*;

public abstract class  Compte {
    protected Double solde;
    protected String numero;
    protected LocalDate dateCreation;
    protected Etat etat;

    protected Employe employe;
    protected Client client;
    protected Agence agence;
    private List<Operation> operations;





    public Compte(Double solde, String numero, LocalDate dateCreation, Etat etat, Employe employe, Client client,Agence agence) {
        setSolde(solde);
        setNumero(numero);
        setDateCreation(dateCreation);
        setEtat(etat);
        setEmploye(employe);
        setClient(client);
        setAgence(agence);
    }
    public Compte() {

    }

    public Compte( String number, Double balance) {
        setSolde(balance);
        setNumero(number);
    }

    public Double getSolde() {
        return solde;
    }
    public void setSolde(Double solde) {
        this.solde = solde;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public LocalDate getDateCreation() {
        return dateCreation;
    }
    public void  setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }
    public List<Operation> getOperations() {
        return operations;
    }
    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}
