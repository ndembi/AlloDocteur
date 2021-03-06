package com.cours.allo.docteur.dao.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Patient")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p"),
				@NamedQuery(name = "Patient.findById",
							query = "SELECT p FROM Patient p WHERE p.idPatient = :idPatient"),
				@NamedQuery(name = "Patient.findByNumeroSecuriteSociale",
							query =
								"SELECT p FROM Patient p WHERE p.numeroSecuriteSociale = :numeroSecuriteSociale"),
				@NamedQuery(name = "Patient.findByNumeroTelephone",
							query =
								"SELECT p FROM Patient p WHERE p.numeroTelephone = :numeroTelephone"),
				@NamedQuery(name = "Patient.findByPrenom",
							query =
								"SELECT p FROM Patient p INNER JOIN p.userPatient u WHERE u.prenom = :prenom"),
				@NamedQuery(name = "Patient.findByNom",
							query =
								"SELECT p FROM Patient p INNER JOIN p.userPatient u WHERE u.nom = :nom") })
public class Patient {
	/*
	 * @ManyToOne(cascade = CascadeType.MERGE)
	 *
	 * @JoinColumn(referencedColumnName = "idMedecin", name = "idMedecin") private
	 * Medecin doctor;
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idPatient")
	private Integer idPatient;

	@Column(name = "numeroSecuriteSociale")
	private String numeroSecuriteSociale;

	@Column(name = "numeroTelephone")
	private String numeroTelephone;

	@Column(name = "version")
	@Version
	private Integer version;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(referencedColumnName = "idUtilisateur", name = "idUtilisateur")
	private Utilisateur userPatient;

	@OneToMany(mappedBy = "patientRdv", cascade = CascadeType.MERGE, orphanRemoval = true,
			   fetch = FetchType.LAZY)
	private List<RendezVous> rdv = new ArrayList<>();

	/**
	 * @return the userPatient
	 */
	public Utilisateur getUserPatient() {
		return userPatient;
	}

	/**
	 * @return the numeroSecuriteSociale
	 */
	public String getNumeroSecuriteSociale() {
		return numeroSecuriteSociale;
	}

	/**
	 * @return the numeroTelephone
	 */
	public String getNumeroTelephone() {
		return numeroTelephone;
	}

	/**
	 * @param userPatient the userPatient to set
	 */
	public void setUserPatient(Utilisateur userPatient) {
		this.userPatient = userPatient;
	}

	/**
	 * @param numeroTelephone the numeroTelephone to set
	 */
	public void setNumeroTelephone(String numeroTelephone) {
		this.numeroTelephone = numeroTelephone;
	}

}