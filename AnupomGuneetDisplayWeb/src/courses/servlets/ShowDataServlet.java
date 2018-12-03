package courses.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import college.courses.client.CatalogClient;
import college.courses.dto.Course;
import college.courses.dto.Professor;
import college.courses.exceptions.ImproperInputException;

/**
 * Servlet implementation class ShowDataServlet
 */
@WebServlet("/showData")
public class ShowDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String showCourses = "courses.jsp";
	private final String showProfessors = "professors.jsp";
	private final String startOver = "index.jsp";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowDataServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nextPage = "startOver.jsp";
		CatalogClient client;
		try {
			String action = request.getParameter("submit");
			switch (action) {
			case "Courses":
				client = new CatalogClient();
				List<Course> courses = client.getAllCourses();
				request.setAttribute("courses", courses);
				nextPage = showCourses;
				break;
			case "Professors":
				client = new CatalogClient();
				List<Professor> professors = client.getAllProfessors();
				request.setAttribute("professors", professors);
				nextPage = showProfessors;
				break;
			default:
				throw new ImproperInputException("Operation cannot be performed, please try again.");
			}
		} catch (ImproperInputException e) {
			request.setAttribute("message", e.getMessage());
			nextPage = startOver;
			e.printStackTrace();
		} finally {
			request.getRequestDispatcher(nextPage).forward(request, response);
		}
		return;
	}

}
