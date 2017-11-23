# spring-embedded-jetty
spring mvc + embedded jetty as a spring bean 

# main note

jetty on it's init -> init servlet -> spring dispatcher servlet (has web context) -> dispatcher servlet on it's init sets the servlet context in web context and refreshes the web context

'org.springframework.web.servlet.FrameworkServlet#configureAndRefreshWebApplicationContext
'''java
wac.setServletContext(getServletContext());
wac.setServletConfig(getServletConfig());
wac.setNamespace(getNamespace());
...
wac.refresh();
```

to see what and when is happening exactly you can set a brake point on
`org.springframework.web.context.support.AbstractRefreshableWebApplicationContext#setServletContext
