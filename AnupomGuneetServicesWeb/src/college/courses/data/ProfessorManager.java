package college.courses.data;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import college.courses.dto.Professor;

public class ProfessorManager {

	public ProfessorManager() {
		super();
	}
	
	public Collection<Professor> getAllProfessors() {
		EntityManagerFactory emf = EMFSupplier.getInstance().getEMF();
		EntityManager em = emf.createEntityManager();
		TypedQuery<Professor> tq = em.createNamedQuery( "getAllProfessors", Professor.class );
		return tq.getResultList();
	}

}
