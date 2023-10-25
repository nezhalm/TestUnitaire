
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Easy Bank</title>
  <link rel="stylesheet"  href="../../Css/ClientAdministration/DashboardPage.css" />
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@10.16.6/dist/sweetalert2.min.css">
  <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10.16.6/dist/sweetalert2.min.js"></script>

  <!-- Font Awesome Cdn Link -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"/>
</head>
<body>
<div class="container">
  <nav>
    <ul>
      <li><a href="/" class="logo">
        <img src="../../Images/admin.png">
        <span class="nav-item">EasyBank</span>
      </a></li>
      <li><a href="/credit-request">
        <i class="fas fa-database"></i>
        <span class="nav-item">Credit Request</span>
      </a></li>
      <li><a href="/displayAllDemandes">
        <i class="fas fa-comment"></i>
        <span class="nav-item">Request List</span>
      </a></li>
      <li><a href="#">
        <i class="fas fa-database"></i>
        <span class="nav-item">Report</span>
      </a></li>
      <li><a href="#">
        <i class="fas fa-chart-bar"></i>
        <span class="nav-item">Attendance</span>
      </a></li>
      <li><a href="#">
        <i class="fas fa-cog"></i>
        <span class="nav-item">Setting</span>
      </a></li>

      <li><a href="#" class="logout">
        <i class="fas fa-sign-out-alt"></i>
        <span class="nav-item">Log out</span>
      </a></li>
    </ul>
  </nav>

  <section class="main">
    <div class="main-top">
      <h1>Dashboard</h1>
      <%-- Affichez le message de succès --%>
      <c:if test="${not empty param.successMessage}">

        <div class="alert alert-success">
          <p>${param.successMessage}</p>
          <button type="button" class="close-alert"><i class="fas fa-times"></i></button>
        </div>
      </c:if>

      <c:if test="${not empty param.errorMessage}">
        <div class="alert alert-danger">
          <p>${param.errorMessage}</p>
          <button type="button" class="close-alert"><i class="fas fa-times"></i></button>
        </div>
      </c:if>

    </div>
    <div class="users">
      <div class="card">
        <img src="../../Images/client.png">
        <h4>Clients</h4>
        <p>Progammer</p>

         <a href="/addClient">Ajouter Client</a>
      </div>
      <div class="card">
        <img src="../../Images/equipe.png">
        <h4>Employes</h4>
        <p>Ui designer</p>
        <button>Ajouter Employe</button>
      </div>


    </div>

    <section class="attendance">
      <div class="attendance-list">
        <div style="display: flex;  justify-content: space-between;">
          <div> <h1>List des clients</h1></div>
        <div style="    margin-top: 1%;
    margin-left: 67%;" class="search">
          <form action="/searchClient" method="get">
            <input type="text"
                   placeholder=" entrer le code du client"
                   name="code">
            <button>
              <i class="fa fa-search"
                 style="font-size: 18px;">
              </i>
            </button>
          </form>
        </div>

        </div>
        <table class="table">
          <thead>
          <tr>
            <th>Code</th>
            <th>Nom</th>
            <th>Prenom</th>
            <th>Date de naissance</th>
            <th>Telephone</th>
            <th>Adresse</th>
            <th>delete</th>
            <th>update</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach items="${clients}" var="client">
            <tr>
              <td>${client.code}</td>
              <td>${client.nom}</td>
              <td>${client.prenom}</td>
              <td>${client.dateNaissance}</td>
              <td>${client.telephone}</td> <!-- Utilisez "telephone" au lieu de "heureOuverture" -->
              <td>${client.adresse}</td> <!-- Utilisez "adresse" au lieu de "heureFermeture" -->
              <td>
                <form method="POST" action="deleteClient">
                  <input type="hidden" name="clientCode" value="${client.code}">
                  <button type="submit">delete</button>
                </form>

              </td>
              <td>
                <form method="GET" action="updateClient">
                  <input type="hidden" name="action" value="updateClient">
                  <input type="hidden" name="clientCode" value="${client.code}">
                  <button type="submit">update</button>
                </form>
              </td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </section>
  </section>
</div>

</body>
<script>
  const closeAlertBtn = document.querySelector(".close-alert");
  const alert = document.querySelector(".alert");

  closeAlertBtn.addEventListener("click", () => {
    alert.style.display = "none";
  });
</script>

</html>