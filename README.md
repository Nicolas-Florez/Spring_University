🎓 Sistema de Universidad – API REST (Spring Boot + JPA + MySQL)

API REST desarrollada con Spring Boot 3, Hibernate/JPA y MySQL para gestionar estudiantes, profesores y cursos.
Incluye relaciones 1:1, 1:N, N:N, manejo de JSON sin recursión, cálculo de edad automático y endpoints completos.

🚀 Tecnologías utilizadas
Tecnología	Versión
Java	17+
Spring Boot	3.x
Spring Data JPA	✔
MySQL	✔
Maven	✔
Lombok	✔
Hibernate ORM	✔
Jackson	✔
📘 Modelo de Entidades
Relaciones principales:
Entidad	Relación	Tipo
Student → StudentDetail	1 : 1	Bidireccional
Student → Course	N : N	Bidireccional
Professor → Course	1 : N	Bidireccional
Course → Professor	N : 1	Bidireccional
🗄️ Configuración (application.properties)
spring.datasource.url=jdbc:mysql://localhost:3306/universidad?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

📚 ENDPOINTS REST
👨‍🎓 1. Estudiantes – /api/estudiantes
✅ Listar todos
GET /api/estudiantes

➕ Crear estudiante
POST /api/estudiantes

Body:
{
"nombre": "Ana Torres",
"email": "ana@mail.com",
"fechaNacimiento": "2000-05-15",
"detalle": {
"direccion": "Calle Falsa 123",
"telefono": "555-1010"
}
}

🔍 Obtener por ID
GET /api/estudiantes/{id}

📘 Asignar curso
PUT /api/estudiantes/{studentId}/cursos/{courseId}

👨‍🏫 2. Profesores – /api/profesores
Listar todos
GET /api/profesores

Crear profesor
POST /api/profesores

Body:
{
"nombre": "Profesor Jirafales",
"departamento": "Matemáticas"
}

Obtener por ID
GET /api/profesores/{id}

📘 3. Cursos – /api/cursos
Listar cursos
GET /api/cursos

Crear curso asignado a profesor
POST /api/cursos?profesorId=1

Body:
{
"nombreCurso": "Cálculo I"
}

Buscar cursos por nombre del profesor
GET /api/cursos/buscar?profesor=Juan Pérez

🧩 Cómo se evita la recursión infinita en JSON

Para evitar respuestas infinitas como:

estudiante → detalle → estudiante → detalle → ...


se usa:

@JsonIgnore


O la alternativa:

@JsonManagedReference
@JsonBackReference

🗂️ Estructura del proyecto
src/
└── main/java/com/example/universidad
├── controller/
│    ├── StudentController.java
│    ├── ProfessorController.java
│    └── CourseController.java
├── entity/
│    ├── Student.java
│    ├── StudentDetail.java
│    ├── Course.java
│    └── Professor.java
├── repository/
├── service/
└── UniversidadApplication.java

▶️ Cómo ejecutar
mvn clean install
mvn spring-boot:run


Luego acceder a:

http://localhost:8080

🧪 Pruebas recomendadas (Insomnia / Postman)

Crear profesor

Crear curso asignado al profesor

Crear estudiante con detalle

Asignar curso al estudiante

Consultar estudiante y verificar cálculo de edad

Desarrollado en clase como ejemplo de uso de SpringBoot por
Nicolas Florez