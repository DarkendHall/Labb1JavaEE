## Laboration 1

This is the GitHub repository for Laboration 1 in the course "Java Enterprise".

### Format for Student JSON is:

```json
{
  "id": 123,
  "firstName": "somefirstname",
  "lastName": "somelastname",
  "email": "some@email.address",
  "phoneNumber": "somephonenumber"
}
```

Field "id" is only needed when updating and field "phoneNumber" is optional.

### ENDPOINTS:

- /labb2/students         (addStudent(Student student), POST)\
- /labb2/students/{id}    (getStudent(@PathParam("id") Long id), GET)\
- /labb2/students/{id}    (patchStudent(@PathParam("id") Long id, Student student), PATCH)\
- /labb2/students/{id}    (updateStudent(@PathParam("id") Long id, Student student), PUT)\
- /labb2/students/{id}    (removeStudent(@PathParam("id") Long id, DELETE)\
- /labb2/students         (getAllStudents, GET)\
- /labb2/students/query   (getAllStudents(@QueryParam("lastName") String lastName), GET)

### Import for Insomnia

https://github.com/DarkendHall/Labb1JavaEE/blob/main/Insomnia_requests.json
