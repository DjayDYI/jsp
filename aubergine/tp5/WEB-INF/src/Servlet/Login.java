package Servlet;

import java.sql.*;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import AubergeInn.GestionAuberge;
import AubergeInn.IFT287Exception;

/**
 * Classe pour login système de gestion de bibliothèque
 * <P>
 * Système de gestion de bibliothèque
 * 2004 Marc Frappier,
 * Université de Sherbrooke
 */

public class Login extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            HttpSession session = request.getSession();
            // fermer la session si elle a déjà été ouverte lors d'un appel
            // précédent
            // survient lorsque l'usager recharge la page login.jsp
            if (session.getAttribute("etat") != null)
            {
                // pour déboggage seulement : afficher no session et information
                System.out.println("GestionAubergInn: session déja crée; id=" + session.getId());
                // la méthode invalidate appelle le listener
                // BiblioSessionListener; cette classe est chargée lors du
                // démarrage de
                // l'application par le serveur (voir le fichier web.xml)
                session.invalidate();
                session = request.getSession();
                System.out.println("GestionAubergInn: session invalidée");
            }

            // lecture des paramètres du formulaire login.jsp
            String userId = request.getParameter("userIdBD");
            String motDePasse = request.getParameter("motDePasseBD");
            String serveur = request.getParameter("serveur");
            String bd = request.getParameter("bd");

            if (serveur != null)
            {
                /*
                 * ouvrir une connexion avec la BD et créer les gestionnaires et
                 * stocker dans la session. On ouvre une session en lecture
                 * readcommited pour les interrogations seulement et une autre
                 * en mode serialisable, pour les transactions
                 */
                System.out.println("Login: session id=" + session.getId());
                GestionAuberge auberge = new GestionAuberge(serveur, bd, userId, motDePasse);
                auberge.getConnexion().setIsolationReadCommited();
                session.setAttribute("aubergeInterrogation", auberge);
                GestionAuberge aubergeUpdate = new GestionAuberge(serveur, bd, userId, motDePasse);
                session.setAttribute("aubergeUpdate", aubergeUpdate);

                // afficher le menu membre en appelant la page
                // selectionMembre.jsp
                // tous les JSP sont dans /WEB-INF/
                // ils ne peuvent pas être appelés directement par l'utilisateur
                // seulement par un autre JSP ou un servlet
                
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
                dispatcher.forward(request, response);
                session.setAttribute("etat", new Integer(AubergInnConstantes.CONNECTE));
            }
            else
            {
                throw new SQLException("Vous devez vous connecter au serveur.");
            }
        }
        catch (SQLException e)
        {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add("Erreur de connexion au serveur");
            listeMessageErreur.add(e.toString());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
            // pour déboggage seulement : afficher tout le contenu de
            // l'exception
            e.printStackTrace();
        }
        catch (IFT287Exception e)
        {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // Dans les formulaires, on utilise la méthode POST
    // donc, si le servlet est appelé avec la méthode GET
    // on appelle POST
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }

} // class
