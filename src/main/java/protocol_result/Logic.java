package protocol_result;

import java.awt.Desktop;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

public class Logic {
	final private String wordPath = "data/word_templates/";
	final private String resultPath = "result/";
	public List<Worker> workers = new ArrayList<Worker>();
	public List<Commission> commissions = new ArrayList<Commission>();

	public Vector<Vector<String>> workerData = new Vector<Vector<String>>();
	public Vector<Vector<String>> commissionData = new Vector<Vector<String>>();

	public Vector<String> columnsCommissions = new Vector<>();
	public Vector<String> columnsWorkers = new Vector<>();

	Main_window main_window = null;

	public void go() {
		DAO_Sqlite dAO_Sqlite = new DAO_Sqlite();
		try {
			workers = dAO_Sqlite.GetWorkers();
			commissions = dAO_Sqlite.GetCommisons();
			createJTableworkerData();
			createJTablcommissionData();
//			 java.awt.EventQueue.invokeLater(new Runnable() {
//				public void run() {
//					try {
//						Main_window window = new Main_window(this);
//						window.frame.setVisible(true);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			});

		} catch (Exception e) {
			e.printStackTrace();
		}

		main_window = new Main_window(this);
	}

	public void createJTableworkerData() {
		columnsWorkers.addElement("id");
		columnsWorkers.addElement("Номер");
		columnsWorkers.addElement("ФИО");
		columnsWorkers.addElement("Должность");
		columnsWorkers.addElement("Подразделение");

		this.workers.stream().forEach(worker -> {
			Vector<String> vector = new Vector<>();
			vector.addElement(worker.getID());
			vector.addElement(worker.getLisence());
			vector.addElement(worker.getFio());
			vector.addElement(worker.getPost());
			vector.addElement(worker.getSubdivision());
			workerData.add(vector);
		});
	}

	public void createJTablcommissionData() {
		columnsCommissions.addElement("id");
		columnsCommissions.addElement("ФИО");
		columnsCommissions.addElement("Должность");

		this.commissions.stream().forEach(commissions -> {
			Vector<String> vector = new Vector<>();
			vector.addElement(commissions.getId_commission());
			vector.addElement(commissions.getFio());
			vector.addElement(commissions.getPost());
			commissionData.add(vector);
		});
	}

//	public void createMedicineProtocol ( DTO_Protocol dTO_Protocol) {
//		MsOfficeWorkerHelper msOfficeWorkerHelper = new MsOfficeWorkerHelper("tmpl_medicine.docx");
//		
//		msOfficeWorkerHelper.connectToFile();
//		msOfficeWorkerHelper.addWorkers(dTO_Protocol.getProtocolWorkers());
//		
//		String chairmanFIO_POST = dTO_Protocol.getChairman().getFio() +" "+ dTO_Protocol.getChairman().getPost();
//		msOfficeWorkerHelper.replaceWordInParagraphsAndTables("chairmanTOP", chairmanFIO_POST);
//		
////		msOfficeWorkerHelper.refresh();
//		
//		String chairmanFIO = dTO_Protocol.getChairman().getFio() ;
//		msOfficeWorkerHelper.replaceWordInParagraphsAndTables("chairmanFIO", chairmanFIO);
////		msOfficeWorkerHelper.refresh();
//		
//		String subcom1FIO_POST =dTO_Protocol.getProtocolSubCommission().get(0).getFio() +" "+ dTO_Protocol.getProtocolSubCommission().get(0).getPost();
//		String subcom2FIO_POST =dTO_Protocol.getProtocolSubCommission().get(1).getFio() +" "+ dTO_Protocol.getProtocolSubCommission().get(1).getPost();
//		
//		msOfficeWorkerHelper.replaceWordInParagraphsAndTables("subcom1TOP", subcom1FIO_POST);
////		msOfficeWorkerHelper.refresh();
//		msOfficeWorkerHelper.replaceWordInParagraphsAndTables("subcom2TOP", subcom2FIO_POST);
////		msOfficeWorkerHelper.refresh();
//		
//		String subcom1FIO =dTO_Protocol.getProtocolSubCommission().get(0).getFio() ;
//		String subcom2FIO =dTO_Protocol.getProtocolSubCommission().get(1).getFio() ;
//		
//		msOfficeWorkerHelper.replaceWordInParagraphsAndTables("subcom1FIO", subcom1FIO);
////		msOfficeWorkerHelper.refresh();
//		msOfficeWorkerHelper.replaceWordInParagraphsAndTables("subcom2FIO", subcom2FIO);
//		
//		
//		
//		msOfficeWorkerHelper.saveFile();
//	}

	public void createMedicineProtocol(DTO_Protocol dTO_Protocol, String fileNameFrom,String fileNameTo) {
		if (dTO_Protocol.isConsistenceOk() == false) {
			JOptionPane.showMessageDialog(null, "ошибка в данных", "InfoBox: " + "Ошибка",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		AsposeHelper asposeHelper = new AsposeHelper(wordPath + fileNameFrom, resultPath + fileNameTo);

		String chairmanFIO_POST = dTO_Protocol.getChairman().getFio() + " " + dTO_Protocol.getChairman().getPost();
		asposeHelper.replaceText("chairmanTOP", chairmanFIO_POST);

		String chairmanFIO = dTO_Protocol.getChairman().getFio();
		asposeHelper.replaceText("chairmanFIO", chairmanFIO);

		asposeHelper.addWorkers(dTO_Protocol.getProtocolWorkers());

		String subcom1FIO_POST = dTO_Protocol.getProtocolSubCommission().get(0).getFio() + " "
				+ dTO_Protocol.getProtocolSubCommission().get(0).getPost();
		String subcom2FIO_POST = dTO_Protocol.getProtocolSubCommission().get(1).getFio() + " "
				+ dTO_Protocol.getProtocolSubCommission().get(1).getPost();

		asposeHelper.replaceText("subcom1TOP", subcom1FIO_POST);
		asposeHelper.replaceText("subcom2TOP", subcom2FIO_POST);

		String subcom1FIO = dTO_Protocol.getProtocolSubCommission().get(0).getFio();
		String subcom2FIO = dTO_Protocol.getProtocolSubCommission().get(1).getFio();

		asposeHelper.replaceText("subcom1FIO", subcom1FIO);
		asposeHelper.replaceText("subcom2FIO", subcom2FIO);

		LocalDate localDate = LocalDate.now();

		String[] monthNames = { "января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября",
				"октября", "ноября", "декабря" };

		asposeHelper.replaceText("day", Integer.toString(localDate.getDayOfMonth()));
		asposeHelper.replaceText("monthLet", monthNames[localDate.getMonthValue() - 1]);
		asposeHelper.replaceText("year", Integer.toString(localDate.getYear()));

		MsOfficeWorkerHelper msOfficeWorkerHelper = new MsOfficeWorkerHelper(resultPath + fileNameTo);
		msOfficeWorkerHelper.deleteFuckingAposeMessage();
		
		JOptionPane.showMessageDialog(null, "Готово", "InfoBox: " + "Состояние",
				JOptionPane.INFORMATION_MESSAGE);
		
		try {
			Desktop.getDesktop().open(new File(resultPath + fileNameTo));	
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	public String getLastNameAndInitiials(String FIO) {

		String result = FIO;
		String[] elements = FIO.split(" ");

		if (elements.length > 2)
			result = elements[0] + elements[1].charAt(0) + elements[2].charAt(0);
		return result;
	}

}
