import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by igor on 23.06.2014.
 */
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        if (request.getPathInfo().equals("/auth")) {
            if (request.getParameter("login").equals("user") && request.getParameter("pass").equals("user1")) {
                request.getSession().setAttribute("isAuth", true);
            } else {
                response.sendRedirect("/");
            }
        }
        if (session.getAttribute("isAuth") == null) {

            if ((request.getParameter("user") != null) && (request.getParameter("id") != null) &&
                    (!request.getParameter("user").equals("")) && (!request.getParameter("id").equals(""))) {

                session.setAttribute("user", request.getParameter("user"));
                session.setAttribute("id", request.getParameter("id"));
            }

            response.getWriter().println(
                    "<head>\n" +
                            "    <title>auth</title>\n" +
                            "\n" +
                            "    <style type=\"text/css\">\n" +
                            "\n" +
                            "        .auth-form{\n" +
                            "            float: left;\n" +
                            "            display: block;\n" +
                            "        }\n" +
                            "\n" +
                            "        .auth__field {\n" +
                            "            clear: both;\n" +
                            "            text-align: right;\n" +
                            "            line-height: 25px;\n" +
                            "        }\n" +
                            "\n" +
                            "        label {\n" +
                            "            float: left;\n" +
                            "            padding-right: 10px;\n" +
                            "        }\n" +
                            "\n" +
                            "        .auth__button {\n" +
                            "            clear: both;\n" +
                            "            text-align: center;\n" +
                            "            padding-top: 20px;;\n" +
                            "        }\n" +
                            "    </style>\n" +
                            "</head>\n" +
                            "\n" +
                            "<body>\n" +
                            "\n" +
                            "    <div class=\"auth-form\">\n" +
                            "        <form action = \"/auth\" method = \"POST\">\n" +
                            "            <div class=\"auth__field\">\n" +
                            "                <label for=\"login\">Login</label>\n" +
                            "                <input type=\"text\" name=\"login\" id=\"login\" />\n" +
                            "            </div>\n" +
                            "\n" +
                            "            <div class=\"auth__field\">\n" +
                            "                <label for=\"pass\">Password</label>\n" +
                            "                <input type=\"password\" name=\"pass\" id=\"pass\" />\n" +
                            "            </div>\n" +
                            "\n" +
                            "            <div class=\"auth__button\">\n" +
                            "                <input type=\"submit\" value=\"Submit\">\n" +
                            "            </div>\n" +
                            "        </form>\n" +
                            "\n" +
                            "    </div>\n" +
                            "\n" +
                            "</body>");

        } else if (request.getSession().getAttribute("isAuth").equals(true)) {
                filterChain.doFilter(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
