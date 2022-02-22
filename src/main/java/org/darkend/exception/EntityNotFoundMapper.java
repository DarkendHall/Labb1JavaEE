package org.darkend.exception;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityNotFoundMapper implements ExceptionMapper<EntityNotFoundException> {

    @Override
    public Response toResponse(EntityNotFoundException e) {
        return Response.status(404, "Not Found")
                .entity(e.getMessage().replace(" ", "_"))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
