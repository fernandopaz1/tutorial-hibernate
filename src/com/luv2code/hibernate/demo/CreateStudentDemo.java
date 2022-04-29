package com.luv2code.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Student;

public class CreateStudentDemo {

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
			
			// creamos el student
			System.out.println("Creating new student object...");
			Student student = new Student("Fernando", "Paz", "ferpaz@luv2code.com");
			
			// empezamos la transaccion
			System.out.println("Iniciamos la transaccion...");
			session.beginTransaction();
			
			// guardamos el objeto
			System.out.println("Guardamos el objeto en la base de datos");
			session.save(student);
			
			// commiteamos la transaccion
			System.out.println("Commiteamos la transacción");
			session.getTransaction().commit();
		} finally {
			factory.close();
		}
	}

}
