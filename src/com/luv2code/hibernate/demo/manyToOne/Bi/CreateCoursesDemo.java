package com.luv2code.hibernate.demo.manyToOne.Bi;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Course;
import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;

public class CreateCoursesDemo {

public static void main(String[] args) {
		
		// creamos la SessionFactory
		// si no especificamos el nombre del archivo de configuraciones
		// por defecto busca el nombre hibernate.cfg.xml
		SessionFactory factory  = new Configuration()
								  .configure("hibernate.cfg.xml")
								  .addAnnotatedClass(Instructor.class)
								  .addAnnotatedClass(InstructorDetail.class)
								  .addAnnotatedClass(Course.class)
								  .buildSessionFactory();

		// creamos la session
		Session session = factory.getCurrentSession();
		
		try {
			// usamos la session para guardar un objeto java
			
			session.beginTransaction();

			// obtenemos el instructor de la base de datos
			int theId = 1;
			Instructor tempInstructor = session.get(Instructor.class, theId);
			
			// creamos cursos
			Course tempCourse1 = new Course("Air guitar");
			Course tempCourse2 = new Course("Pinball masterclass");
			
			// agregamos los cursos al instructor
			tempInstructor.add(tempCourse1);
			tempInstructor.add(tempCourse2);
			
			// guardamos los cursos
			session.save(tempCourse1);
			session.save(tempCourse2);
			// commit transaccion		
			session.getTransaction().commit();
		}
		catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
			factory.close(); 
		}
	}
}
