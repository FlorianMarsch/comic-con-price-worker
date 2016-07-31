package de.florianmarsch;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.EntityManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigJob implements Job {


	final static Logger logger = LoggerFactory.getLogger(ConfigJob.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		String content = loadFile("http://comic-con-price-api.herokuapp.com/api/comiccon/2017/");
		logger.info(content);
		
		try {
			JSONObject obj = new JSONObject(content).getJSONObject("data");
			PricePoint pp = new PricePoint();
			pp.setSaturday(obj.getInt("saturday"));
			pp.setSunday(obj.getInt("sunday"));
			pp.setVip(obj.getInt("vip"));
			pp.setWeekend(obj.getInt("weekend"));
			
			EntityManager em= new EmFactory().produceEntityManager();
			em.getTransaction().begin();
			em.persist(pp);
			em.getTransaction().commit();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	
	private String loadFile(String url) {

		StringBuffer tempReturn = new StringBuffer();
		try {
			URL u = new URL(url);
			InputStream is = u.openStream();
			DataInputStream dis = new DataInputStream(new BufferedInputStream(is));
			String s;

			while ((s = dis.readLine()) != null) {
				tempReturn.append(s);
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tempReturn.toString();
	}

}
