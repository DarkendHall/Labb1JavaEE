package org.darkend.rest;

import org.darkend.entity.Student;
import org.darkend.service.StudentService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("students")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StudentRest {


    @Inject
    StudentService studentService;

    @Path("")
    @POST
    public Response addStudent(Student student) {
        studentService.add(student);

        return Response.created(URI.create("/labb1/students/" + student.getId()))
                .entity(student)
                .build();
    }

    @Path("{id}")
    @GET
    public Response getStudent(@PathParam("id") Long id) {
        Student foundStudent = studentService.get(id);

        return Response.ok(foundStudent)
                .build();
    }

    @Path("{id}")
    @PATCH
    public Response patchStudent(@PathParam("id") Long id, Student student) {
        Student patchedStudent = studentService.patch(id, student);

        return Response.ok(patchedStudent)
                .build();
    }

    @Path("{id}")
    @PUT
    public Response updateStudent(@PathParam("id") Long id, Student student) {
        studentService.update(id, student);

        return Response.ok(student)
                .build();
    }

    @Path("{id}")
    @DELETE
    public Response removeStudent(@PathParam("id") Long id) {
        Student studentToRemove = studentService.remove(id);

        return Response.ok(studentToRemove)
                .build();
    }

    @Path("")
    @GET
    public Response getAllStudents() {
        return Response.ok(studentService.getAll())
                .build();
    }

    @Path("query")
    @GET
    public Response getAllStudents(@QueryParam("lastName") String lastName) {
        return Response.ok(studentService.getAll(lastName))
                .build();
    }
}
