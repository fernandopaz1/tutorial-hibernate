package com.luv2code.hibernate.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Student;

public class QueryStudentDemo {

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
			session.beginTransaction();
			
			System.out.println("Todos los estudiantes");
			List<Student> theStudents = session.createQuery("from Student").getResultList();
			displayStudents(theStudents);
			
			System.out.println("Todos los estudiantes con apellido Duck");
			theStudents = session.createQuery("from Student s where s.lastName='Duck'").getResultList();
			displayStudents(theStudents);
			
			System.out.println("Todos con apellido Paz o primer nombre Daffy");
			theStudents = session.createQuery("from Student s where s.lastName='Paz' or s.firstName='Daffy'").getResultList();
			displayStudents(theStudents);
			
			System.out.println("Todos con email que termine en luv2code.com");
			theStudents = session.createQuery("from Student s where s.email LIKE '%luv2code.com'").getResultList();
			displayStudents(theStudents);
		
			session.getTransaction().commit();
		} finally {
			factory.close();
		}
	}

	private static void displayStudents(List<Student> theStudents) {
		theStudents.stream().forEach(System.out::println);
	}

}
