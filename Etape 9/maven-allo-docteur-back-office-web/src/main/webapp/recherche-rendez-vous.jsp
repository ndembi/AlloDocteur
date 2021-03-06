<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!doctype html>
<html class="no-js" lang="">
  <head>
    <meta charset="utf-8">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Allo Docteur</title>

    <link rel="apple-touch-icon" href="apple-touch-icon.png">
    <!-- Place favicon.ico in the root directory -->

    <!-- build:css styles/vendor.css -->
    <!-- bower:css -->
    <!-- endbower -->
    <!-- endbuild -->

    <!-- build:css styles/main.css -->
    <link rel="stylesheet" href="./assets/styles/bootstrap.css"> 
    <link rel="stylesheet" href="./assets/styles/main.css">
    <link rel="stylesheet" href="./assets/styles/rwd.css">

    <!-- endbuild -->
    
    <!-- build:js scripts/vendor/modernizr.js -->
    <!-- endbuild -->
  </head>
  <body class="clearfix">
    <!--[if IE]>
      <p class="browserupgrade">Vous utilisez un <strong>obsolète</strong> navigateur. Merci de se <a href="http://browsehappy.com/">mettre à jour</a> pour améliorer votre experience.</p>
    <![endif]-->
    
    <div class="container-fluid  no-padding ">
      <div class="container-fluid clearfix no-padding header-container ">
        <div class="container header-content">
          <a href="./home" class="logo">
            <img src="./assets/images/logo.png" alt="">
          </a>
         <ul class="loginMenu">
            <li><a href="./login">Connexion</a></li>
          </ul>
          <ul class="nav">
            <li><a href="./home">Médecin</a></li>
            <li><a href="./account">Informations médecins</a></li>
            <li><a href="./searchpat">Recherche patient</a></li>
            <li><a href="./appointmentpresence">Présence rendez-vous</a></li>
            <li><a href="./createdoctor">Parrainer un médecin</a></li>
            <li><a href="./apptday">Rendez-vous journée</a></li>
          </ul>
        </div>
      </div>

      <div class="container-fluid clearfix">
        <div class="container content-outer forgot-password">
          <div class="content-container clearfix">
            <div class="content">
              <h1 class="site-title">
                Rechercher un rendez-vous
              </h1>
              <div class="formulaire">
                <form action="./searchappt" method="get">   
                
                  <p>
                    <input data-toggle="datepicker" type="text" name="rdvDate" placeholder="sélectionner une date">
                  </p>
                  <p>
                  <p>
                  <button type="submit">Valider</button>
                  </p>
                  <p>
                </form>
              </div>

              <table class="presence">
                <thead>
                  <tr>
                    <th>Prénom et Nom</th>
                    <th>Identifiant</th>
                    <th>Numéro de sécurité sociale</th>
                    <th>Numéro de téléphone</th>
                    <th>Date</th>
                    <th>Adresse</th>
                  </tr>
                </thead>
                  <tbody>
                    <c:forEach items="${rdvlist}" var="rdv">
                      <c:set var="patient" value="${rdv.patientRdv}"/>
                      <c:set var="user" value="${patient.userPatient}"/>
                      <c:set var="creneau" value="${rdv.creneau}"/>
                        <tr>
                          <td title="Prénom et Nom"><c:out value="${user.civilite} ${user.prenom} ${user.nom}" /></td>
                          <td title="Identifiant"><c:out value="${user.identifiant}" /></td>
                          <td title="Numéro de sécurité sociale"><c:out value="${patient.numeroSecuriteSociale}"/></td>
                          <td title="Numéro de téléphone"><c:out value="${patient.numeroTelephone}"/></td>
                          <td title="Date"><fmt:formatDate value="${rdv.jour}" pattern="yyyy-MM-dd" /><br /><c:out value="${creneau.heureDebut}" />h
                            <c:out value="${creneau.minuteDebut}" />m - <c:out value="${creneau.heureFin}" />h <c:out value="${creneau.minuteFin}" />m</td>
                          <td title="Adresse"> 2 rue du Paradis, 75000 Paris, France</td>
                        </tr>
                      </c:forEach>
                    </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
      
    </div>
    
    

    <!-- build:js scripts/vendor.js -->
    <!-- bower:js -->
    <script src="./assets/scripts/jquery.js"></script>
    
    <!-- endbower -->
    <!-- endbuild -->
    
    <script src="./assets/scripts/jquery.slicknav.js"></script> 
    <!-- build:js scripts/main.js -->
    <script src="./assets/scripts/main.js"></script>
    <!-- endbuild -->
  </body>
</html>
