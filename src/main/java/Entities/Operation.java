package Entities;
import java.time.LocalDate;
import Enum.*;
public class Operation {

    private Employe employes;
    private Compte comptes;
    private String numero;
    private LocalDate dateCreation;
    private Double montant;
    private TypeOperation type;

    public Operation(Employe employes, Compte comptes, String numero, LocalDate dateCreation, Double montant) {
        setEmployes(employes);
        setComptes(comptes);
        setNumero(numero);
        setDateCreation(dateCreation);
        setMontant(montant);
    }

    public Operation(Employe employes, Compte comptes, String numero, LocalDate dateCreation, Double montant, TypeOperation type) {
        setEmployes(employes);
        setComptes(comptes);
        setNumero(numero);
        setDateCreation(dateCreation);
        setMontant(montant);
        setType(type);
    }

    public Operation() {}



    public Employe getEmployes() {
        return employes;
    }

    public void setEmployes(Employe employes) {
        this.employes = employes;
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

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public CompteCourant getComptes() {
        return (CompteCourant) comptes;
    }

    public void setComptes(Compte comptes) {
        this.comptes = comptes;
    }

    public TypeOperation getType() {
        return type;
    }

    public void setType(TypeOperation type) {
        this.type = type;
    }
}
