package protocol_result;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;

public class MsOfficeWorkerHelper {
	XWPFDocument doc = null;
	String fileName = null;
	FileInputStream fis = null;

	public MsOfficeWorkerHelper(String fileName) {
		super();
		this.fileName = fileName;
		connectToFile();
	}

	public void deleteFuckingAposeMessage() {
		connectToFile();

		XWPFParagraph p = doc.getParagraphs().get(0);
		int pPos = doc.getPosOfParagraph(p);
		doc.removeBodyElement(pPos);

		for (XWPFHeader header : doc.getHeaderList()) {
			header.setHeaderFooter(
					org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHdrFtr.Factory.newInstance());
		}
		for (XWPFFooter footer : doc.getFooterList()) {
			footer.setHeaderFooter(
					org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHdrFtr.Factory.newInstance());
		}
		
		saveFile();
	}

	public void connectToFile() {

		try {
			fis = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					doc = new XWPFDocument(fis);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void saveFile() {
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			doc.write(fos);
			fos.close();
			doc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void replaceWordInParagraphsAndTables(String replceTarget, String replceWord) {

		for (XWPFParagraph p : doc.getParagraphs()) {
			List<XWPFRun> runs = p.getRuns();
			if (runs != null) {
				for (XWPFRun r : runs) {

					String text = r.getText(0);

					if (text != null && text.contains(replceTarget)) {

						System.out.println("!!!!!!!!!!!replceTarget!!!!!!!!!!!! " + replceTarget);
						if (text != null && text.contains(replceTarget)) {
							text = text.replace(replceTarget, replceWord);
							r.setText(text, 0);
						}

//								if (replceWord.contains("\n")) {
//									System.out.println("hjkhjk");
//									String[] lines = replceWord.split("\n");
//									r.setText(lines[0], 0); // set first line into XWPFRun
//									for (int i = 1; i < lines.length; i++) {
//										// add break and insert new text
//										r.addBreak();
//										r.setText(lines[i]);
//										
//									}
//								} else {
//									text = text.replace(replceTarget, replceWord);
//									r.setText(text, 0);
//								}

					}
				}
			}
		}
		for (XWPFTable tbl : doc.getTables()) {
			for (XWPFTableRow row : tbl.getRows()) {
				for (XWPFTableCell cell : row.getTableCells()) {
					for (XWPFParagraph p : cell.getParagraphs()) {
						for (XWPFRun r : p.getRuns()) {
							String text = r.getText(0);
							if (text != null && text.contains(replceTarget)) {
								text = text.replace(replceTarget, replceWord);
								r.setText(text, 0);
							}
						}
					}
				}
			}
		}

	}

	public void addWorkers(ArrayList<Worker> protocolWorkers) {
		Iterator<Worker> iter = protocolWorkers.iterator();
		while (iter.hasNext()) {
			insertTableDataInRows(iter.next());
			if (iter.hasNext()) {
				insertTableBlankRows();
				refresh();

			}

		}

	}

	public void refresh() {
		saveFile();
		connectToFile();
	}

	public void insertTableBlankRows() {
		try {
			XWPFTable table = doc.getTableArray(0);

			XWPFTableRow oldRow = table.getRow(2);
			CTRow ctrow = CTRow.Factory.parse(oldRow.getCtRow().newInputStream());
			XWPFTableRow newRow = new XWPFTableRow(ctrow, table);
//			clearRow(newRow);
			table.addRow(newRow);

			oldRow = table.getRow(3);
			ctrow = CTRow.Factory.parse(oldRow.getCtRow().newInputStream());
			newRow = new XWPFTableRow(ctrow, table);
			table.addRow(newRow);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertTableDataInRows(Worker worker) {

		try {
			XWPFTable table = doc.getTableArray(0);

			int numberOfRows = table.getNumberOfRows();
			XWPFTableRow testRow = table.getRow(numberOfRows - 2);

			for (XWPFTableCell cell : testRow.getTableCells()) {
				for (XWPFParagraph p : cell.getParagraphs()) {
					for (XWPFRun r : p.getRuns()) {
						String text = r.getText(0);
						if (text != null) {

							r.setText("", 0);
						}
					}
				}
			}

			table.getRow(numberOfRows - 2).getCell(0).setText(Integer.toString(numberOfRows / 2 - 1));
			table.getRow(numberOfRows - 2).getCell(1).setText(worker.getFio());
			table.getRow(numberOfRows - 2).getCell(2).setText(worker.getPost());
			table.getRow(numberOfRows - 2).getCell(3).setText(worker.getSubdivision());
			table.getRow(numberOfRows - 2).getCell(4).setText("Сдал удост-ие " + worker.getLisence());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clearRow(XWPFTableRow BlankFIOrow) {
		for (XWPFTableCell cell : BlankFIOrow.getTableCells()) {
			for (XWPFParagraph paragraph : cell.getParagraphs()) {
				deleteParagraph(paragraph);
			}
		}
	}

	public static void deleteParagraph(XWPFParagraph p) {
		XWPFDocument doc = p.getDocument();
		int pPos = doc.getPosOfParagraph(p);
		;
		doc.removeBodyElement(pPos);
	}
}
