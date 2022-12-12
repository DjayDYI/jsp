<%@ page import="java.util.*,java.text.*,AubergeInn.*" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
	<HEAD>
		<TITLE>IFT287 - Système de gestion de Auberge</TITLE>
		<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=ISO-8859-1">
		<META NAME="author" CONTENT="Jerome Boucher">
		<META NAME="description" CONTENT="page d'accueil système de gestion de bilbiothèque.">
		
		<LINK rel="stylesheet" type="text/css" href="stylesheet.css">
	</HEAD>
	<BODY>
		<!-- ======================================================
							MENU DE NAVIGATION
			 =================================================== -->
		<div id="header_content">
			<div id="logoutWrapper"><FORM ACTION="Logout" METHOD="GET"><input type="submit" name="btnLogout" value="Log Out" class="btnLogout"></FORM></div>
			<div id="menuWrapper">	
				<ul class="nav">
					<li><a href="Chambre" >Chambre</a></li>
					<li><a href="Client">Client</a></li>
					<li><a href="Service"> Service</a></li>
					<li><a href="Reservation"> Reservation</a></li>
				</ul>
			</div>
		</div>
		
		<!-- ======================================================
							CONTENU DE LA PAGE
			=================================================== -->
		<div class="contentUpCanvas">
			<center>
				<FORM ACTION="Reservation" METHOD="GET">
					<p>Sélectionnez une chambre :
						<select name="chambres" required>
						<% 
							GestionAuberge auberge = (GestionAuberge) request.getSession().getAttribute("aubergeInterrogation");
                
							for(int i=0; i< auberge.getGestionChambre().getListeChambre().size(); i++){
							    int idChambre = auberge.getGestionChambre().getListeChambre().get(i).getIdChambre();
							    String nomChambre = auberge.getGestionChambre().getListeChambre().get(i).getNomChambre();
						%>
    						<option name="idChambreAffiche" value="<%=idChambre%>"><%=idChambre + " ( " + nomChambre + " ) "%></option>
  						<%}%>
  						</select>
  					</p>

  					<p>Sélectionnez un client :
						<select name="clients" required>
						<% 
							auberge = (GestionAuberge) request.getSession().getAttribute("aubergeInterrogation");
                
							for(int i=0; i< auberge.getGestionClient().getListeClient().size(); i++){
							    int idClient = auberge.getGestionClient().getListeClient().get(i).getIdClient();
							    String prenom =  auberge.getGestionClient().getListeClient().get(i).getPrenom();
							    String nom =  auberge.getGestionClient().getListeClient().get(i).getNom();
						%>
    						<option name="idClientAffiche" value="<%=idClient%>"><%= idClient + " ( " + prenom + " " + nom + " ) "%></option>
  						<%}%>
  						</select>
  					</p>
  					
  					<br>
  					
  					<label for="dateDebut">Date Debut:</label>
  					<input type="date" id="dateDebut" name="dateDebut" required>
  					<label for="dateFin">Date Fin:</label>
  					<input type="date" id="dateFin" name="dateFin" required>
  					
  					<br><br><br>
  					
  					<input type="submit" name="btnReserver" value="Reservez" class="btnForm">	
				</FORM>
			</center>
		</div>
		
		<div class="afficherBottom">
			<jsp:include page="/WEB-INF/messageErreur.jsp" />
		</div>
	</BODY>
</HTML>
