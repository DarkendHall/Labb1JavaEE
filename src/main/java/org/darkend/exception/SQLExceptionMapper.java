package org.darkend.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.sql.SQLException;

@Provider
public class SQLExceptionMapper implements ExceptionMapper<SQLException> {
    @Override
    public Response toResponse(SQLException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(e.getMessage()
                        .replace(" ", "_"))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}