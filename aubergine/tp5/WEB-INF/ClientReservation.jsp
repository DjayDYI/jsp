<%@ page import="java.util.*,java.text.*,AubergeInn.*" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
	<HEAD>
		<TITLE>IFT287 - Système de gestion de Auberge</TITLE>
		<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=ISO-8859-1">
		<META NAME="author" CONTENT="Jerome Boucher">
		<META NAME="description" CONTENT="page d'accueil système de gestion de l'auberge.">
		
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
			<% GestionAuberge auberge = (GestionAuberge) request.getSession().getAttribute("aubergeInterrogation"); %>
			<h1>Informations sur la client <%= request.getParameter("idClientAffiche")%></h1>
			<br><br>
			<FORM ACTION="ClientReservation" METHOD="POST">
				<input type="submit" name="btnPrecedent" value="Retour" class="btnForm">
			</FORM>
		</div>
		
		<div class="afficherRight">
			<table>
				<tr>
					<th>ID</th>
				    <th>Date Debut</th>
				    <th>Date Fin</th>
				    <th>Prix</th>
				</tr>
				<tr>	
				  	<%
				  		auberge = (GestionAuberge) request.getSession().getAttribute("aubergeInterrogation");
						
				  		int idClient = Integer.parseInt(String.valueOf(request.getParameter("idClientAffiche")));
				  	
				  		ArrayList<TupleReserver> tr = auberge.getGestionReservation().getReservationClient(idClient);
				  		
				  		for(int i=0; i < tr.size();i++)
				  		{
				  		    int idChambre = tr.get(i).getIdChambre();
							Date dateDebut = tr.get(i).getDateDebut();
							Date dateFin = tr.get(i).getDateFin();
							int prix = auberge.getGestionChambre().afficherChambre(idChambre).getPrix();
				  		
					%>
					
				    	<td><%= idChambre%></td>
						<td><%= dateDebut%></td>
						<td><%= dateFin%></td>
						<td><%= prix%></td>
				    	
				    	<td>
				    		<FORM ACTION="ClientReservation" METHOD="POST">
				    			<input type="hidden" name="idClientAffiche" value="<%=idClient%>">
				    			<input type="hidden" name="idChambreAffiche" value="<%=idChambre%>">
				    			<input type="hidden" name="dateDebutAffiche" value="<%=dateDebut%>">
				    			<input type="submit" name="btnAnnulezReservations" value="Annuler" class="btnForm">
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
