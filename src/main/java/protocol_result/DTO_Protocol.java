package protocol_result;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class DTO_Protocol {
	private ArrayList<Worker> protocolWorkers = new ArrayList<>();
	private ArrayList<Commission> protocolSubCommission = new ArrayList<>();
	private Commission chairman = null;
	private String result = "";

	public ArrayList<Worker> getProtocolWorkers() {
		return protocolWorkers;
	}

	public ArrayList<Commission> getProtocolSubCommission() {
		return protocolSubCommission;
	}

	public Commission getChairman() {
		return chairman;
	}

	public DTO_Protocol(ArrayList<Worker> protocolWorkers, ArrayList<Commission> protocolSubCommission,
			Commission chairman) {
		super();
		this.protocolWorkers = protocolWorkers;
		this.protocolSubCommission = protocolSubCommission;
		this.chairman = chairman;
	}

	public String toString() {
		protocolWorkers.forEach(n -> this.result = this.result + n.toString());
		protocolSubCommission.forEach(n -> this.result = this.result + n.toString());
		this.result = this.result + chairman.toString();
		return this.result;

	}

	public boolean isConsistenceOk() {
		try {
			if (checkNull(this) == true) {
				return false;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//
//		protocolWorkers.forEach(worker -> {
//			try {
//				checkNull(worker);
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		});
//		
//		protocolSubCommission.forEach(worker -> {
//			try {
//				checkNull(worker);
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		});
		
		if (protocolSubCommission.size() <2) return false;
		
		return true;
	}

	public boolean checkNull(Object testedObject) throws IllegalAccessException {
		for (Field f : testedObject.getClass().getDeclaredFields())
			if (f.get(this) != null)
				return false;
		return true;
	}

}
