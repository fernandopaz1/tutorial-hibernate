package com.luv2code.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Student;

public class ReadStudentDemo {

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
			Student student = new Student("Pepe", "Pepez", "pepe@luv2code.com");
			
			// empezamos la transaccion
			System.out.println("Iniciamos la transaccion...");
			session.beginTransaction();
			
			// guardamos el objeto
			System.out.println("Guardamos el objeto en la base de datos");
			session.save(student);
			
			// commiteamos la transaccion
			System.out.println("Commiteamos la transacción");
			session.getTransaction().commit();
			
			// leemos el student desde la base de datos
			System.out.println("Estudiante guardad. Id Asignada: "+student.getId());
			
			// para leer creamos una nueva session y transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			System.out.println("Obtenemos el estudiante con id: "+student.getId());
			Student myStudent = session.get(Student.class, student.getId());
			System.out.println("El estudiante obtenido es: " +myStudent);
			// cerramos la transaccion
			session.getTransaction().commit();
			
		} finally {
			factory.close();
		}
	}

}
