package Service;

import Dao.DaoImplementation.ClientImp;
import Dao.DaoImplementation.EmployeImp;
import Entities.Employe;

import java.time.LocalDate;
import java.util.*;

public class EmployeService {
    private final EmployeImp employeImp;

    public EmployeService(EmployeImp employeImp) {
        this.employeImp = employeImp;
    }

    public void ajouterEmploye() throws Exception {
        Scanner scanner2 = new Scanner(System.in);
        Entities.Employe employe = new Entities.Employe();

        System.out.println("Veuillez saisir les informations de l'employé :");
        System.out.print("Code : ");
        String code = scanner2.nextLine();
        System.out.print("Prénom : ");
        String prenom = scanner2.nextLine();
        System.out.print("Nom : ");
        String nom = scanner2.nextLine();
        System.out.print("Date de naissance (AAAA-MM-JJ) : ");
        String dateNaissanceStr = scanner2.nextLine();
        LocalDate dateNaissance = LocalDate.parse(dateNaissanceStr);
        System.out.print("Numéro de téléphone : ");
        String numeroTelephone = scanner2.nextLine();
        System.out.print("Email : ");
        String email = scanner2.nextLine();
        System.out.print("Date de recrutement (AAAA-MM-JJ) : ");
        String dateRecrutementStr = scanner2.nextLine();
        LocalDate dateRecrutement = LocalDate.parse(dateRecrutementStr);
        employe.setMatricule(code);
        employe.setPrenom(prenom);
        employe.setNom(nom);
        employe.setDateNaissance(dateNaissance);
        employe.setTelephone(numeroTelephone);
        employe.setDateRecrutement(dateRecrutement);
        employe.setEmail(email);
        Optional<Entities.Employe> employe1 = employeImp.ajouter(employe);
        employe1.ifPresent(e -> {
            System.out.println("L'employé a été ajouté avec succès !");
        });
        if (!employe1.isPresent()) {
            System.out.println("Échec de l'ajout de l'employé.");
        }

    }


    public List<Employe> AllEmployes() {
        List<Employe> clientListOptional = employeImp.afficheList();
        if (clientListOptional != null) {
            List<Entities.Employe> employeLists = clientListOptional;
            List<Entities.Employe> processedEmployes = new ArrayList<>();
            if (!employeLists.isEmpty()) {
                for (Entities.Employe employe : employeLists) {
                    processedEmployes.add(employe);
                }
                return processedEmployes;
            } else {
                return Collections.emptyList();
            }
        } else {
            return Collections.emptyList();
        }
    }

    public void chercherPourSupprimer() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter le matricule");
        String code = scanner.nextLine();
        Optional<Entities.Employe> employe = employeImp.chercher(code);
        if (employe.isPresent()) {
            Entities.Employe employe1 = employe.get();
            employeImp.supprimer(employe1);
            System.out.println("employe supprimer avec success");
        } else {
            System.out.println("employe non trouvé pour le le matricule : " + code);
        }
    }

    public Employe chercher(String code) {
        Optional<Employe> clientTrouve = employeImp.chercher(code);
        if (clientTrouve.isPresent()) {
            return clientTrouve.get();
        } else {
            return null;
        }
    }
}

