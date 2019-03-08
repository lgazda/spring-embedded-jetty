# spring-embedded-jetty
spring mvc + embedded jetty as a spring bean 

# main note

jetty on init -> inits servlet -> spring dispatcher servlet (already has web context) -> dispatcher servlet on init sets the servlet context in web context and refreshes the web context

`org.springframework.web.servlet.FrameworkServlet#configureAndRefreshWebApplicationContext`
```java
wac.setServletContext(getServletContext());
wac.setServletConfig(getServletConfig());
wac.setNamespace(getNamespace());
...
wac.refresh();
```

to see what and when is happening exactly you can set a brake point on
`org.springframework.web.context.support.AbstractRefreshableWebApplicationContext#setServletContext`
