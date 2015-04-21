package net.zfp.web.startup;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

import net.zfp.util.SystemProperties;

import org.apache.log4j.Logger;


public class StartServer extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(StartServer.class.getName());
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public void init(ServletConfig config) {
		
		/*
		 * Thread thread1 = new Thread(new FileReader(), "thread1"); thread1.start();
		 */

		SystemProperties.SERVER_REAL_PATH = config.getServletContext().getRealPath("/");
		SystemProperties.SERVER_CONTEXT_NAME  = config.getServletContext().getContextPath();

		SystemProperties.initInstance(SystemProperties.SERVER_REAL_PATH + "WEB-INF/connection.properties");
		
		System.out.println("\n\n********** " + SystemProperties.SERVER_REAL_PATH + " **********");
		//System.out.println("1++++++++++ " + config.getServletContext().getContextPath() + " ++++++++++");
		//System.out.println("2++++++++++ " + config.getServletContext().get + " ++++++++++");
		//System.out.println("3++++++++++ Server: " + SystemProperties.getProp("webServerUrl") + " ++++++++++");
		//System.out.println("4++++++++++ Port: " + SystemProperties.getProp("webPort") + " ++++++++++");
		System.out.println("----------!!!---------- The Velo Web Services Server has started! ----------!!!----------\n\n");
		//LOG.info(" --- !!! --- Config file location: " + ReadProperties.SERVER_PATH + " --- !!! --- " );
		
		// CRON JOB
		try {
			/*
			 * SchedulerFactory sf=new StdSchedulerFactory(); Scheduler sched=sf.getScheduler(); 
			 * JobDetail jd=new JobDetail("job1","group1",GenerateAlerts.class); 
			 * CronTrigger ct=new CronTrigger("cronTrigger","group2","0 0/1 * * * ?");
			 * sched.scheduleJob(jd,ct); sched.start();
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	
	
}
