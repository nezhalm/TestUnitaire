package Entities;
import Enum.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "demande")
public class Demande {
    @Id
    private String number;

    @Column(name = "mensualite")
    private Double monsualite;

    @Column(name = "price")
    private Integer price;

    private Integer duration;

    private String remarque;

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private StatusDemande status;

    @ManyToOne
    @JoinColumn(name = "codeemploye")
    private Employe employe;

    @ManyToOne
    @JoinColumn(name = "codeagence")
    private Agence agence;

    @ManyToOne
    @JoinColumn(name = "codeclient")
    private Client client;

    @OneToMany(mappedBy = "demande", cascade = CascadeType.ALL)
    private List<UpdateDemandeHistory> updateHistory;

    public Demande(Employe employe, Agence agence, Client client, Integer credit, Integer duree, String remarque, LocalDateTime dateEtHeureCreation, String s, StatusDemande statusDemande, Double monsualite) {
         setEmploye(employe);
         setAgence(agence);
         setClient(client);
         setPrice(credit);
         setDuration(duree);
         setRemarque(remarque);
         setDate(dateEtHeureCreation);
         setNumber(s);
         setStatus(statusDemande);
         setMonsualite(monsualite);
    }
    public Demande(Employe employe, Agence agence, Client client, Integer credit, Integer duree, String remarque,  String s, StatusDemande statusDemande, Double monsualite) {
        setEmploye(employe);
        setAgence(agence);
        setClient(client);
        setPrice(credit);
        setDuration(duree);
        setRemarque(remarque);
        setNumber(s);
        setStatus(statusDemande);
        setMonsualite(monsualite);
    }

}
