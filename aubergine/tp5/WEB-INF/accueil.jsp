<%@ page import="java.util.*,java.text.*" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
		<center>
			<h1>Bienvenue sur le gestionnaire d'aubergeInn</h1>
		</center>
		
	</BODY>
</HTML>
