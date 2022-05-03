package com.luv2code.hibernate.demo.oneToOne.Bi;

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
			session.beginTransaction();
			
			// obtenenemos en instructor detail object
			int theId = 1000;
			InstructorDetail tempInstructorDetail = 
					session.get(InstructorDetail.class, theId);
			
			System.out.println("tempInstructorDetail: "+tempInstructorDetail);
			
			System.out.println("the assocuiated instructor: "+tempInstructorDetail.getInstructor());
			
			
			
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
