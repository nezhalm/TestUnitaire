<%@ page import="ConnexionBaseDonnes.Connexion" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<p><% Connexion.getInstance();%></p>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Attendance Dashboard | By Code Info</title>
    <link rel="stylesheet" href="../../../Css/ClientAdministration/DashboardPage.css"/>
    <link rel="stylesheet" href="../../../Css/ClientAdministration/home.css"/>
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

    <section class="main2">
        <div class="main-top">
            <i class="fas fa-user-cog"></i>
        </div>

        <div class="row">
            <div class="column1">
                <h1>Easybank Plateforme</h1>
                <p>Simplifier les procedures de votre compte </p>
                <button>voir</button>
            </div>
            <div class="column2">
                <img src="./Images/undraw_working_re_ddwy.svg" alt="banner" width="500px">
            </div>
        </div>


    </section>
</div>
<script>
    document.getElementById('ajouterClientButton').addEventListener('click', function () {
        window.location.href = 'new-client'; // Remplacez 'AddClient.html' par le chemin de votre page AddClient
    });
</script>


</body>
</html>