package controller;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;

@HttpsOnly
public class HttpsOnlyFilter implements ContainerRequestFilter {
    public void filter(ContainerRequestContext requestContext) {
        if (!requestContext.getUriInfo().getRequestUri().getScheme().equals("https")) {
            Response response = Response.status(200).entity("https only filter error").build();
            requestContext.abortWith(response);
        }
    }
}