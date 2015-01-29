package com.cp.jpa;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cp.sys.dao.ApplicationDAO;
import com.cp.sys.entity.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/config/applicationContext**.xml")
public class JpaTest {

	/* private static Logger logger = LoggerFactory.getLogger(JpaTest.class); */

	@PersistenceContext
	private EntityManager em;

	@Resource
	private ApplicationDAO applicationDAO;

	//@Test
	public void testCRUD() {
		Application app = applicationDAO.get("10001");
		System.out.println(app);
		/*
		 * Application application = new Application();
		 * application.setAppid("1001"); application.setText("app-1001");
		 * application.setHtml("http://127.0.0.1/"); application.setLeaf(false);
		 * application.setExpanded(true);
		 * 
		 * Application node1 = new Application(); node1.setAppid("10001");
		 * node1.setText("app-1001-1"); node1.setHtml("http://127.0.0.1/");
		 * node1.setLeaf(true); node1.setExpanded(true);
		 * 
		 * Application node2 = new Application(); node2.setAppid("10002");
		 * node2.setText("app-1001-2"); node2.setHtml("http://127.0.0.1/");
		 * node2.setLeaf(true); node2.setExpanded(true);
		 * 
		 * application.getChildren().add(node1);
		 * application.getChildren().add(node1);
		 * 
		 * em.persist(application);
		 */

		/*
		 * Application application = entityDAO.findById("1001");
		 * logger.debug(application.toString());
		 */
	}

	//@Test
	public void testUpdate() {
		Application app = applicationDAO.get("10001");
		app.setHtml("http://localhost:8080/cphoto/static/user.html");
		applicationDAO.update(app);
	}

	//@Test
	public void testSave() {
		//Application root = applicationDAO.get("1001");
		//em.getTransaction().begin();
		Application application = new Application();
		application.setAppid("10003");
		application.setText("权限管理");
		application.setHtml("http://127.0.0.1/");
		application.setLeaf(false);
		application.setExpanded(true);
		application.setParent(null);
		applicationDAO.save(application);
		//em.persist(application);
		//em.getTransaction().commit();
	}
}
