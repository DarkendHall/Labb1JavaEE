## Laboration 1

This is the GitHub repository for Laboration 1 in the course "Java Enterprise".

### Format for Student JSON is:

{ \
"firstName": "somefirstname", \
"lastName": "somelastname", \
"email": "some@email.address", \
"id": 123, (only needed when updating with PUT), \
"phoneNumber": "somephonenumber" (field is a String, and optional) \
}

### ENDPOINTS:

/labb1/students         (addStudent(Student student), POST)

/labb1/students/{id}    (getStudent(@PathParam("id") Long id), GET)

/labb1/students/{id}    (updateStudent(@PathParam("id") Long id, Student student), PATCH)

/labb1/students/{id}    (updateStudent(@PathParam("id") Long id, Student student), PUT)

/labb1/students/{id}    (removeStudent(@PathParam("id") Long id, DELETE)

/labb1/students         (getAllStudents, GET)

/labb1/students/query   (getAllStudents(@QueryParam("lastName") String lastName), GET)

### Import for Insomnia

https://github.com/DarkendHall/Labb1JavaEE/blob/main/Insomnia_requests.json
