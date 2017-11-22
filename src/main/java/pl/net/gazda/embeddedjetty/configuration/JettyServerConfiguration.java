package pl.net.gazda.embeddedjetty.configuration;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletMapping;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


@Configuration
public class JettyServerConfiguration implements ApplicationContextAware {

    private static final String DISPATCHER_SERVLET_NAME = "dispatcherServlet";
    private static final String CONTEXT_PATH = "/";

    @Value("${jetty.serverPort}")
    private int serverPort;

    @Value("${jetty.minThreadCount}")
    private Integer minThreads;

    @Value("${jetty.maxThreadCount}")
    private Integer maxThreads;

    private ApplicationContext context;


    @Bean(value = "jettyServer", destroyMethod = "stop")
    public Server jettyServer() {
        Server server = new Server(createThreadPool());
        server.setConnectors(new Connector[]{createServerConnector(server)});
        server.setHandler(createServletContextHandlerCollection());
        return server;
    }

    @NotNull
    private QueuedThreadPool createThreadPool() {
        QueuedThreadPool queuedThreadPool = new QueuedThreadPool(minThreads, maxThreads);
        queuedThreadPool.setName("jetty-thread");
        return queuedThreadPool;
    }

    @NotNull
    private ServerConnector createServerConnector(Server server) {
        ServerConnector serverConnector = new ServerConnector(server);
        serverConnector.setPort(serverPort);
        return serverConnector;
    }

    @NotNull
    private HandlerCollection createServletContextHandlerCollection() {
        HandlerCollection handler = new HandlerCollection();
        handler.setHandlers(new ServletContextHandler[]{createServletContextHandler()});
        return handler;
    }

    @NotNull
    private ServletContextHandler createServletContextHandler() {
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.setContextPath(CONTEXT_PATH);
        servletContextHandler.setServletHandler(createServletHandler());
        return servletContextHandler;
    }

    @NotNull
    private ServletHandler createServletHandler() {
        ServletHandler servletHandler = new ServletHandler();
        servletHandler.setServlets(new ServletHolder[]{getSpringDispatcherServletHolder()});
        servletHandler.setServletMappings(new ServletMapping[]{createDispatcherServletMapping()});
        return servletHandler;
    }

    @NotNull
    private ServletHolder getSpringDispatcherServletHolder() {
        ServletHolder servletHolder = new ServletHolder();
        servletHolder.setName(DISPATCHER_SERVLET_NAME);
        servletHolder.setServlet(new DispatcherServlet(createWebContext()));
        return servletHolder;
    }

    @NotNull
    private AnnotationConfigWebApplicationContext createWebContext() {
        AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
        mvcContext.setParent(context);
        mvcContext.register(MvcConfiguration.class);
        return mvcContext;
    }

    @NotNull
    private ServletMapping createDispatcherServletMapping() {
        ServletMapping servletMapping = new ServletMapping();
        servletMapping.setPathSpecs(new String[]{CONTEXT_PATH});
        servletMapping.setServletName(DISPATCHER_SERVLET_NAME);
        return servletMapping;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
