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
 * Ce servlet sert principalement a gèrer les chambres
 *
 * </pre>
 */

public class Chambre extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Si l'état est null on renvoie au login
        Integer etat = (Integer) request.getSession().getAttribute("etat");
        if (etat == null)
        {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        }
        // Si bouton ajouter Chambre a été cliqué
        else if(request.getParameter("btnAjouterChambre") != null)
        {
            ajouterChambre(request, response);
        }
        // Si bouton supprimer Chambre a été cliqué
        else if(request.getParameter("btnSupprimer") != null)
        {
            supprimerChambre(request, response);
        }
        // Si bouton ajouter commodité a été cliqué
        else if(request.getParameter("btnAjouterCommodite") != null)
        {
            // On appelle la page ChambreService pour inclure les commodités
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ChambreService.jsp");
            request.setAttribute("idChambreAffiche", request.getParameter("idChambreAffiche"));
            dispatcher.forward(request, response); 
        }
        // Si bouton details a été cliqué
        else if(request.getParameter("btnDetails")!=null)
        {
            // On appelle la page chambre detail pour afficher les detail de la chambre
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ChambreDetail.jsp");
            request.setAttribute("idChambreAffiche", request.getParameter("idChambreAffiche"));
            dispatcher.forward(request, response); 
        }
        // Sinon on affiche la page
        else
        {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/chambre.jsp");
            dispatcher.forward(request, response);   
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }
    
    /**
     * Cette methode sert a supprimer une chambre selon les informations
     * envoyées par la page jsp
     * @param request
     * @param response 
     * @throws ServletException
     * @throws IOException
     */
    private void supprimerChambre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            String idChambreParam= request.getParameter("idChambreAffiche");
            int idChambre  = Integer.valueOf(idChambreParam);
            GestionAuberge aubergeUpdate = (GestionAuberge) request.getSession().getAttribute("aubergeUpdate");
            
            // Modification sur la base de donnée
            synchronized (aubergeUpdate)
            {
                // S'assure d'enlever les commdoite de la chmabre a supprimer par soucis de contraintes
                for(int i=0; i< aubergeUpdate.getGestionChambre().getListeService(idChambre).size();i++)
                {
                    aubergeUpdate.getGestionChambre().enleverCommodite(idChambre, aubergeUpdate.getGestionChambre().getListeService(idChambre).get(i).getIdService()); 
                }
                // Supprime la chambre
                aubergeUpdate.getGestionChambre().supprimerChambre(idChambre);
            }
            
            // Rafraichir la page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/chambre.jsp");
            dispatcher.forward(request, response);
                
        }
        catch (IFT287Exception e)
        {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add(e.toString());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/chambre.jsp");
            dispatcher.forward(request, response);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
        }      
    }
    
    /**
     * Cette méthode sert a recupérer les informations dans 
     * les champs approprié de la page jsp et ajouter une nouvelle chambre 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void ajouterChambre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            // Récupère la valeur du ID
            String idChambreParam = request.getParameter("idChambre");
            request.setAttribute("idChambre", idChambreParam);

            // Validation du format recu dans le textbox de l'id
            int idChambre = -1;
            try
            {
                idChambre = Integer.parseInt(idChambreParam);
            }
            catch (NumberFormatException e)
            {
                throw new IFT287Exception("Format de idChambre " + idChambreParam + " incorrect.");
            }

            // Récupérer le nom de la chambre
            String nomChambreParam = request.getParameter("nomChambre");
            request.setAttribute("nomChambre", nomChambreParam);

            // Récupér le type de lit
            String typeLitParam = request.getParameter("typeLit");
            request.setAttribute("typeLit", typeLitParam);

            // Recupère la valeur du prix
            String prixParam = request.getParameter("prix");
            request.setAttribute("prix", prixParam);

            // Validation du format recu dans le textbox de prix
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

            // Modification sur la base de donnée
            synchronized (aubergeUpdate)
            {
                 aubergeUpdate.getGestionChambre().ajouterChambre(idChambre, nomChambreParam, typeLitParam, prix);
            }
            
            // Rafraichir la page
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
