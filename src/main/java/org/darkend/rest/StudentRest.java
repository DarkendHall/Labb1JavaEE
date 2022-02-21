package org.darkend.rest;

import org.darkend.entity.Student;
import org.darkend.service.StudentService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("students")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StudentRest {


    @Inject
    StudentService studentService;

    public StudentRest() {
    }

    public StudentRest(StudentService studentService) {
        this.studentService = studentService;
    }

    @Path("")
    @POST
    public Response addStudent(Student student) {

        studentService.add(student);

        return Response.created(URI.create("/students/" + student.getId()))
                .entity(student)
                .build();
    }
}
