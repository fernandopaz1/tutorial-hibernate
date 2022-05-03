package com.luv2code.hibernate.demo.oneToOne.Uni;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
								  .buildSessionFactory();

		// creamos la session
		Session session = factory.getCurrentSession();
		
		try {
			// usamos la session para guardar un objeto java
			
			// creamos un instructor
			Instructor tempInstructor = 
					new Instructor("Chad", "Darby", "darby@luv2code.com");
			
			// creamos un InstructorDetail
			InstructorDetail tempInstructorDetail =
					new InstructorDetail("youtube", "Luv 2 code!!!");
			
			// debemos asociar los dos datos 
			tempInstructor.setInstructorDetail(tempInstructorDetail);
			
			session.beginTransaction();
			
			// Por cascada esta linea tambien guardara el instructor detail
			session.save(tempInstructor);
			
			session.getTransaction().commit();
		} finally {
			factory.close(); 
		}
	}

}
