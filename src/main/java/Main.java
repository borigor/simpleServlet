import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 * Created by igor on 23.06.2014.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        Server server = new Server(8080);
        MyServlet myServlet = new MyServlet();
        MyFilter myFilter = new MyFilter();

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(myServlet), "/*");
        contextHandler.addFilter(new FilterHolder(myFilter), "/*", EnumSet.of(DispatcherType.REQUEST));

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("static");

        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[]{resourceHandler, contextHandler});

        server.setHandler(handlerList);

        server.start();
        server.join();

    }
}
