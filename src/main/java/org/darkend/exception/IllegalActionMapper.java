package org.darkend.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IllegalActionMapper implements ExceptionMapper<IllegalActionException> {

    @Override
    public Response toResponse(IllegalActionException e) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ExceptionJson(Response.Status.NOT_FOUND.getStatusCode(), e.getMessage()))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
