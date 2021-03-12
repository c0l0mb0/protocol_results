package protocol_result;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.FileOutputStream;

import com.aspose.words.CellCollection;
import com.aspose.words.Document;
import com.aspose.words.FindReplaceDirection;
import com.aspose.words.FindReplaceOptions;
import com.aspose.words.NodeType;
import com.aspose.words.ParagraphCollection;
import com.aspose.words.Row;
import com.aspose.words.Run;
import com.aspose.words.RunCollection;
import com.aspose.words.Table;
import com.aspose.words.TableCollection;

public class AsposeHelper {
	Document doc = null;
	String wordDocPath = null;
	String resultPath = null;

	public AsposeHelper(String wordDocPath, String resultPath) {
		try {
			this.wordDocPath = wordDocPath;
			this.resultPath = resultPath;
			coppyFile();			
			doc = new Document(resultPath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void coppyFile () throws IOException {
		
	    File from = new File(wordDocPath);
	    File to = new File(resultPath);
	    to.createNewFile();
	    try (
	   
	      InputStream in = new BufferedInputStream(
	        new FileInputStream(from));
	      OutputStream out = new BufferedOutputStream(  new FileOutputStream(to))) {
	 
	        byte[] buffer = new byte[1024];
	        int lengthRead;
	        while ((lengthRead = in.read(buffer)) > 0) {
	            out.write(buffer, 0, lengthRead);
	            out.flush();
	        }
	    }
	}

	public void replaceText(String pattern, String replacement) {
		// Find and replace text in the document
		try {
			doc.getRange().replace(pattern, replacement, new FindReplaceOptions(FindReplaceDirection.FORWARD));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		saveFile();

	}

	public void addWorkers(ArrayList<Worker> protocolWorkers) {
		Iterator<Worker> iter = protocolWorkers.iterator();

		while (iter.hasNext()) {
			insertTableDataInRows(iter.next());
			if (iter.hasNext()) {
				insertTableBlankRows();
			}
		}
		saveFile();
		System.out.println("saveFile() ");
	}

	public void insertTableDataInRows(Worker worker) {
		System.out.println("insertTableDataInRows ");
		TableCollection tables = doc.getFirstSection().getBody().getTables();
		Table table = tables.get(0);
		int numberOfRows = table.getRows().getCount();

		try {
			table.getRows().get(numberOfRows - 2).getCells().get(0).getParagraphs().get(0).getRuns()
					.add(new Run(doc, Integer.toString(numberOfRows / 2 - 1)));
			table.getRows().get(numberOfRows - 2).getCells().get(1).getParagraphs().get(0).getRuns()
					.add(new Run(doc, worker.getFio()));
			table.getRows().get(numberOfRows - 2).getCells().get(2).getParagraphs().get(0).getRuns()
					.add(new Run(doc, worker.getPost()));
			table.getRows().get(numberOfRows - 2).getCells().get(3).getParagraphs().get(0).getRuns()
					.add(new Run(doc, worker.getSubdivision()));
			table.getRows().get(numberOfRows - 2).getCells().get(4).getParagraphs().get(0).getRuns()
					.add(new Run(doc, "Сдал удост-ие " + worker.getLisence()));

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void insertTableBlankRows() {
		System.out.println("insertTableBlankRows ");

		Table table = (Table) doc.getChild(NodeType.TABLE, 0, true);

		Row penultimateRow = (Row) table.getRows().get(2).deepClone(true);

		CellCollection cells = penultimateRow.getCells();
		for (int j = 0; j < cells.getCount(); j++) {
			ParagraphCollection Paragraphs = cells.get(j).getParagraphs();
			for (int i = 0; i < Paragraphs.getCount(); i++) {
				RunCollection runs = Paragraphs.get(i).getRuns();
				for (int k = 0; k < Paragraphs.getCount(); k++) {

					if (runs.get(k) != null) {
						runs.get(k).setText("");
					}
				}
			}
		}

		Row lastRow = (Row) table.getRows().get(3).deepClone(true);

		table.appendChild(penultimateRow);
		table.appendChild(lastRow);

	}

	public void saveFile() {
		try {
			doc.save(resultPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
