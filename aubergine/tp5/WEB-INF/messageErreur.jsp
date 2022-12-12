<%@ page import="java.util.*,java.text.*" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%
  // affichage de la liste des messages d'erreur
  if (request.getAttribute("listeMessageErreur") != null)
    {
%>
    <span style="color: red; font-weight: bold;">
<%
    ListIterator it = ((List) request.
      getAttribute("listeMessageErreur")).listIterator();
    while (it.hasNext())
      {
%>
      <BR>
      <%= it.next() %>
<%
      }
%>
    </span>
<%
    }
%>
