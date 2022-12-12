package Servlet;

import java.io.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import AubergeInn.GestionAuberge;
import AubergeInn.IFT287Exception;
import AubergeInn.TupleService;
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
 * Cette classe permet principalement de gérer les services
 *
 * </pre>
 */

public class Service extends HttpServlet
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
        // Si le bouton ajouter a été cliqué
        else if(request.getParameter("btnAjouter") != null)
        {
            ajouterService(request, response);
        }
        // Si le bouton supprimer a été cliqué
        else if(request.getParameter("btnSupprimer") != null)
        {
            supprimerService(request, response);
        }
        // Sinon
        else
        {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/service.jsp");
            dispatcher.forward(request, response);   
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        doGet(request, response);
    }
    
    /**
     * Cette méthode sert à supprimer un service
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void supprimerService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            // Récupère un service
            String idServiceParam= (String) request.getParameter("idServiceAffiche");
            int idService  = Integer.valueOf(idServiceParam);
            GestionAuberge aubergeUpdate = (GestionAuberge) request.getSession().getAttribute("aubergeUpdate");
            
            // Modifications de la base de données
            synchronized (aubergeUpdate)
            {
                // Pour toutes les chambres
                for(int i=0; i < aubergeUpdate.getGestionChambre().getListeChambre().size();i++)
                {
                    ArrayList<TupleService> serviceChambre = aubergeUpdate.getGestionChambre().getListeService(aubergeUpdate.getGestionChambre().getListeChambre().get(i).getIdChambre());
                    
                   // Pour chaques service
                   for(int j=0; j < serviceChambre.size(); j++)
                   {
                       // Si la chambre contient le service
                       if(serviceChambre.get(j).getIdService() == Integer.valueOf(idServiceParam))
                       {
                           // On enlève le service de la chambre
                           aubergeUpdate.getGestionChambre().enleverCommodite(aubergeUpdate.getGestionChambre().getListeChambre().get(i).getIdChambre(), Integer.valueOf(idServiceParam));
                       }
                   }
                }
                
                 // On supprime le service
                 aubergeUpdate.getGestionService().supprimerCommodite(idService);            
            }
            
            // Rafraichir la page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/service.jsp");
            dispatcher.forward(request, response);
                
        }
        catch (IFT287Exception e)
        {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add(e.toString());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher2 = request.getRequestDispatcher("/WEB-INF/service.jsp");
            dispatcher2.forward(request, response);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
        }  
    }
    
    /**
     * Cette méthode sert à ajouter un nouveau service
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void ajouterService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            // Récupère le id service
            String idServiceParam = request.getParameter("idService");
            request.setAttribute("idService", idServiceParam);

            // Validaton du format de idService
            int idService = -1;
            try
            {
                idService = Integer.parseInt(idServiceParam);
            }
            catch (NumberFormatException e)
            {
                throw new IFT287Exception("Format de idService " + idServiceParam + " incorrect.");
            }

            // Récupère le nom service
            String nomServiceParam = request.getParameter("nomService");
            request.setAttribute("nomService", nomServiceParam);

            // Récupère le prix
            String prixParam = request.getParameter("prix");
            request.setAttribute("prix", prixParam);

            // Validaton du prix
            int prix = -1;
            try
            {
                prix = Integer.parseInt(prixParam);
            }
            catch (NumberFormatException e)
            {
                throw new IFT287Exception("Format de prix " + prixParam + " incorrect.");
            }

            GestionAuberge aubergeUpdate = (GestionAuberge) request.getSession().getAttribute("aubergeUpdate");
            
            // modification de la base de données
            synchronized (aubergeUpdate)
            {
                 aubergeUpdate.getGestionService().ajouterCommodite(idService, nomServiceParam, prix);
            }
            
            // Rafraichir la page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/service.jsp");
            dispatcher.forward(request, response);
        }
        catch (IFT287Exception e)
        {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add(e.toString());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher2 = request.getRequestDispatcher("/WEB-INF/service.jsp");
            dispatcher2.forward(request, response);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
        }
    }

}
