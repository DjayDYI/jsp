package Servlet;

import java.io.*;
import java.sql.Date;
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
 * Cette classe permet d'ajouter des reservations
 *
 * </pre>
 */

public class Reservation extends HttpServlet
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
        // Si le bouton reserver a été cliqué
        else if(request.getParameter("btnReserver") != null)
        {
            reserver(request, response);
        }
        // Sinon
        else
        {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/reservation.jsp");
            dispatcher.forward(request, response);
        }   
    }

    /**
     * Cette méthode sert a ajouter une reservation à la base de données
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void reserver(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            // Récupère le idChambre
            String idChambreParam = request.getParameter("chambres");
            request.setAttribute("chambre", idChambreParam);
            
            // Récupère le idClient
            String idClientParam = request.getParameter("clients");
            request.setAttribute("clients", idClientParam);

            // Récupère la date de début
            Date dateDebut = Date.valueOf(request.getParameter("dateDebut"));
            request.setAttribute("dateDebut", dateDebut);
            
            // Récupère la date de fin
            Date dateFin = Date.valueOf(request.getParameter("dateFin"));
            request.setAttribute("dateFin", dateFin);

            GestionAuberge aubergeUpdate = (GestionAuberge) request.getSession().getAttribute("aubergeUpdate");
            
            // Modification de la base données
            synchronized (aubergeUpdate)
            {
                 aubergeUpdate.getGestionReservation().Reserver(Integer.parseInt(idChambreParam), Integer.parseInt(idClientParam), dateDebut, dateFin);
            }
            
            // Rafraichir la page 
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/reservation.jsp");
            dispatcher.forward(request, response);
        }
        catch (IFT287Exception e)
        {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add(e.toString());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/reservation.jsp");
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
