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
			<!-- **************************************************
							Section Ajouter Chambre
																 -->
			<div class="afficherLeft">
				<FORM ACTION="Chambre" METHOD="GET">
					
	  				<p class="leftAlign">ID : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="idChambre" type="text" required class="rightAlign"></p>		  	
	  				<p class="leftAlign">Nom : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="nomChambre" type="text" required></p>
	  				<p class="leftAlign">Type Lit: &nbsp;<input name="typeLit" type="text" required></p>
	  				<p class="leftAlign">Prix	:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input name="prix" type="text" required></p>
	  				
	  				<input type="submit" name="btnAjouterChambre" value="Ajouter Chambre" class="btnForm">
				</FORM>
			</div>
			<!-- *********************************************** -->
			
			<!-- ***************************************************
							Section Afficher Chambre
																 -->
			<div class="afficherRight">
				<br>
				<table>
					<tr>
						<th>Id</th>
						<th>Nom</th>
						<th>Type de Lit</th>
						<th>Prix</th>
						<th>Libre</th>
					</tr>
				
					<tbody>
						<% 
						GestionAuberge auberge = (GestionAuberge) request.getSession().getAttribute("aubergeInterrogation");
	                	
						boolean estLibre = false;
						
						for(int i=0; i< auberge.getGestionChambre().getListeChambre().size(); i++){
							String idChambre =String.valueOf(auberge.getGestionChambre().getListeChambre().get(i).getIdChambre());
						 	String nomChambre = (String) auberge.getGestionChambre().getListeChambre().get(i).getNomChambre();
						 	String prix = String.valueOf(auberge.getGestionChambre().getListeChambre().get(i).getPrix());
						 	String typeLit = (String) auberge.getGestionChambre().getListeChambre().get(i).getTypeLit();
						 	
						 	for(int j=0; j < auberge.getGestionChambre().afficherChambresLibres().size(); j++)
						 	{
						 	    if(auberge.getGestionChambre().getListeChambre().get(i).getIdChambre() == auberge.getGestionChambre().afficherChambresLibres().get(j).getIdChambre())
						 	    {
						 	        estLibre = true;
						 	    }
						 	}
						%>
						<tr>
						 	<td><% out.println(idChambre);%></td>
							<td><% out.println(nomChambre);%></td>
							<td><% out.println(typeLit);%></td>
							<td><% out.println(prix);%></td>
							<td><% if(estLibre){ %>
							    		<input type="checkbox" checked disabled />
									   <%
									} else {
								       %>
								      <input type="checkbox" disabled />
								<%}%>
							</td>
							<td>
								<FORM ACTION="Chambre" METHOD="POST">
					    			<input type="hidden" name="idChambreAffiche" value="<%=idChambre%>">
					    			<input type="submit" name="btnDetails" value="Voir Plus" class="btnForm">
	  								<input type="submit" name="btnSupprimer" value="supprimer" class="btnForm">
									<input type="submit" name="btnAjouterCommodite" value="ajouterCommodite" class="btnForm">
					    		</FORM>
							</td>
						</tr>
	  					<%}%>
	  				</tbody>
	  			</table>
	  		</div>
	  		<!-- *********************************************** -->	
		</div>
		
		<div class="afficherBottom">
			<jsp:include page="/WEB-INF/messageErreur.jsp" />
		</div>
		
	</BODY>
</HTML>
