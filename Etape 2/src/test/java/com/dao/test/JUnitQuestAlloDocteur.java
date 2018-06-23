package com.dao.test;

import java.io.File;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

import com.cours.allo.docteur.dao.MySqlSingleton;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.cours.allo.docteur.dao.entities.Adresse;
import com.cours.allo.docteur.dao.entities.Utilisateur;
import com.cours.allo.docteur.service.IServiceFacade;
import com.cours.allo.docteur.service.ServiceFacade;
import com.cours.allo.docteur.utils.Constants;
import com.cours.allo.docteur.utils.SQLScriptFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class JUnitQuestAlloDocteur {

	private static final Log log = LogFactory.getLog(JUnitQuestAlloDocteur.class);
	private static IServiceFacade serviceFacade = null;
	// Compter le nombre d'utilisateurs dans votre base de données.
	private static final int NB_UTILISATEURS_LIST = 11;
	private static final int NB_ADRESSES_LIST = 27;
	private static final int NB_UTILISATEURS_FIND_BY_PRENOM = 3;
	private static final int NB_UTILISATEURS_FIND_BY_NOM = 3;
	private static final int NB_UTILISATEURS_FIND_BY_CODE_POSTAL = 4;
	private static final String UTILISATEUR_FIND_BY_PRENOM = "Jerome";
	private static final String UTILISATEUR_FIND_BY_NOM = "Petit";
	private static final String UTILISATEUR_FIND_BY_CODE_POSTAL = "75000";

	private static final int NB_ADRESSES_FIND_BY_VILLE = 4;
	private static final int NB_ADRESSES_FIND_BY_CODE_POSTAL = 4;

	private static final String ADRESSES_FIND_BY_VILLE = "Paris";
	private static final String ADRESSES_FIND_BY_CODE_POSTAL = "75000";

	private static List<Utilisateur> utilisateurs = null;
	private static List<Adresse> adresses = null;

	@BeforeClass
	public static void init() throws Exception {
		// configuration de l'application
		log.debug("Entree de la methode");
		serviceFacade = new ServiceFacade();
		initDataBase();
		log.debug("Sortie de la methode");
	}

	public static void initDataBase() {
		SQLScriptFile sqlFile;
		String filePath;

		filePath = System.getProperty("user.dir");
		filePath += File.separator + Constants.SQL_JUNIT_PATH_FILE;
		Connection conn;

		try {
			conn = MySqlSingleton.getInstance().getDataSource().getConnection();
			sqlFile = new SQLScriptFile(conn, filePath);
			sqlFile.run();
		} catch (Exception e) {}
	}

	@Test
	public void testFindAllUtilisateurs() {
		log.debug("Entree de la methode");
		if (utilisateurs != null) {
			log.debug(
				"NB_UTILISATEURS_LIST: " + NB_UTILISATEURS_LIST + " , utilisateurs.size(): " +
				utilisateurs.size());
			Assert.assertEquals(NB_UTILISATEURS_LIST, utilisateurs.size());
			verifyDataUtilisateur(utilisateurs);
		}
		log.debug("Sortie de la methode");
	}

	// @Test
	public void verifyDataUtilisateur(List<Utilisateur> utilisateurs) {
		log.debug("Entree de la methode");
		if (utilisateurs != null) {
			log.debug("utilisateurs.size(): " + utilisateurs.size());
			for (Utilisateur user : utilisateurs) {
				Assert.assertNotNull(user.getAdresses());
				Assert.assertTrue(!user.getAdresses().isEmpty());
				for (Adresse adr : user.getAdresses()) {
					Assert.assertNotNull(adr.getIdAdresse());
					Assert.assertNotNull(adr.getRue());
					Assert.assertNotNull(adr.getCodePostal());
					Assert.assertNotNull(adr.getVille());
					Assert.assertNotNull(adr.getPays());
				}
			}
		} else if (utilisateurs == null || utilisateurs.isEmpty()) {
			Assert.fail("Aucun utilisateur n'a ete trouves dans votre liste");
		}
		log.debug("Sortie de la methode");
	}

	@Test
	public void testFindAllAdresses() {
		log.debug("Entree de la methode");
		if (adresses != null) {
			log.debug(
				"NB_ADRESSES_LIST: " + NB_ADRESSES_LIST + " , adresses.size(): " + adresses.size());
			Assert.assertEquals(NB_ADRESSES_LIST, adresses.size());
		} else if (adresses == null || adresses.isEmpty()) {
			Assert.fail("Aucun adresse n'a ete trouves dans votre base de données");
		}
		log.debug("Sortie de la methode");
	}

	@Test
	public void testFindByCriteria() {
		log.debug("Entree de la methode");
		List<Utilisateur> utilisateursByPrenom = serviceFacade.getUtilisateurDao()
												 .findUtilisateursByPrenom(
			UTILISATEUR_FIND_BY_PRENOM);
		List<Utilisateur> utilisateursByNom = serviceFacade.getUtilisateurDao()
											  .findUtilisateursByNom(UTILISATEUR_FIND_BY_NOM);
		List<Utilisateur> utilisateursByCodePostal = serviceFacade.getUtilisateurDao()
													 .findUtilisateursByCodePostal(
			UTILISATEUR_FIND_BY_CODE_POSTAL);

		List<Adresse> adressesByVille = serviceFacade.getAdresseDao().findAdressesByVille(
			ADRESSES_FIND_BY_VILLE);
		List<Adresse> adressesByCodePostal = serviceFacade.getAdresseDao()
											 .findAdressesByCodePostal(ADRESSES_FIND_BY_CODE_POSTAL);

		if (utilisateursByPrenom != null) {
			log.debug("NB_UTILISATEURS_FIND_BY_PRENOM: " + NB_UTILISATEURS_FIND_BY_PRENOM
					  + " , utilisateursByPrenom.size(): " + utilisateursByPrenom.size());
			Assert.assertEquals(NB_UTILISATEURS_FIND_BY_PRENOM, utilisateursByPrenom.size());
			verifyDataUtilisateur(utilisateursByPrenom);
		} else {
			Assert.fail("Aucun utilisateur avec le prenom '" + UTILISATEUR_FIND_BY_PRENOM
						+ "' n'a ete trouve dans votre base de données");
		}
		if (utilisateursByNom != null) {
			log.debug("NB_UTILISATEURS_FIND_BY_NOM: " + NB_UTILISATEURS_FIND_BY_NOM + " , utilisateursByNom.size(): "
					  + utilisateursByNom.size());
			Assert.assertEquals(NB_UTILISATEURS_FIND_BY_NOM, utilisateursByNom.size());
			verifyDataUtilisateur(utilisateursByNom);
		} else {
			Assert.fail("Aucun utilisateur avec le nom '" + UTILISATEUR_FIND_BY_NOM
						+ "' n'a ete trouve dans votre base de données");
		}
		if (utilisateursByCodePostal != null) {
			log.debug("NB_UTILISATEURS_FIND_BY_CODE_POSTAL: " + NB_UTILISATEURS_FIND_BY_CODE_POSTAL
					  + " , utilisateursByCodePostal.size(): " + utilisateursByCodePostal.size());
			Assert.assertEquals(NB_UTILISATEURS_FIND_BY_CODE_POSTAL,
								utilisateursByCodePostal.size());
			verifyDataUtilisateur(utilisateursByCodePostal);
		} else {
			Assert.fail("Aucun utilisateur avec le code postal '" + UTILISATEUR_FIND_BY_CODE_POSTAL
						+ "' n'a ete trouve dans votre base de données");
		}
		if (adressesByVille != null) {
			log.debug("NB_ADRESSES_FIND_BY_VILLE: " + NB_ADRESSES_FIND_BY_VILLE + " , adressesByVille.size(): "
					  + adressesByVille.size());
			Assert.assertEquals(NB_ADRESSES_FIND_BY_VILLE, adressesByVille.size());
		} else {
			Assert.fail("Aucune adresse avec la ville '" + ADRESSES_FIND_BY_VILLE
						+ "' n'a ete trouve dans votre base de données");
		}
		if (adressesByCodePostal != null) {
			log.debug("NB_ADRESSES_FIND_BY_CODE_POSTAL: " + NB_ADRESSES_FIND_BY_CODE_POSTAL
					  + " , adressesByCodePostal.size(): " + adressesByCodePostal.size());
			Assert.assertEquals(NB_ADRESSES_FIND_BY_CODE_POSTAL, adressesByCodePostal.size());
		} else {
			Assert.fail("Aucune adresse avec le code postal '" + ADRESSES_FIND_BY_CODE_POSTAL
						+ "' n'a ete trouve dans votre base de données");
		}
		log.debug("Sortie de la methode");
	}

	@Test
	public void testCreateUpdateDeleteUtilisateur() {
		log.debug("Entree de la methode");
		Utilisateur userCRUD = new Utilisateur("Mr",
											   "Jean",
											   "Ass",
											   "jean.ass@gmail.com",
											   "passw0rd",
											   new Date(System.currentTimeMillis()));
		userCRUD = serviceFacade.getUtilisateurDao().createUtilisateur(userCRUD);
		Assert.assertNotNull(userCRUD);
		Assert.assertNotNull(userCRUD.getIdUtilisateur());
		Assert.assertNotNull(userCRUD.getPrenom());
		Assert.assertNotNull(userCRUD.getNom());
		log.debug("Created userCRUD : " + userCRUD);
		userCRUD =
			serviceFacade.getUtilisateurDao().findUtilisateurById(userCRUD.getIdUtilisateur());
		Assert.assertNotNull(userCRUD);
		userCRUD.setPrenom("Jean Bis");
		userCRUD.setNom("Ass Bis");
		userCRUD = serviceFacade.getUtilisateurDao().updateUtilisateur(userCRUD);
		Assert.assertNotNull(userCRUD);
		userCRUD =
			serviceFacade.getUtilisateurDao().findUtilisateurById(userCRUD.getIdUtilisateur());
		log.debug("Updated userCRUD : " + userCRUD);
		Assert.assertEquals("Jean Bis", userCRUD.getPrenom());
		Assert.assertEquals("Ass Bis", userCRUD.getNom());
		Assert.assertTrue(serviceFacade.getUtilisateurDao().deleteUtilisateur(userCRUD));
		List<Utilisateur> utilisateursFinal =
			serviceFacade.getUtilisateurDao().findAllUtilisateurs();
		if (utilisateursFinal != null) {
			Assert.assertEquals(NB_UTILISATEURS_LIST, utilisateursFinal.size());
			log.debug(
				"utilisateursFinal.size() : " + utilisateursFinal.size() + " , NB_UTILISATEURS_LIST: "
				+ NB_UTILISATEURS_LIST);
		} else if (utilisateursFinal == null || utilisateursFinal.isEmpty()) {
			Assert.fail("Aucun utilisateur n'a ete trouve au final");
		}
		log.debug("Sortie de la methode");
	}

	@Test
	public void testCreateUpdateDeleteAdresse() {
		log.debug("Entree de la methode");
		Utilisateur userCRUD = new Utilisateur("Mr",
											   "Jean",
											   "Ass",
											   "jean.ass@gmail.com",
											   "passw0rd",
											   new Date(System.currentTimeMillis()));
		userCRUD = serviceFacade.getUtilisateurDao().createUtilisateur(userCRUD);
		Assert.assertNotNull(userCRUD);
		Assert.assertNotNull(userCRUD.getIdUtilisateur());
		Assert.assertNotNull(userCRUD.getPrenom());
		Assert.assertNotNull(userCRUD.getNom());
		Adresse adresseCRUD = new Adresse("5 rue de l'enfer",
										  "75000",
										  "Paris",
										  "France",
										  userCRUD.getIdUtilisateur());
		adresseCRUD = serviceFacade.getAdresseDao().createAdresse(adresseCRUD);
		Assert.assertNotNull(adresseCRUD);
		Assert.assertNotNull(adresseCRUD.getIdAdresse());
		Assert.assertNotNull(adresseCRUD.getRue());
		Assert.assertNotNull(adresseCRUD.getCodePostal());
		Assert.assertNotNull(adresseCRUD.getVille());
		Assert.assertNotNull(adresseCRUD.getPays());
		log.debug("Created adresseCRUD : " + adresseCRUD);
		adresseCRUD = serviceFacade.getAdresseDao().findAdresseById(adresseCRUD.getIdAdresse());
		Assert.assertNotNull(adresseCRUD);
		adresseCRUD.setCodePostal("75 000 Bis");
		adresseCRUD.setVille("Paris Bis");
		adresseCRUD = serviceFacade.getAdresseDao().updateAdresse(adresseCRUD);
		Assert.assertNotNull(adresseCRUD);
		adresseCRUD = serviceFacade.getAdresseDao().findAdresseById(adresseCRUD.getIdAdresse());
		log.debug("Updated adresseCRUD : " + adresseCRUD);
		Assert.assertEquals("75 000 Bis", adresseCRUD.getCodePostal());
		Assert.assertEquals("Paris Bis", adresseCRUD.getVille());
		Assert.assertTrue(serviceFacade.getAdresseDao().deleteAdresse(adresseCRUD));
		Assert.assertTrue(serviceFacade.getUtilisateurDao().deleteUtilisateur(userCRUD));
		List<Utilisateur> utilisateursFinal =
			serviceFacade.getUtilisateurDao().findAllUtilisateurs();
		if (utilisateursFinal != null) {
			Assert.assertEquals(NB_UTILISATEURS_LIST, utilisateursFinal.size());
			log.debug(
				"utilisateursFinal.size() : " + utilisateursFinal.size() + " , NB_UTILISATEURS_LIST: "
				+ NB_UTILISATEURS_LIST);
		} else if (utilisateursFinal == null || utilisateursFinal.isEmpty()) {
			Assert.fail("Aucun utilisateur n'a ete trouve au final");
		}
		List<Adresse> adressesFinal = serviceFacade.getAdresseDao().findAllAdresses();
		if (adressesFinal != null) {
			Assert.assertEquals(NB_ADRESSES_LIST, adressesFinal.size());
			log.debug(
				"adressesFinal.size() : " + adressesFinal.size() + " , NB_ADRESSES_LIST: " +
				NB_ADRESSES_LIST);
		} else if (adressesFinal == null || adressesFinal.isEmpty()) {
			Assert.fail("Aucune adresse n'a ete trouve au final");
		}
		log.debug("Sortie de la methode");
	}

	@AfterClass
	public static void terminate() throws Exception {
		String methodName = "terminate";
		log.debug("Entree de la methode");
		serviceFacade = null;
		utilisateurs = null;
		adresses = null;
		log.debug("Sortie de la methode");
	}

}