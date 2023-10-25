package Entities;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
@Entity
@Table(name = "client")
public  class Client extends Personne {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code")
    private String code ;
    @Column(name = "address")
    private String adresse ;
  @Transient
   private Employe creator ;
  @Transient
   private List<Compte> comptes;
  @Transient
   private List<Demande> demande;


    public Client(String nom, String prenom, String telephone , LocalDate dateNaissance, String code, String adresse) {
        super(nom, prenom, dateNaissance, telephone);
        setCode(code);
        setAdresse(adresse);
    }
    public Client(String nom, String prenom, String telephone , LocalDate dateNaissance, String code, String adresse,Employe employe) {
        super(nom, prenom, dateNaissance, telephone);
        setCode(code);
        setAdresse(adresse);
        setCreator(employe);
    }


    public Client() {
        super();
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }


    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }

    public Employe getCreator() {
        return creator;
    }

    public void setCreator(Employe creator) {
       this.creator = creator;
    }


    public List<Demande> getDemande() {
        return demande;
    }

    public void setDemande(List<Demande> demande) {
        this.demande = demande;
    }
}
