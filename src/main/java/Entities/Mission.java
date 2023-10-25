package Entities;
import java.util.List;
public class Mission {
    private List<Affectation> affectations;
    private String description;
    private String nom;
    private String code;

    public Mission(String code, String nom, String description) {
        setCode(code);
        setNom(nom);
        setDescription(description);
    }

    public Mission() {

    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Affectation> getAffectations() {
        return affectations;
    }

    public void setAffectations(List<Affectation> affectations) {
        this.affectations = affectations;
    }
}
