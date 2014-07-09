import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by igor on 23.06.2014.
 */
public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/html;charset=utf-8");

        HttpSession session = req.getSession();

        if ((req.getParameter("user") == null) && (req.getParameter("id") == null)) {

            if ((session.getAttribute("user") != null) && (session.getAttribute("id") != null)) {

                String user = (String) req.getSession().getAttribute("user");
                String idString = (String) req.getSession().getAttribute("id");

                req.getSession().setAttribute("user", null);
                req.getSession().setAttribute("id", null);

                preparingData(user, idString, resp);
            }
        } else {
            if ((!req.getParameter("user").equals("")) && (!req.getParameter("id").equals(""))) {

                String user = req.getParameter("user");
                String idString = req.getParameter("id");

                preparingData(user, idString, resp);

            } else {
                resp.sendRedirect("/");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if ((req.getSession().getAttribute("user") != null) && (req.getSession().getAttribute("id") != null)) {
            doGet(req, resp);
        } else {
            resp.sendRedirect("/");
        }
    }

    private void preparingData(String user, String idString, HttpServletResponse resp) throws IOException {

        int id = 0;
        int nextId = 0;

        try {
            id = Integer.parseInt(idString);
            nextId = id + 1;
        } catch (NumberFormatException e) {
            System.out.println("error in type of id");
            throw new NumberFormatException();
        }
        resp.getWriter().println("id = " + id + ", next id = " + nextId + ", username = " + user);
        resp.getWriter().println(
                "<form action = \"/\" method = \"GET\">\n" +
                "        <input type=\"submit\" value=\"Main\">\n" +
                "</form>"
        );
    }
}
