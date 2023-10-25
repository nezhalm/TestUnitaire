<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Easy Bank</title>
    <link rel="stylesheet" href="../../Css/DemandeAdministration/FormeDemande.css"/>
    <link rel="stylesheet" href="../../Css/DemandeAdministration/PetiteCard.css"/>
    <link rel="stylesheet" href="../../Css/ClientAdministration/DashboardPage.css" />
    <link rel="stylesheet" href="../../Css/DemandeAdministration/style.css"/>
    <script src="../../Js/Script.js" defer></script>
    <script src="../../Js/FormeScript.js" defer></script>
    <script src="https://code.iconify.design/iconify-icon/1.0.7/iconify-icon.min.js"></script>
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

    <section class="main main-request">
        <div class="form-container">
            <form action="/add" method="post" class="form">
                <h1 class="text-center">Credit Request</h1>
                <!-- Progress bar -->
                <div class="progressbar">
                    <div class="progress" id="progress"></div>

                    <div
                            class="progress-step progress-step-active"
                            data-title="Credit simulation"
                    ></div>
                    <div class="progress-step" data-title="Client"></div>
                    <div class="progress-step" data-title="Employee"></div>
                </div>

                <!-- Step 1 -->
                <div class="form-step form-step-active">
                    <div class="input-group">
                        <div class="user-input-box">
                            <label for="agency-code">Agency</label>
                            <select id="agency-code" class="select-credit-request" name="agency-code">
                                <option disabled selected>Select</option>
                                <c:forEach items="${agencies}" var="agency">
                                    <option value="${agency.code}">${agency.nom}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="input-group">
                        <div class="user-input-box">
                            <label for="">To be used</label>
                            <select id="" class="select-credit-request" name="">
                                <option value="">proejet1</option>
                                <option value="">proejet2</option>
                                <option value="">proejet3</option>
                                <option value="">proejet4</option>
                                <option value="">proejet5</option>
                            </select>
                        </div>
                    </div>
                    <div class="range-&-label">
                        <h3>Choose the credit amount</h3>
                        <div class="range">
                            <div class="sliderValue">
                                <span>5000</span>
                            </div>
                            <div class="field">
                                <div class="value left">5000</div>
                                <input type="range" id="range1" min="5000" max="600000" value="5000" step="1000" name="price">
                                <div class="value right">600000</div>
                            </div>
                        </div>
                    </div>
                    <div class="range-&-label">
                        <h3>Choose the duration</h3>
                        <div class="range">
                            <div class="sliderValue2">
                                <span>12</span>
                            </div>
                            <div class="field">
                                <div class="value left">12</div>
                                <input type="range" id="range2" min="12" max="120" value="12" step="6" name="duration">
                                <div class="value right">120</div>
                            </div>
                        </div>
                    </div>
                    <div class="">
                        <button type="button" class="btn btn-next width-50 ml-auto">Next</button>
                    </div>
                </div>

                <!-- Step 2 -->
                <div class="form-step">
                    <div class="input-group">
                        <label for="client-code">Enter the client's code</label>
                        <input type="text" name="code" id="client-code"/>
                        <button type="button" id="search-btn">
                            <iconify-icon icon="svg-spinners:ring-resize" style="display: none;"></iconify-icon>
                            <i class="fas fa-search"></i>
                        </button>
                    </div>
                    <div id="client-info"></div>
                    <div class="btns-group">
                        <button type="button" class="btn btn-prev">Previous</button>
                        <button type="button" class="btn btn-next" disabled>Next</button>
                    </div>
                </div>

                <!-- Step 3 -->
                <div class="form-step">
                    <div class="input-group">
                        <div class="user-input-box">
                            <label for="employee-code">Employee</label>
                            <select id="employee-code" class="select-credit-request" name="employee-code">
                                <option disabled selected>Select</option>
                                <c:forEach items="${employees}" var="employee">
                                    <option value="${employee.matricule}">${employee.prenom} ${employee.nom}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="input-group">
                        <div class="user-input-box">
                            <label for="request-remark">Remarks</label>
                            <textarea name="remark" id="request-remark"></textarea>
                        </div>
                    </div>
                    <div class="btns-group">
                        <button type="button" class="btn btn-prev">Previous</button>
                        <button class="btn">Submit</button>
                    </div>
                </div>
            </form>
        </div>
        <div class="table basic">
            <div class="package-name"></div>
            <ul class="features">
                <li class="overview-section">
                    <p>Credit details</p>
                </li>
                <li>
                    <span class="list-name">Credit amount:</span>
                    <span class="list-price" id="mon"></span>
                </li>
                <li>
                    <span class="list-name">Duration:</span>
                    <span class="list-price" id="dureet"></span>
                </li>
                <li>
                    <span class="list-name">Monthly payment:</span>
                    <span class="list-price" id="output"></span>
                </li>
            </ul>
        </div>
    </section>
</div>
</body>
</html>
