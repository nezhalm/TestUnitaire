package com.example.demo;

import Dao.DaoImplementation.ClientImp;
import Dao.DaoImplementation.DemandeImp;
import Dao.DaoImplementation.EmployeImp;
import Entities.Client;
import Entities.Employe;
import Service.ClientService;
import Service.DemandeService;
import Service.EmployeService;
import Utils.LocalDateSerialization;
import Utils.LocalDateTimeSerialization;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static Utils.ExtraMethods.generateUniqueCode;

@WebServlet(urlPatterns = {"/saveClient", "/addClient", "/list", "/deleteClient", "/savechangesClient", "/updateClient", "/listClients", "/searchClient", "/get-client"})
public class AllClientsServlet extends HttpServlet {
    ClientService clientService;
    EmployeService employeService;
    DemandeService demandeService;

    @Override
    public void init() throws ServletException {
        clientService = new ClientService(new ClientImp());
        employeService = new EmployeService(new EmployeImp());
        demandeService = new DemandeService(new DemandeImp());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/saveClient":
                    addClient(request, response);
                    break;
                case "/addClient":
                    displayeForm(request, response);
                    break;
                case "/deleteClient":
                    doDelete(request, response);
                    break;
                case "/updateClient":
                    getClientTOUpdate(request, response);
                    break;
                case "/savechangesClient":
                    savechangesClient(request, response);
                    break;
                case "/list":
                    clientsDashboard(request, response);
                    break;
                case "/searchClient":
                    searchClient(request, response);
                    break;
                case "/get-client":
                    searchClientAPI(request, response);
                    break;
                default:
                    System.out.println("Error: This path does not exist.");
                    break;
            }
        } catch (ServletException ex) {
            throw new ServletException(ex);
        }
    }

    private void displayeForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("employes", employeService.AllEmployes());
        request.getRequestDispatcher("/JSPs/ClientAdministration/AddClient.jsp").forward(request, response);
    }

    private void getClientTOUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("employes", employeService.AllEmployes());

        request.setAttribute("clientTrouvee", clientService.chercher(request.getParameter("clientCode")).get());

        request.getRequestDispatcher("/JSPs/ClientAdministration/UpdateClient.jsp").forward(request, response);
    }

    private void clientsDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("clients", clientService.AllClients());
        request.getRequestDispatcher("/JSPs/ClientAdministration/Dashboard.jsp").forward(request, response);
    }

    private void searchClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        Optional<Client> client = clientService.chercher(code);
        if (client.isPresent()) {
            request.setAttribute("client", client.get());
            request.getRequestDispatcher("/JSPs/ClientAdministration/GetClient.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/list?errorMessage=Le+client+introuvable.");
        }
    }


    private void searchClientAPI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        Optional<Client> client = clientService.chercher(code);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (client.isPresent()) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateSerialization())
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerialization())
                    .create();
            String clientJSON = gson.toJson(client.get());


            response.getWriter().write(clientJSON);
        } else {
            String errorMessage = "{\"error\": \"Client not found.\"}";
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().print(errorMessage);
        }
        response.getWriter().flush();
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String clientCode = req.getParameter("clientCode");
        boolean deleted = clientService.chercherPourSupprimer(clientCode);
        if (deleted) {
            resp.sendRedirect(req.getContextPath() + "/list?successMessage=Le+client+a+ete+supprime+avec+succes.");
        } else {
            resp.sendRedirect(req.getContextPath() + "/list?errorMessage=La+suppression+du+client+a+echoue.");

        }
    }


    private void addClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupération des données du formulaire
        String fullName = request.getParameter("fullName");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String dateNaissanceStr = request.getParameter("dateNaissance");
        // Validation des données (vous devrez ajouter une validation appropriée)
        if (fullName == null || username == null || email == null || phoneNumber == null || dateNaissanceStr == null) {
            request.setAttribute("errorMessage", "Données du formulaire incorrectes.");
            request.getRequestDispatcher("/JSPs/erreur.jsp").forward(request, response);
        }

        LocalDate dateNaissance = LocalDate.parse(dateNaissanceStr);
        String matricule = request.getParameter("matricule");
        // Création d'un nouvel objet Client
        Client newClient = new Client();
        newClient.setCode(generateUniqueCode(3));
        newClient.setNom(fullName);
        newClient.setPrenom(username);
        newClient.setDateNaissance(dateNaissance);
        newClient.setAdresse(email);
        newClient.setTelephone(phoneNumber);
        // Recherche de l'employé associé par matricule
        Employe employe = employeService.chercher(matricule);

        if (employe != null) {
            //  newClient.setCreator(employe);

            Optional<Client> inserted = clientService.insertClient(newClient);

            if (inserted.isPresent()) {
                response.sendRedirect(request.getContextPath() + "/list?successMessage=Le+client+a+ete+ajoute+avec+succes.");
            } else {
                response.sendRedirect(request.getContextPath() + "/list?errorMessage=error+dans+l'ajout+du+client.");

            }
        } else {
            request.setAttribute("errorMessage", "Employé non trouvé.");
            //  request.getRequestDispatcher("/JSPs/erreur.jsp").forward(request, response);
        }
    }


    private void savechangesClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String fullName2 = request.getParameter("fullName");
        String code = request.getParameter("code");
        String username2 = request.getParameter("username");
        String adresse = request.getParameter("email");
        String phoneNumber2 = request.getParameter("phoneNumber");
        LocalDate dateNaissance = LocalDate.parse(request.getParameter("dateNaissance"));
        Client client = new Client(fullName2, username2, phoneNumber2, dateNaissance, code, adresse);
        boolean var = clientService.update(client);
        if (var) {
            response.sendRedirect(request.getContextPath() + "/list?successMessage=Le+client+a+ete+mise+a+jour+avec+succes.");
        } else {
            response.sendRedirect(request.getContextPath() + "/list?errorMessage=error+dans+la+modification+du+client.");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

