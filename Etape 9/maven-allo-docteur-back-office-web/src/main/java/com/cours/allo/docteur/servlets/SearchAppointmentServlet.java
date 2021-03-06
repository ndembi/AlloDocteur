package com.cours.allo.docteur.servlets;

import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cours.allo.docteur.dao.IRendezVousDao;
import com.cours.allo.docteur.dao.entities.RendezVous;
import com.cours.allo.docteur.service.IServiceFacade;
import com.cours.allo.docteur.utils.Utils;

import com.cours.allo.docteur.utils.security.TokenAuthUserList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

/**
 * SearchAppointmentServlet
 */
public class SearchAppointmentServlet extends HttpServlet {
	private static final Log log = LogFactory.getLog(SearchAppointmentServlet.class);

	IServiceFacade serviceFacade;
	ApplicationContext ctx;

	@Override
	public void init() throws ServletException {
		ctx = Utils.initContext(this);
		serviceFacade = Utils.getBeanServiceFacade(ctx, "serviceFacade");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
	IOException {
		Date date;
		Date test;
		SimpleDateFormat sdf;
		IRendezVousDao rDao;
		List<RendezVous> rdvlist;
		TokenAuthUserList tokenAuthUserList = TokenAuthUserList.getInstance();

		System.out.println("test");

		if(req.getParameter("rdvDate") == null){
			req.getRequestDispatcher("./recherche-rendez-vous.jsp").forward(req, resp);
		}

		try {
			sdf = new SimpleDateFormat("dd/MM/yyyy");

			date = sdf.parse((String) req.getParameter("rdvDate"));

			rDao = serviceFacade.getRendezVousDao();

			sdf = new SimpleDateFormat("yyyy - MM - dd");

			rdvlist = rDao.findRendezVousByJourAndIdMedecin(tokenAuthUserList.getUserId(req), date);



			/*for (int i = 0; i < rdvlist.size(); i++){
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				test = sdf.parse(sdf.format(rdvlist.get(i).getJour()));
				System.out.println(test);
				rdvlist.get(i).setJour(test);
			}*/

			req.setAttribute("rdvlist", rdvlist);
			req.getRequestDispatcher("./recherche-rendez-vous.jsp").forward(req, resp);
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {}
	}

}