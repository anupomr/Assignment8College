package college.courses.service;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
public class CatalogConifg extends Application {
    
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(CatalogService.class);
		classes.add(CatalogUtils.class);
		classes.add(ResultCourseNotFound.class);
		classes.add(ResultDuplicateCourse.class);
		classes.add(ResultImproperInput.class);
		return classes;
	}
    
}
