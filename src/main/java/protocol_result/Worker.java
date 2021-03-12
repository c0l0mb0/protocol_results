package protocol_result;

public class Worker {

	String ID;
	String lisence;
	String fio;
	String post;
	String subdivision;
	String personnelNumber;


	public Worker(String iD, String lisence, String fio, String post, String subdivision, String personnelNumber) {
		super();
		this.ID = iD;
		this.lisence = lisence;
		this.fio = fio;
		this.post = post;
		this.subdivision = subdivision;
		this.personnelNumber = personnelNumber;
	}

	public String getID() {
		return ID;
	}

	public String getLisence() {
		return lisence;
	}

	public String getFio() {
		return fio;
	}

	public String getPost() {
		return post;
	}

	public String getSubdivision() {
		return subdivision;
	}

	public String getPersonnelNumber() {
		return personnelNumber;
	}

	public String toString() {

		return "ID " + ID + "lisence " + lisence + "fio " + fio + "post " + post + "subdivision " + subdivision + " "
				+ "personnelNumber " + personnelNumber;

	}

}
