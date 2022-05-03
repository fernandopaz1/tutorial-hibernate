package com.luv2code.hibernate.demo.manyToOne.Uni;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Course;
import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;
import com.luv2code.hibernate.demo.entity.Review;

public class CreateDemo {

	public static void main(String[] args) {

		// creamos la SessionFactory
		// si no especificamos el nombre del archivo de configuraciones
		// por defecto busca el nombre hibernate.cfg.xml
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Instructor.class)
								.addAnnotatedClass(InstructorDetail.class)
								.addAnnotatedClass(Course.class)
								.addAnnotatedClass(Review.class)
								.buildSessionFactory();

		// creamos la session
		Session session = factory.getCurrentSession();

		try {
			// usamos la session para guardar un objeto java

			session.beginTransaction();

			//creamos un curso
			Course tempCourse = new Course("Pacman - How to score on million points");
			
			// añadimos reviews
			tempCourse.add(new Review("Great course ... loove it"));
			tempCourse.add(new Review("Cool course"));
			tempCourse.add(new Review("Booring"));

			// cuardamos todo con cascada
			session.save(tempCourse);
			// commit transaccion
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
	}
}
