package Servlet;

import java.io.IOException;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * Cette classe sert principalement a gérer les reservations des clients
 *
 * </pre>
 */
public class ClientReservation extends HttpServlet
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
        // Si le bouton annuler reservations a été cliqué
        else if(request.getParameter("btnAnnulezReservations") != null)
        {
            annulerReservation(request, response);
        }
        // Si le bouton precedent a été cliqué
        else if(request.getParameter("btnPrecedent") != null)
        {
            // On redirige l'utilisateur vers la page client
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/client.jsp");
            dispatcher.forward(request, response);
        }
        // Sinon
        else
        {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ClientReservation.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Cette methode sert a  annuler reservation
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void annulerReservation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            // Récupère le id Client
            String idClientParam = request.getParameter("idClientAffiche");
            int idClient = Integer.parseInt(idClientParam);
            request.setAttribute("idClient", idClientParam);
                        
            // Récupère le id Chambre
            String idChambreParam = (String) request.getParameter("idChambreAffiche");
            int idChambre = Integer.parseInt(idChambreParam);
            request.setAttribute("idChambre", idChambreParam);

            // Récupère la date de la reservation
            Date dateDebut = Date.valueOf(request.getParameter("dateDebutAffiche"));
            request.setAttribute("dateDebut", dateDebut);
            
            GestionAuberge aubergeUpdate = (GestionAuberge) request.getSession().getAttribute("aubergeUpdate");
            
            // Modification de la base de données
            synchronized (aubergeUpdate)
            {
                aubergeUpdate.getGestionReservation().annulerReservation(idClient, idChambre, dateDebut);
            }
            
            //Rafraichir la page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ClientReservation.jsp");
            dispatcher.forward(request, response);
        }
        catch (IFT287Exception e)
        {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add(e.toString());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher2 = request.getRequestDispatcher("/WEB-INF/ClientReservation.jsp");
            dispatcher2.forward(request, response);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
        }        
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }
    

}
