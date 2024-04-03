
SpringBoot 3, Java 17

		Para lanzar los servicios hay que usar Docker		
		
1. Desde la raiz del proyecto ejecutar "docker compose up"
2. Revisar los endpoints :
	- course (http://localhost:8080/swagger-ui/index.html)
	- student (http://localhost:8081/swagger-ui/index.html)	
3. Revisar trabajo de RabbbitMQ
http://localhost:15672/
guest
guest	

Los endpoints permiten crear, leer, actualizar, eliminar y listar cursos.

Ademas en el microservicio Student ( StudentController ) tenemos dos endpoints (enrollStudentToCourse, removeStudentFromCourse) que hacen los cambios en base de datos de servicio Curso.

RabbitMq
removeStudentFromCourse (com/malykh/student/controller/StudentController.java) intenté hacer usando RabbitTemplate (he dejado el codigo commentado)
Sale error que no se puede encontrar viartual host (vhost), pero lo tengo configurado en RabbbitMQ, tambien en Docker-compose.yml.
Entonces lo deje en este paso
Archivo de configuracion RabbitMQ esta aqui com/malykh/student/RabbitMQ/MQConfiguration.java
Listener esta aqui com/malykh/course/RabbitMQ/Reciever.java


