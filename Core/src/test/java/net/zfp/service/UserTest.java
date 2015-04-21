package net.zfp.service;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import net.zfp.dao.EntityDao;
import net.zfp.service.UserService;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration(locations = { "classpath:app-config.xml" })
public class UserTest {

	@Resource
	private static UserService	userService;

	/**
	 * @param args
	 * @throws ParseException
	 * @throws IOException
	 */
	public static void main(String[] args) throws ParseException, IOException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String todayDate = format.format(new Date());

		System.out.println(format.parse(todayDate));
		// System.out.println("c.get(Calendar.hour):"+c.get(Calendar.HOUR));

		Calendar calCurrent = new GregorianCalendar(TimeZone.getTimeZone("America/Montreal"));
		// Calendar calCurrent = Calendar.getInstance();
		/*
		 * Date endTime = calCurrent.getTime(); calCurrent.add(Calendar.MINUTE, -15); Date startTime = calCurrent.getTime(); System.out.println(endTime);
		 * System.out.println(startTime); /*Calendar cal = Calendar.
		 * 
		 * Date startTime = cal.getTime(); System.out.println(startTime);
		 */
		String temp = "Thu Mar 17 17:46:15 2011";

		BeanFactory jpaBeanFactory = new XmlBeanFactory(new ClassPathResource("app-config.xml"));

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("zerofootprint");
		Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("Asia/Taipei"));

		System.out.println(cal.get(Calendar.HOUR_OF_DAY));
		/*
		 * List<Long> idList= new ArrayList<Long>(); idList.add(new Long(1));
		 * 
		 */

		/** A thread per session */
		// Session currentSession= emf.
		/*
		 * Student student = studentDao.findById(1); Collection<Course> cs = student.getCourses(); System.out.println("size"+cs .size());
		 * 
		 * Set<Course> courses = new HashSet<Course>();
		 * 
		 * courses.add(new Course("Maths"));
		 * 
		 * courses.add(new Course("Computer Science")); Student student1 = new Student("Joe", courses);
		 * 
		 * EntityManager mngr =emf.createEntityManager();
		 * 
		 * studentDao.flush();
		 */

	}

}
