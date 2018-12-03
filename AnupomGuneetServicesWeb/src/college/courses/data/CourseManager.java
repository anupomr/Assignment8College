package college.courses.data;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import college.courses.dto.Course;
import college.courses.dto.Professor;
import college.courses.exceptions.CourseNotFoundException;
import college.courses.exceptions.DuplicateCourseException;
import college.courses.exceptions.ImproperInputException;

public class CourseManager {

	public CourseManager() {
		super();
	}

	public Collection<Course> getAllCourses() {
		EntityManagerFactory emf = EMFSupplier.getInstance().getEMF();
		EntityManager em = emf.createEntityManager();
		TypedQuery<Course> tq = em.createNamedQuery("getAllCourses", Course.class);
		return tq.getResultList();
	}
	
	public Course getCourse(String courseCode) throws CourseNotFoundException {
		EntityManagerFactory emf = EMFSupplier.getInstance().getEMF();
		EntityManager em = emf.createEntityManager();
		Course course = em.find(Course.class, courseCode);
		if (course == null) {
			throw new CourseNotFoundException("Course code " + courseCode+" not found !!");
		}
		return course;
	}
	private Set<Professor> addProfessors(Set<Professor> profs, EntityManager em ) {
		Set<Professor> professors = new HashSet<Professor>();
		for (Professor professor : profs) {		
			if (professor.getPid() == 0) {
				TypedQuery<Professor> tq = em.createNamedQuery("getProfByName", Professor.class);
				tq.setParameter("fName", professor.getFirstName());
				tq.setParameter("middle", professor.getMiddleName());
				tq.setParameter("last", professor.getLastName());
				try {
					Professor existProf = tq.getSingleResult();					
					professors.add(existProf);
					//em.persist(professor);
				} catch (NoResultException e) {
					em.persist(professor);
					professors.add(professor);
				}
			}
		}
		return professors;
	}
	
	public Course addCourse(Course course) throws DuplicateCourseException {
		EntityManagerFactory emf = EMFSupplier.getInstance().getEMF();
		EntityManager em = emf.createEntityManager();
		Course courseObj = em.find(Course.class, course.getCourseCode());
		if (courseObj != null) {
			throw new DuplicateCourseException("Course with code " + course.getCourseCode()+" is already Exists!!");
		}
		EntityTransaction et = em.getTransaction();
		et.begin();
		Set<Professor> professors = course.getProfessors();
		if (professors != null) {		
			professors = addProfessors( professors, em );
		}
		course.setProfessors(professors);
		
		em.persist(course);
		et.commit();
		em.close();
		return course;
	}
	public Course updateCourse(Course c) throws CourseNotFoundException {
		EntityManagerFactory emf = EMFSupplier.getInstance().getEMF();
		EntityManager em = emf.createEntityManager();
		Course course = em.find(Course.class, c.getCourseCode());
		if (course == null) {
			throw new CourseNotFoundException("Not Found " + c.getCourseCode());
		}
		Set<Professor> oldProfs = course.getProfessors();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			course.setCourseTitle(c.getCourseTitle());
			course.setCapacity(c.getCapacity());
			course.setEnrolled(c.getEnrolled());
			Set<Professor> professors = c.getProfessors();
			if (professors != null) {
				course.setProfessors( addProfessors ( professors, em ) );
			} else {
				course.setProfessors(null);
			}
			if (oldProfs != null) {
				removeProfFromCourse(oldProfs, em);
			}			
			//em.merge(professors);
			em.merge(course);
			et.commit();
		} catch (ImproperInputException e) {
			et.rollback();
			throw new CourseNotFoundException("Exsisted course " + c.getCourseCode());
		} finally {
			em.close();
		}
		return course;
	}
	public Course deleteCourse(String courseCode) throws CourseNotFoundException {
		EntityManagerFactory emf = EMFSupplier.getInstance().getEMF();
		EntityManager em = emf.createEntityManager();
		Course courseObj = em.find(Course.class, courseCode);
		if (courseObj == null) {
			throw new CourseNotFoundException("Not Found Course code: " + courseCode);
		}
		Set<Professor> professors = courseObj.getProfessors();
		EntityTransaction et = em.getTransaction();
		et.begin();
		if (professors != null) {
			 
			deleteProfessor(professors, em);
		}
		//em.remove(professors);
		em.remove(courseObj);
		et.commit();
		em.close();
		return courseObj;
	}
	// delete professor while course is updated
		private void removeProfFromCourse(Set<Professor> profs, EntityManager em) {
			for (Professor professor : profs) {
				TypedQuery<Course> tq = em.createNamedQuery("getCoursesByProf", Course.class);
				tq.setParameter("pId", professor.getPid());//last Modify
				List<Course> instructor = tq.getResultList();
				if (instructor.size() == 0 ) {
					em.remove(professor);
				}
			}
		}
	// delete professor while course is deleted
		private void deleteProfessor(Set<Professor> profs, EntityManager em) {
			for (Professor professor : profs) {
				TypedQuery<Course> tq = em.createNamedQuery("getCoursesByProf", Course.class);
				tq.setParameter("pId", professor.getPid());
				List<Course> instractor = tq.getResultList();
				if (instractor.size() == 1 ) {
					em.remove(professor);
				}
			}
		}


}
