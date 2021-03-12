package protocol_result;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.ScrollPaneConstants;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;

public class Main_window {

	private JFrame frame;
	private Logic logic;
	private JTable tableWorkers;
	private JPanel panelCommission;
	private JTable tableCommisson;
	private JPanel panel;
	private JTable tableProtocol;

	ArrayList<Worker> protocolWorkers = new ArrayList<>();
	ArrayList<Commission> protocolSubCommission = new ArrayList<>();
	Commission chairman = null;

	public DTO_Protocol protocolDTO = null;

	DefaultTableModel dtm = new DefaultTableModel(0, 0);

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Main_window window = new Main_window();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public Main_window(Logic logic) {

		this.logic = logic;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("\u041F\u0440\u043E\u0442\u043E\u043A\u043E\u043B\u044B");
		frame.setBounds(100, 100, 1082, 885);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panelWorkers = new JPanel();
		panelWorkers.setBorder(null);
		panelWorkers.setBounds(30, 26, 481, 437);
		frame.getContentPane().add(panelWorkers);

		tableWorkers = new JTable(logic.workerData, logic.columnsWorkers);
		panelWorkers.add(tableWorkers);
		JScrollPane scrollWorkers = new JScrollPane(tableWorkers);
		scrollWorkers.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollWorkers.setVisible(true);
		panelWorkers.add(scrollWorkers);
		tableWorkers.setSelectionModel(new ForcedListSelectionModel());
		tableWorkers.removeColumn(tableWorkers.getColumnModel().getColumn(0));

		panelCommission = new JPanel();
		panelCommission.setBorder(null);
		FlowLayout flowLayout = (FlowLayout) panelCommission.getLayout();
		panelCommission.setBackground(SystemColor.menu);
		panelCommission.setBounds(562, 26, 473, 437);
		frame.getContentPane().add(panelCommission);

		tableCommisson = new JTable(logic.commissionData, logic.columnsCommissions);
		panelCommission.add(tableCommisson);
		JScrollPane scrollCommission = new JScrollPane(tableCommisson);
		panelCommission.add(scrollCommission);

		tableCommisson.removeColumn(tableCommisson.getColumnModel().getColumn(0));

		panel = new JPanel();
		panel.setBounds(25, 473, 486, 31);
		frame.getContentPane().add(panel);

		JButton btnNewButton = new JButton("Работник");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String selectedWorkerFIO = null;

				int[] selectedRow = tableWorkers.getSelectedRows();
				selectedWorkerFIO = (String) tableWorkers.getValueAt(selectedRow[0], 1);

				dtm.addRow(new Object[] { selectedWorkerFIO, "тестируемый" });

				Worker worker = new Worker((String) tableWorkers.getModel().getValueAt(selectedRow[0], 0),
						(String) tableWorkers.getModel().getValueAt(selectedRow[0], 1),
						(String) tableWorkers.getModel().getValueAt(selectedRow[0], 2),
						(String) tableWorkers.getModel().getValueAt(selectedRow[0], 3),
						(String) tableWorkers.getModel().getValueAt(selectedRow[0], 4), "");

				protocolWorkers.add(worker);
			}
		});
		btnNewButton.setIcon(
				new ImageIcon(Main_window.class.getResource("/javax/swing/plaf/metal/icons/ocean/minimize.gif")));
		panel.add(btnNewButton);

		JLabel lblNewLabel = new JLabel("Работники");
		lblNewLabel.setBounds(50, 10, 110, 13);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Комиссия");
		lblNewLabel_1.setBounds(583, 10, 110, 13);
		frame.getContentPane().add(lblNewLabel_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(572, 473, 463, 31);
		frame.getContentPane().add(panel_2);

		JButton btnNewButton_2 = new JButton("Председатель");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int[] selectedRow = tableCommisson.getSelectedRows();
				String selectedWorkerFIO = (String) tableCommisson.getValueAt(selectedRow[0], 0);
				dtm.addRow(new Object[] { selectedWorkerFIO, "Председатель" });

				chairman = new Commission(
						(String) tableCommisson.getModel().getValueAt(tableCommisson.getSelectedRow(), 0),
						(String) tableCommisson.getModel().getValueAt(selectedRow[0], 1),
						(String) tableCommisson.getModel().getValueAt(selectedRow[0], 2));

			}
		});
		btnNewButton_2.setIcon(
				new ImageIcon(Main_window.class.getResource("/javax/swing/plaf/metal/icons/ocean/minimize.gif")));
		panel_2.add(btnNewButton_2);

		JButton btnNewButton_2_1 = new JButton("Член комиссии");
		btnNewButton_2_1.setIcon(
				new ImageIcon(Main_window.class.getResource("/javax/swing/plaf/metal/icons/ocean/minimize.gif")));
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int[] selectedRow = tableCommisson.getSelectedRows();
				String selectedWorkerFIO = (String) tableCommisson.getValueAt(selectedRow[0], 0);
				dtm.addRow(new Object[] { selectedWorkerFIO, "Подкомиссия" });

				Commission commission = new Commission((String) tableCommisson.getModel().getValueAt(selectedRow[0], 0),
						(String) tableCommisson.getModel().getValueAt(selectedRow[0], 1),
						(String) tableCommisson.getModel().getValueAt(selectedRow[0], 2));
				protocolSubCommission.add(commission);

			}
		});
		panel_2.add(btnNewButton_2_1);

		JPanel panelProtocol = new JPanel();
		panelProtocol.setBounds(30, 576, 481, 251);
		frame.getContentPane().add(panelProtocol);

		;
		String[] tableProtocolHeader = new String[] { "ФИО", "Роль" };
		dtm.setColumnIdentifiers(tableProtocolHeader);

		tableProtocol = new JTable();
		tableProtocol.setModel(dtm);
		panelProtocol.add(tableProtocol);
		JScrollPane scrollProtocol = new JScrollPane(tableProtocol);
		panelProtocol.add(scrollProtocol);

		scrollProtocol.setPreferredSize(new Dimension(470, 200));

		JPanel panel_2_1 = new JPanel();
		panel_2_1.setBounds(572, 579, 201, 196);
		frame.getContentPane().add(panel_2_1);
		panel_2_1.setLayout(null);

		JButton btnNewButton_2_2 = new JButton("ТБ");
		btnNewButton_2_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				protocolDTO = new DTO_Protocol(protocolWorkers, protocolSubCommission, chairman);

				logic.createMedicineProtocol(protocolDTO, "tmpl_tb.docx", "ТБПротокол.docx");

			}
		});
		btnNewButton_2_2.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_2_2.setIcon(new ImageIcon(
				Main_window.class.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Left-Black.png")));
		btnNewButton_2_2.setBounds(0, 0, 128, 28);
		btnNewButton_2_2.setVerticalAlignment(SwingConstants.TOP);
		panel_2_1.add(btnNewButton_2_2);

		JButton btnNewButton_2_2_1 = new JButton("Медицина");
		btnNewButton_2_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				protocolDTO = new DTO_Protocol(protocolWorkers, protocolSubCommission, chairman);

				logic.createMedicineProtocol(protocolDTO, "tmpl_medicine.docx", "МедПротокол.docx");
			}
		});
		btnNewButton_2_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_2_2_1.setIcon(new ImageIcon(Main_window.class
				.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Center-Black.png")));
		btnNewButton_2_2_1.setBounds(0, 45, 128, 28);
		panel_2_1.add(btnNewButton_2_2_1);

		JButton btnNewButton_2_2_1_1 = new JButton("Выоста ");
		btnNewButton_2_2_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				protocolDTO = new DTO_Protocol(protocolWorkers, protocolSubCommission, chairman);

				logic.createMedicineProtocol(protocolDTO, "tmpl_tb.docx", "ТБПротокол.docx");

			}
		});
		btnNewButton_2_2_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_2_2_1_1.setIcon(new ImageIcon(
				Main_window.class.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Left-Black.png")));
		btnNewButton_2_2_1_1.setVerticalAlignment(SwingConstants.TOP);
		btnNewButton_2_2_1_1.setBounds(0, 90, 128, 28);
		panel_2_1.add(btnNewButton_2_2_1_1);

		JButton btnNewButton_2_2_1_1_1 = new JButton("Очитить");
		btnNewButton_2_2_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				protocolWorkers = new ArrayList<>();
				protocolSubCommission = new ArrayList<>();
				chairman = null;
				
				if (dtm.getRowCount() > 0) {
				    for (int i = dtm.getRowCount() - 1; i > -1; i--) {
				    	dtm.removeRow(i);
				    }
				}
				
			}
		});
		btnNewButton_2_2_1_1_1.setIcon(
				new ImageIcon(Main_window.class.getResource("/javax/swing/plaf/metal/icons/ocean/paletteClose.gif")));
		btnNewButton_2_2_1_1_1.setVerticalAlignment(SwingConstants.TOP);
		btnNewButton_2_2_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_2_2_1_1_1.setBounds(0, 158, 128, 28);
		panel_2_1.add(btnNewButton_2_2_1_1_1);

		frame.setVisible(true);

	}
}

class ForcedListSelectionModel extends DefaultListSelectionModel {

	public ForcedListSelectionModel() {
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	@Override
	public void clearSelection() {
	}

	@Override
	public void removeSelectionInterval(int index0, int index1) {
	}

}
