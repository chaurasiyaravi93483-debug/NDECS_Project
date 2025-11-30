package com.ndecs.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Central auth filter:
 *  - redirects to login.jsp if not logged in (for protected pages)
 *  - redirects to no_access.jsp if user is not admin but accessing admin-only pages
 *
 * Configure mapping in web.xml or use @WebFilter("/*") — we'll add mapping in web.xml below.
 */
public class AuthFilter implements Filter {

    // public (no-login) paths - allow anyone (add view resources if needed)
    private final Set<String> publicPaths = new HashSet<>(Arrays.asList(
            "/login.jsp", "/LoginServlet", "/css/", "/js/", "/images/",
            "/report_incident.jsp", "/index.jsp", "/testConnection.jsp"
    ));

    // admin-only paths (exact or prefix). Add servlet mappings or jsp names used for admin tasks.
    private final Set<String> adminPaths = new HashSet<>(Arrays.asList(
            "/AddTeamServlet",
            "/add_team.jsp",
            "/RegisterUserServlet",
            "/register_user.jsp",
            "/UserListServlet",
            "/DeleteUserServlet",
            "/AssignTeamServlet",
            "/PrepareAssignServlet",
            "/AssignmentHistoryServlet",
            "/EditIncidentServlet",
            "/deleteIncident", // if you have a delete servlet mapping
            "/team_list.jsp" // if managing teams only admin
            // add more as needed
    ));

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // nothing
    }

    private boolean startsWithAny(String path, Set<String> prefixes) {
        for (String p : prefixes) {
            if (p.endsWith("/")) {
                if (path.startsWith(p)) return true;
            } else {
                if (path.equals(p) || path.startsWith(p + "?") ) return true;
                if (path.startsWith(p + "/")) return true;
            }
        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());
        // normalize trailing
        if (path.isEmpty()) path = "/";

        // allow static resources quickly (css/js/images in /lib or /static) - tweak if needed
        if (path.startsWith("/resources/") || path.startsWith("/static/") || path.endsWith(".css") || path.endsWith(".js") || path.endsWith(".png") || path.endsWith(".jpg") || path.endsWith(".svg")) {
            chain.doFilter(request, response);
            return;
        }

        // allow public pages
        if (startsWithAny(path, publicPaths)) {
            chain.doFilter(request, response);
            return;
        }

        // check session / login
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            // not logged in -> redirect to login (preserve original target if you want)
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        // logged in user
        Object uobj = session.getAttribute("user");
        String role = null;
        try {
            // user object is com.ndecs.model.User
            java.lang.reflect.Method m = uobj.getClass().getMethod("getRole");
            Object r = m.invoke(uobj);
            if (r != null) role = r.toString();
        } catch (Exception ignore) {
            // fallback if reflection fails
            try {
                role = (String) session.getAttribute("role");
            } catch (Exception ex) { role = null; }
        }

        boolean isAdmin = role != null && "admin".equalsIgnoreCase(role);

        // Check admin-only paths
        if (startsWithAny(path, adminPaths) && !isAdmin) {
            // Access denied for non-admin
            resp.sendRedirect(req.getContextPath() + "/no_access.jsp");
            return;
        }

        // otherwise allowed
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // nothing
    }
}
