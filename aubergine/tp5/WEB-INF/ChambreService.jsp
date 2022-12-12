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
			<center>
				<FORM ACTION="ChambreService" METHOD="GET">
				<div>
					<p>Sélectionnez une chambre :
						<select name="chambres">
						<% 
							GestionAuberge auberge = (GestionAuberge) request.getSession().getAttribute("aubergeInterrogation");
		                
							for(int i=0; i< auberge.getGestionChambre().getListeChambre().size(); i++){
							    int idChambre = auberge.getGestionChambre().getListeChambre().get(i).getIdChambre();
							    String nomChambre = auberge.getGestionChambre().getListeChambre().get(i).getNomChambre();
								
							    if(idChambre == Integer.valueOf(String.valueOf(request.getAttribute("idChambreAffiche")))){
						%>
								<option name="idChambreAffiche"  value="<%=idChambre%>" selected>
		    						<%=idChambre + " ( " + nomChambre + " ) "%>
		    					</option>
		    			<%		    
							    } else {
		    			%>
		  						<option name="idChambreAffiche" value="<%=idChambre%>"  disabled>
		    						<%=idChambre + " ( " + nomChambre + " ) "%>
		    					</option>
		  				<%}}%>
		  				</select>
		  			</p>
		  			
		  			<p>Sélectionnez un service :
						<select name="services">
						<%                 
							for(int i=0; i< auberge.getGestionService().getListeService().size(); i++){
							    int idService = auberge.getGestionService().getListeService().get(i).getIdService();
							    String nomService = auberge.getGestionService().getListeService().get(i).getNom();
						%>
		    				<option name="idServiceAffiche" value="<%=idService%>">
		    				<%= idService + " ( " + nomService + " ) "%>
		    				</option>	    				
		  				<%}%>
		  				</select>
		  			</p>
				</div>
				<input type="submit" name="btnInclure" value="Inclure Service" class="btnForm">
				<input type="submit" name="btnPrecedent" value="Retour" class="btnForm">
				</FORM>
			</center>
		</div>
			
		<div class="afficherBottom">
			<jsp:include page="/WEB-INF/messageErreur.jsp" />
		</div>
	</BODY>
</HTML>
