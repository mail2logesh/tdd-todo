package io.framework.todo.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;


//@TODO: Context Path to be externalized
public class JettyServer {
    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(args[0]);
        String warPath = args[1];

        Server server = new Server(port);
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/todo");
        webapp.setWar(warPath);

        server.setHandler(webapp);
        server.start();
        server.join();
    }

}
