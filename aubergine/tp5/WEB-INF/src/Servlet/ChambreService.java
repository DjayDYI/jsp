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
import AubergeInn.TupleChambre;

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
 * Cette classe principalement a inclure un service dans une chambre
 *
 * </pre>
 */

public class ChambreService extends HttpServlet
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
        // Si le bouton inclure commodite a été cliqué
        else if(request.getParameter("btnInclure") != null)
        {
            inclureService(request, response);
        }
        // Si le bouton précédent a été cliqué
        else if(request.getParameter("btnPrecedent") != null)
        {
            // Redirige vers la page chambre.jsp
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/chambre.jsp");
            dispatcher.forward(request, response);
        }
        // Sinon
        else
        {
            try
            {
                String idChambreParam= request.getParameter("idChambreAffiche");
                GestionAuberge aubergeUpdate = (GestionAuberge) request.getSession().getAttribute("aubergeUpdate");
                
                // Acces à la base de données
                synchronized (aubergeUpdate)
                {
                        TupleChambre tc=aubergeUpdate.getGestionChambre().afficherChambre(Integer.parseInt(idChambreParam));
                        request.setAttribute("idChambreAffiche",  tc.getIdChambre());
                        request.setAttribute("nomChambreAffiche",  tc.getNomChambre());
                        request.setAttribute("prixChambreAffiche",  tc.getPrix());
                        request.setAttribute("typeLitAffiche",tc.getTypeLit());
                }
                
                // Rafraichir la page
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ChambreService.jsp");
                dispatcher.forward(request, response); 
            }
            catch (IFT287Exception e)
            {
                List<String> listeMessageErreur = new LinkedList<String>();
                listeMessageErreur.add(e.toString());
                request.setAttribute("listeMessageErreur", listeMessageErreur);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ChambreService.jsp");
                dispatcher.forward(request, response);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
            }
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ChambreService.jsp");
            dispatcher.forward(request, response);   
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }
    
    /**
     * Cette méthode permet d'inclure un service
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void inclureService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            // Récupère le id chambre
            String idChambreParam = request.getParameter("chambres");
            request.setAttribute("idChambre", idChambreParam);
            
            // Récupeère le idService
            String idServiceParam = request.getParameter("services");
            request.setAttribute("idService", idServiceParam);

            GestionAuberge aubergeUpdate = (GestionAuberge) request.getSession().getAttribute("aubergeUpdate");

            // Modification de la base de donnée
            synchronized (aubergeUpdate)
            {
                aubergeUpdate.getGestionChambre().inclureCommodite(Integer.valueOf(idChambreParam), Integer.valueOf(idServiceParam));
            }
            
            // Si le service a bien été ajouter on redirige l'utilisateur verds la page chambre.jsp
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/chambre.jsp");
            dispatcher.forward(request, response);
        }
        catch (IFT287Exception e)
        {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add(e.toString());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher2 = request.getRequestDispatcher("/WEB-INF/chambre.jsp");
            dispatcher2.forward(request, response);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
        }
    }
}
