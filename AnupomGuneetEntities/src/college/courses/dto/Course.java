package college.courses.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import college.courses.exceptions.ImproperInputException;

@Entity
@Table(name = "COURSE", schema = "COLLEGE")
@NamedQueries({ 
	@NamedQuery(name = "getAllCourses", query = "SELECT c FROM Course c") ,
	@NamedQuery(name = "getCoursesByProf", query="select c from Course c join c.professors p where p.pid=:pId"), 
	@NamedQuery(name = "getProfsByCourse", query="select p from Professor p join p.courses c where c.courseCode=:courseCode")
	})
public class Course implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "CCODE")
	private String courseCode;
	@Column(name = "CTITLE")
	private String courseTitle;
	@Column(name = "CAPACITY")
	private int capacity;
	@Column(name = "ENROLLED")
	private int enrolled;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="INSTRUCTOR", joinColumns= @JoinColumn(name="CCODE"),	inverseJoinColumns= @JoinColumn(name="PROFID"))	
	private Set<Professor> professors;

	// default constructor for future use and JavaBean Standard
	public Course() {
		super();
	}

	public Course(String courseCode) throws ImproperInputException {
		super();
		setCourseCode(courseCode);
	}

	public Course(String courseCode, String courseTitle) throws ImproperInputException {
		this(courseCode);
		setCourseTitle(courseTitle);
	}

	public String getCourseCode() {
		return courseCode;
	}

	private void setCourseCode(String courseCode) throws ImproperInputException {
		if (courseCode == null || courseCode.isEmpty()) {
			throw new ImproperInputException("Course must have a course code");
		}
		courseCode.toUpperCase();
		if (!courseCode.matches("[A-Z]{3,4} ?[0-9]{3,4}")) {
			throw new ImproperInputException("Course code must be 3 or 4 letters followed by 3 or 4 digits");
		}
		this.courseCode = courseCode;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) throws ImproperInputException {
		if (courseTitle != null && courseTitle.length() > 60) {
			throw new ImproperInputException("Course title can be hold 60 characters");
		}
		this.courseTitle = courseTitle;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) throws ImproperInputException {
		if (capacity == 0) {
			capacity = 30;
		}
		if (capacity < 5 || capacity > 250) {
			throw new ImproperInputException("Course capacity must be between 5 and 250");
		}
		this.capacity = capacity;
	}

	public int getEnrolled() {
		return enrolled;
	}

	public void setEnrolled(int enrolled) throws ImproperInputException {
		if (enrolled < 0 || enrolled > capacity) {
			throw new ImproperInputException("Enrollment must be between zero and course capacity");
		}
		this.enrolled = enrolled;
	}

	public Set<Professor> getProfessors() {
		return professors;
	}

	public void setProfessors(Set<Professor> professors) {
		this.professors = professors;
	}

	public void addProfessor(Professor professor) {
		Set<Professor> profs = getProfessors();
		if (profs == null) {
			profs = new HashSet<Professor>();
		}
		profs.add(professor);
		setProfessors(profs);
	}

	public void removeProfessor(Professor professor) throws ImproperInputException {
		Set<Professor> profs = getProfessors();
		for (Professor prof : profs) {
			if (prof == professor) {
				profs.remove(prof);
				setProfessors(profs);
				return;
			}
		}
		throw new ImproperInputException(professor + " does not teach " + getCourseCode());
	}

	public String toString() {
		String output = getCourseCode() + ": " + getCourseTitle() + " [enrolled/capacity= " + getEnrolled() + "/"
				+ getCapacity() + "] ";
		if (getProfessors() != null) {
			output += "Taught by ";
			boolean first = true;
			for (Professor p : getProfessors()) {
				if (first) {
					first = false;
				} else {
					output += ", ";
				}
				output += p.toString();
			}
		}
		return output;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + capacity;
		result = prime * result + ((courseCode == null) ? 0 : courseCode.hashCode());
		result = prime * result + ((courseTitle == null) ? 0 : courseTitle.hashCode());
		result = prime * result + enrolled;
		return result;
	}

// does not compare professors 
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (capacity != other.capacity)
			return false;
		if (courseCode == null) {
			if (other.courseCode != null)
				return false;
		} else if (!courseCode.equals(other.courseCode))
			return false;
		if (courseTitle == null) {
			if (other.courseTitle != null)
				return false;
		} else if (!courseTitle.equals(other.courseTitle))
			return false;
		if (enrolled != other.enrolled)
			return false;
		return true;
	}

}
