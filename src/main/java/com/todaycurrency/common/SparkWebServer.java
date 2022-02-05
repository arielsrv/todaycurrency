package com.todaycurrency.common;

import com.todaycurrency.Application;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import spark.servlet.SparkFilter;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

import static java.lang.String.format;

public class SparkWebServer {

    private static final Logger logger = LogManager.getLogger(SparkWebServer.class);

    public static void run(int serverPort, Class<Application> applicationClass) throws Exception {

        Server server = new Server(serverPort);
        ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");

        FilterHolder filterHolder = new FilterHolder(SparkFilter.class);
        filterHolder.setInitParameter("applicationClass", applicationClass.getName());
        context.addFilter(filterHolder, "/*", EnumSet.of(DispatcherType.REQUEST));

        server.start();
        logger.info(format("Listening on %d ...", serverPort));
        server.join();
    }
}
