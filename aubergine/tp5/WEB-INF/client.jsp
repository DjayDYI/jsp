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
			<div class="afficherLeft">
				<FORM ACTION="Client" METHOD="GET">
  					<p class="leftAlign">ID : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="idClient" type="text" required class="rightAlign"></p>		  	
  					<p class="leftAlign">Prenom : &nbsp;&nbsp;<input name="nomClient" type="text" required></p>
  					<p class="leftAlign">Nom : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="prenomClient" type="text" required></p>
  					<p class="leftAlign">Age :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input name="age" type="text" required></p>
  					<input type="submit" name="btnAjouterClient" value="Ajouter Client" class="btnForm">
				</FORM>
			</div>
			<div class="afficherRight">
				<br>
				<table>
				  <tr>
				    <th>ID</th>
				    <th>Prenom</th>
				    <th>Nom</th>
				    <th>Age</th>
				  </tr>
				  <tr>
				  	
				  	<%
				  		GestionAuberge auberge = (GestionAuberge) request.getSession().getAttribute("aubergeInterrogation");
						
				  		for(int i=0; i < auberge.getGestionClient().getListeClient().size();i++)
				  		{
							 int idClient = auberge.getGestionClient().getListeClient().get(i).getIdClient();
							 String nomClient = (String)auberge.getGestionClient().getListeClient().get(i).getNom();
							 String prenomClient = (String)auberge.getGestionClient().getListeClient().get(i).getPrenom();
							 String age = String.valueOf(auberge.getGestionClient().getListeClient().get(i).getAge());
				  		
					%>
					
				    	<td><% out.println(idClient);%></td>
				    	<td><% out.println(nomClient);%></td>
				    	<td><% out.println(prenomClient);%></td>
				    	<td><% out.println(age);%></td>
				    	<td><FORM ACTION="Client" METHOD="POST">
				    		<input type="hidden" name="idClientAffiche" value="<%=idClient%>">
				    		<input type="submit" name="btnDetailClient" value="Detail" class="btnForm">
				    		<input type="submit" name="btnSupprimerClient" value="Supprimer" class="btnForm">
				    		</FORM>
				    	</td>
				    
				   </tr>
				    <%
				  		}	
					%>
					
				</table>
				
			</div>
			
		</div>

		<div class="afficherBottom">
			<jsp:include page="/WEB-INF/messageErreur.jsp" />
		</div>
	</BODY>
</HTML>
