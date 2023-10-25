package Entities;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "agency")
public class Agence {
    @Id
    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String nom;
    @Column(name = "address")
    private String adresse;
    @Column(name = "tele")
    private String tele;
    @Transient
    private List<Compte> compteList;

    @Transient
    private List<Employe> employeList;

    @OneToMany(mappedBy = "agence")
    private List<Demande> demandes;

    public Agence(String code, String nom, String address, String tele) {
        setCode(code);
        setNom(nom);
        setAdresse(address);
        setTele(tele);
    }
}
