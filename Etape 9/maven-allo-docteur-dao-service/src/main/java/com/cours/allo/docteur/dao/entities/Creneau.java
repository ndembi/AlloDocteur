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

/**
 * Creneau
 */
@Entity
@Table(name = "Creneau")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Creneau.findAll", query = "SELECT c FROM Creneau c"),
		@NamedQuery(name = "Creneau.findByHeureDebut", query = "SELECT c FROM Creneau c WHERE c.heureDebut = :heureDebut"),
		@NamedQuery(name = "Creneau.findByMinuteDebut", query = "SELECT c FROM Creneau c WHERE c.minuteDebut = :minuteDebut"),
		@NamedQuery(name = "Creneau.findByHeureFin", query = "SELECT c FROM Creneau c WHERE c.heureFin = :heureFin"),
		@NamedQuery(name = "Creneau.findByMinuteFin", query = "SELECT c FROM Creneau c WHERE c.minuteFin = :minuteFin") })
public class Creneau {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCreneau")
	private Integer idCreneau;

	@Column(name = "heureDebut")
	private Integer heureDebut;

	@Column(name = "heureFin")
	private Integer heureFin;

	@Column(name = "minuteDebut")
	private Integer minuteDebut;

	@Column(name = "minuteFin")
	private Integer minuteFin;

	@Version
	@Column(name = "version")
	private Integer version;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(referencedColumnName = "idMedecin", name = "idMedecin")
	private Medecin doctorCreneau;

	@OneToMany(mappedBy = "creneauRdv", cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<RendezVous> rdv = new ArrayList<>();

	public Integer getHeureDebut() {
		return heureDebut;
	}

	public void setHeureDebut(Integer heureDebut) {
		this.heureDebut = heureDebut;
	}

	public Integer getHeureFin() {
		return heureFin;
	}

	public void setHeureFin(Integer heureFin) {
		this.heureFin = heureFin;
	}

	public Integer getMinuteDebut() {
		return minuteDebut;
	}

	public void setMinuteDebut(Integer minuteDebut) {
		this.minuteDebut = minuteDebut;
	}

	public Integer getMinuteFin() {
		return minuteFin;
	}

	public void setMinuteFin(Integer minuteFin) {
		this.minuteFin = minuteFin;
	}

}