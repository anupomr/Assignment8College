package college.courses.dto;

import java.io.Serializable;
import java.util.Set;

import college.courses.exceptions.ImproperInputException;

// This class is Serializable for future use as data transfer bean
public class Professor implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int pid;
	private String firstName = null;
	private String middleName = null;
	private String lastName = null;
	private Set<Course> courses;
	
	// default constructor for future use and JavaBean Standard
	public Professor() {
		super();
	}
	
	public Professor( String firstName, String lastName)
			throws ImproperInputException {
		setFirstName(firstName);
		setMiddleName("");
		setLastName(lastName);
	}
	
	public Professor( String firstName, String middleName, String lastName)
			throws ImproperInputException {
		setFirstName(firstName);
		setMiddleName(middleName);
		setLastName(lastName);
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) throws ImproperInputException {
		if (firstName == null || firstName.isEmpty()) {
			throw new ImproperInputException("Proressor must have a first name");
		}
		this.firstName = firstName;
	}

	public String getMiddleName() {
		// middle name is optional, but null not accepted by JSON
		if ( middleName == null ) {
			return "";
		}
		return middleName;
	}

	public void setMiddleName(String middleName) throws ImproperInputException {
		// Modified because JSON had trouble with missing fields
		if ( middleName == null ) {
			middleName = "";
		}
		this.middleName = middleName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) throws ImproperInputException {
		if (lastName == null || lastName.isEmpty()) {
			throw new ImproperInputException("Proressor must have a last name");
		}
		this.lastName = lastName;
	}
	
	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	public String toString() {
		String name = firstName + " ";
		if ( middleName != null && middleName.length() > 0 ) { 
			name += middleName + " ";
		}
		name += lastName;	
		String output = pid + ": " + name;
		if ( courses != null && courses.size() > 0) {
			output += " Teaches [ ";
			boolean first = true;
			for ( Course course : courses) {
				if ( ! first )  {
					output += ", " ;
				}
				output += course.getCourseCode();
				first = false;
			}
			output += " ]";
		}
		return output;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((courses == null) ? 0 : courses.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((middleName == null) ? 0 : middleName.hashCode());
		result = prime * result + pid;
		return result;
	}

	// does not compare primary keys because client may not know PK values
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Professor other = (Professor) obj;
		if (courses == null) {
			if (other.courses != null)
				return false;
		} else if (!courses.equals(other.courses))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (middleName == null) {
			if (other.middleName != null)
				return false;
		} else if (!middleName.equals(other.middleName))
			return false;
		return true;
	}
	


}
