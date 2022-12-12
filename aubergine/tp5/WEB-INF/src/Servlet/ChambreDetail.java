package Servlet;

import java.io.IOException;
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
 * Ce servlet sert principalement a gérer la page
 * detailChambre.jsp
 *
 * </pre>
 */

public class ChambreDetail extends HttpServlet
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
        // Si le bouton enlever commodité a été cliqué
        else if(request.getParameter("btnEnleverService") != null)
        {
            enleverService(request, response);
        }
        // Si le bouton enlever précédent a été cliqué
        else if(request.getParameter("btnPrecedent") != null)
        {
            // On redirige l'utilisateur vers chambre.jsp
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/chambre.jsp");
            dispatcher.forward(request, response); 
        }
        // Sinon
        else
        {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ChambreDetail.jsp");
            dispatcher.forward(request, response); 
        }
    }

    /**
     * Cette methode enlève un service
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void enleverService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            // Récupère le id de la chambre
            String idChambreParam = request.getParameter("idChambreAffiche");
            int idChambre = Integer.parseInt(idChambreParam);
            request.setAttribute("idChambre", idChambreParam);
            
            // Récupère le id du service
            String idServiceParam = (String) request.getParameter("idServiceAffiche");
            int idService = Integer.parseInt(idServiceParam);
            request.setAttribute("idService", idServiceParam);

            GestionAuberge aubergeUpdate = (GestionAuberge) request.getSession().getAttribute("aubergeUpdate");
            
            // Modification de la base de données
            synchronized (aubergeUpdate)
            {
                aubergeUpdate.getGestionChambre().enleverCommodite(idChambre, idService);
            }
            
            // Rafraichir la page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ChambreDetail.jsp");
            dispatcher.forward(request, response);
        }
        catch (IFT287Exception e)
        {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add(e.toString());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher2 = request.getRequestDispatcher("/WEB-INF/ChambreDetail.jsp");
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
