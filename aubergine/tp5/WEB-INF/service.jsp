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
				<FORM ACTION="Service" METHOD="GET">
					
	  				<p class="leftAlign">ID : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="idService" type="text" required class="rightAlign"></p>		  	
	  				<p class="leftAlign">Nom : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="nomService" type="text" required></p>
	  				<p class="leftAlign">Prix	:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input name="prix" type="text" required></p>
	  				
	  				<input type="submit" name="btnAjouter" value="Ajouter Service" class="btnForm">
				</FORM>
			</div>
		
			<div class="afficherRight">
				<br>
				<table>
					  <tr>
					    <th>ID</th>
					    <th>Nom Service</th>
					    <th>Prix</th>
					  </tr>
					  <tr>
					  	
					  	<%
					  		GestionAuberge auberge = (GestionAuberge) request.getSession().getAttribute("aubergeInterrogation");
							
					  		for(int i=0; i < auberge.getGestionService().getListeService().size();i++)
					  		{
								 int idService = auberge.getGestionService().getListeService().get(i).getIdService();
								 String nomService = (String)auberge.getGestionService().getListeService().get(i).getNom();
								 int prix = auberge.getGestionService().getListeService().get(i).getPrix();
					  		
						%>
						
					    	<td><% out.println(idService);%></td>
					    	<td><% out.println(nomService);%></td>
					    	<td><% out.println(prix);%></td>
					    	<td><FORM ACTION="Service" METHOD="POST">
					    		<input type="hidden" name="idServiceAffiche" value="<%=idService%>">
					    		<input type="submit" name="btnSupprimer" value="Supprimer" class="btnForm">
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
