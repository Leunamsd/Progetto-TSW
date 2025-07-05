package it.unisa.progetto.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet Filter implementation class FiltroAccesso
 */
@WebFilter(urlPatterns = {"/Admin/*", "/User/*"})
public class FiltroAccesso implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String path = req.getRequestURI();
        boolean isAdminPath = path.startsWith(req.getContextPath() + "/Admin");
        boolean isUserPath = path.startsWith(req.getContextPath() + "/User");

        if (session == null || session.getAttribute("ruolo") == null) {
            // Nessun utente loggato
            res.sendRedirect(req.getContextPath() + "/Common/accessoNegato.jsp?errore=auth");
            return;
        }

        String ruolo = (String) session.getAttribute("ruolo");

        if (isAdminPath && !ruolo.equals("amministratore")) {
            // Utente non admin cerca di accedere a /admin
            res.sendRedirect(req.getContextPath() + "/Common/accessoNegato.jsp?errore=nonadmin");
            return;
        } /*else if (isUserPath && !ruolo.equals("user")) {
            // Utente non user cerca di accedere a /user
            res.sendRedirect(req.getContextPath() + "/accessoNegato.jsp");
            return;
        }*/

        chain.doFilter(request, response);
    }

    public void destroy() {
        
    }
}