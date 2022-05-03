package com.luv2code.hibernate.demo.manyToOne.Bi;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Course;
import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;

public class CreateDemo {

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
			
			// creamos los objetos
			Instructor tempInstructor = 
					new Instructor("Susan", "Perez", "susanp@luv2code.com");
			
			InstructorDetail tempInstructorDetail =
					new InstructorDetail("youtube", "Guitar");
			
			// asociamos los objetos
			tempInstructor.setInstructorDetail(tempInstructorDetail);
			
			session.beginTransaction();

			session.save(tempInstructor);
			
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
