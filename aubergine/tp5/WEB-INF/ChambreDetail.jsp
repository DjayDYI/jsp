<%@ page import="java.util.*,java.text.*,AubergeInn.*" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
	<HEAD>
		<TITLE>IFT287 - Syst?me de gestion de Auberge</TITLE>
		<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=ISO-8859-1">
		<META NAME="author" CONTENT="Jerome Boucher">
		<META NAME="description" CONTENT="page d'accueil syst?me de gestion de l'auberge.">
		
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
				<h1>Informations sur la chambre <%= request.getParameter("idChambreAffiche")%></h1>
				<br><br>
				<FORM ACTION="ChambreDetail" METHOD="POST">
					<input type="submit" name="btnPrecedent" value="Retour" class="btnForm">
				</FORM>
			</div>
		
			<div class="afficherRight">
				<table>
					<tr>
						<th>ID</th>
					    <th>Nom Service</th>
					    <th>Prix</th>
					</tr>
					<tr>	
					  	<%
					  		auberge = (GestionAuberge) request.getSession().getAttribute("aubergeInterrogation");
							
					  		int idChambre = Integer.parseInt(String.valueOf(request.getParameter("idChambreAffiche")));
					  	
					  		for(int i=0; i < auberge.getGestionChambre().getListeService(idChambre).size();i++)
					  		{
								int idService = auberge.getGestionChambre().getListeService(idChambre).get(i).getIdService();
								String nomService = auberge.getGestionChambre().getListeService(idChambre).get(i).getNom();
								int prix = auberge.getGestionChambre().getListeService(idChambre).get(i).getPrix();
					  		
						%>
						
					    	<td><% out.println(idService);%></td>
					    	<td><% out.println(nomService);%></td>
					    	<td><% out.println(prix);%></td>
					    	
					    	<td>
					    		<FORM ACTION="ChambreDetail" METHOD="POST">
					    			<input type="hidden" name="idServiceAffiche" value="<%=idService%>">
					    			<input type="hidden" name="idChambreAffiche" value="<%=idChambre%>">
					    			<input type="submit" name="btnEnleverService" value="Supprimer" class="btnForm">
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
