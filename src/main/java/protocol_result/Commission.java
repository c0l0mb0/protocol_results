package protocol_result;

public class Commission {
	String id_commission;
	String fio;
	String post;

	public Commission(String id_commission, String fio, String post) {
		super();
		this.id_commission = id_commission;
		this.fio = fio;
		this.post = post;
	}

	public String getId_commission() {
		return id_commission;
	}

	public String getFio() {
		return fio;
	}

	public String getPost() {
		return post;
	}

	public String toString() {

		return "id_commission " + id_commission + "fio " + fio + "post " + post;

	}

}
