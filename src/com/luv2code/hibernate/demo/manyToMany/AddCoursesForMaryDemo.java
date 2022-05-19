package com.luv2code.hibernate.demo.manyToMany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Course;
import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;
import com.luv2code.hibernate.demo.entity.Review;
import com.luv2code.hibernate.demo.entity.Student;

public class AddCoursesForMaryDemo {

public static void main(String[] args) {
		
		// creamos la SessionFactory
		// si no especificamos el nombre del archivo de configuraciones
		// por defecto busca el nombre hibernate.cfg.xml
		SessionFactory factory  = new Configuration()
								  .configure("hibernate.cfg.xml")
								  .addAnnotatedClass(Instructor.class)
								  .addAnnotatedClass(InstructorDetail.class)
								  .addAnnotatedClass(Course.class)
								  .addAnnotatedClass(Review.class)
								  .addAnnotatedClass(Student.class)
								  .buildSessionFactory();

		// creamos la session
		Session session = factory.getCurrentSession();
		
		try {
			// usamos la session para guardar un objeto java
			
			session.beginTransaction();
			
			// obtenemos a mary de la base de datos
			int studentId = 2;
			Student tempStudent = session.get(Student.class, studentId);
			
			System.out.println("\nLoaded student: "+ tempStudent);
			System.out.println("Courses: "+tempStudent.getCourses() );

			Course tempCourse1 = new Course("Rubik's Cube - How to speed cube");
			Course tempCourse2 = new Course("Atari 2600 - Game development");
			
			tempCourse1.addStudent(tempStudent);
			tempCourse2.addStudent(tempStudent); 
			
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
