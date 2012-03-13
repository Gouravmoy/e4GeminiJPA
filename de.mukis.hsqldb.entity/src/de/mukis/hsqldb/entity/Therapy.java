package de.mukis.hsqldb.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({ @NamedQuery(name = "Therapy.findAll", query = "SELECT t FROM Therapy t") })
public class Therapy {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String caption;

	@Temporal(TemporalType.DATE)
	private Date therapyStart;

	@Temporal(TemporalType.DATE)
	private Date therapyEnd;

	private String comment;

	private int success;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Patient patient;

	@OneToMany
	private List<TherapyResult>	therapyResults;
	
	protected Therapy() {
	}

	public Therapy(Patient patient) {
		this.patient = patient;
	}
	
//	@PreRemove
//	protected void preRemove() {
//		//Cascading leads to concurrent modification
//		therapyResults.clear();
//	}

	public void updateSuccess() {
		if (!therapyResults.isEmpty()) {
			int sum = 0;
			for (TherapyResult therapyResult : therapyResults) {
				sum += therapyResult.getSuccess();
			}
			sum /= therapyResults.size();
			setSuccess(sum);
		} else {
			setSuccess(0);
		}
	}

	public long getId() {
		return id;
	}

	protected void setId(long id) {
		this.id = id;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public Date getTherapyStart() {
		return therapyStart;
	}

	public void setTherapyStart(Date therapyStart) {
		this.therapyStart = therapyStart;
	}

	public Date getTherapyEnd() {
		return therapyEnd;
	}

	public void setTherapyEnd(Date therapyEnd) {
		this.therapyEnd = therapyEnd;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public List<TherapyResult> getTherapyResults() {
		return therapyResults;
	}
	
	protected void setTherapyResults(List<TherapyResult> therapyResults) {
		this.therapyResults = therapyResults;
	}
	
	public boolean addTherapyResult(TherapyResult therapyResult) {
		if(therapyResults.contains(therapyResult))
			return false;
		boolean success = therapyResults.add(therapyResult);
		if(!this.equals(therapyResult.getTherapy()))
			therapyResult.setTherapy(this);
		return success;
	}
	
	public void removeTherapyResult(TherapyResult therapyResult) {
		therapyResults.remove(therapyResult);
	}

	@Override
	public String toString() {
		return "Therapy [id=" + id + ", patient=" + patient + ", therapyResults=" + therapyResults + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Therapy other = (Therapy) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}