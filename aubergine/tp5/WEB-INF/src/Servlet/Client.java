package Servlet;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import AubergeInn.GestionAuberge;
import AubergeInn.IFT287Exception;

/**
 * Classe TupleService
 *
 * <pre>
 * 
 * Jérôme Boucher-Veilleux
 * Patrick-Olivier Tété
 * Universite de Sherbrooke
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Cette classe sert principalement a gérer les clients
 *
 * </pre>
 */
public class Client extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Si l'état est null on renvoit au login
        Integer etat = (Integer) request.getSession().getAttribute("etat");
        if (etat == null)
        {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        }
        // Si le bouton ajouter client a été cliqué
        else if(request.getParameter("btnAjouterClient") != null)
        {
            ajouterClient(request,response);
        }
        // Si le bouton supprimer client a été cliqué
        else if(request.getParameter("btnSupprimerClient") != null)
        {
            supprimerClient(request,response);
        }
        // Si le bouton detai a été cliqué
        else if(request.getParameter("btnDetailClient") != null)
        {
            // On redirge l'utilisateur vers la page ClientReservation.jsp
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ClientReservation.jsp");
            request.setAttribute("idClientAffiche", request.getParameter("idClientAffiche"));
            dispatcher.forward(request, response);
        }
        // Sinon
        else
        {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/client.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Cette méthode supprime un client
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void supprimerClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            // Récupère le idClient
            String idClientParam= (String) request.getParameter("idClientAffiche");
            int idClient  = Integer.valueOf(idClientParam);
            GestionAuberge aubergeUpdate = (GestionAuberge) request.getSession().getAttribute("aubergeUpdate");
            
            // Modification de la base de donnée
            synchronized (aubergeUpdate)
            {
                aubergeUpdate.getGestionClient().supprimerClient(idClient);
            }
            
            // Rafraichir la page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/client.jsp");
            dispatcher.forward(request, response);
                
        }
        catch (IFT287Exception e)
        {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add(e.toString());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/client.jsp");
            dispatcher.forward(request, response);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
        } 
    }

    /**
     * Cette méthode ajoute un nouveau client
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void ajouterClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            // Récupère le id client
            String idClientParam = request.getParameter("idClient");
            request.setAttribute("idClient", idClientParam);

            // Validation du format de idClient
            int idClient = -1;
            try
            {
                idClient = Integer.parseInt(idClientParam);
            }
            catch (NumberFormatException e)
            {
                throw new IFT287Exception("Format de idClient " + idClientParam + " incorrect.");
            }

            // Récupère le nom client 
            String nomParam = request.getParameter("nomClient");
            request.setAttribute("nomClient", nomParam);

            // Récupère le prenom client
            String prenomParam = request.getParameter("prenomClient");
            request.setAttribute("prenomClient", prenomParam);

            // Récupère l'age du client
            String ageParam = request.getParameter("age");
            request.setAttribute("age", ageParam);

            // Validation du format de l'age
            int age = -1;
            try
            {
                age = Integer.parseInt(ageParam);
            }
            catch (NumberFormatException e)
            {
                throw new IFT287Exception("Format de prix " + ageParam + " incorrect.");
            }

            GestionAuberge aubergeUpdate = (GestionAuberge) request.getSession().getAttribute("aubergeUpdate");
            
            // Modification de la base de données
            synchronized (aubergeUpdate)
            {
                 aubergeUpdate.getGestionClient().ajouterClient(idClient, nomParam, prenomParam, age);
            }
            
            // Rafraichir la page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/client.jsp");
            dispatcher.forward(request, response);
        }
        catch (IFT287Exception e)
        {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add(e.toString());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/client.jsp");
            dispatcher.forward(request, response);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        doGet(request, response);
    }

}
