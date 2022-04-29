package com.luv2code.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Student;

public class DeleteStudentDemo {

	public static void main(String[] args) {
		
		// creamos la SessionFactory
		// si no especificamos el nombre del archivo de configuraciones
		// por defecto busca el nombre hibernate.cfg.xml
		SessionFactory factory  = new Configuration()
								  .configure("hibernate.cfg.xml")
								  .addAnnotatedClass(Student.class)
								  .buildSessionFactory();

		// creamos la session
		Session session = factory.getCurrentSession();
		
		try {
			// usamos la session para guardar un objeto java
			int studentId = 1;
			session.beginTransaction();
			
			// Obteniendo el student por id
			System.out.println("Obteniendo estudiante de id: "+studentId);
			Student myStudent = session.get(Student.class, studentId);

			
			// para borrar pasamos el objeto
			session.delete(myStudent);
			
			// otra forma es mediante una query
			session.createQuery("delete from Student where id=2").executeUpdate();
			
			
			session.getTransaction().commit();
			
		
		
			
		} finally {
			factory.close();
		}
	}

}
