package protocol_result;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAO_Sqlite {
	private final List<Worker> workers = new ArrayList<Worker>();
	private final List<Commission> commissions = new ArrayList<Commission>();

	public List<Worker> GetWorkers() throws Exception {
		// load the sqlite-JDBC driver using the current class loader
		Class.forName("org.sqlite.JDBC");

		Connection connection = null;
		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:data/MyData.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.

//			statement.executeUpdate("drop table if exists person");
//			statement.executeUpdate("create table person (id integer, name string)");
//			statement.executeUpdate("insert into person values(1, 'leo')");
//			statement.executeUpdate("insert into person values(2, 'yui')");
			ResultSet rs = statement.executeQuery("select * from workers");

			while (rs.next()) {
				// read the result set
//				System.out.println("fio = " + rs.getString("fio"));
//				System.out.println("post" + rs.getString("post"));
				Worker worker = new Worker(Integer.toString(rs.getInt("id_worker")),
						Integer.toString(rs.getInt("lisence_numb")), rs.getString("fio"), rs.getString("post"),
						rs.getString("subdivision"), rs.getString("personnel_number"));
//				worker.ID = Integer.toString(rs.getInt("id_worker"));
//				worker.lisence = Integer.toString(rs.getInt("lisence_numb"));
//				worker.fio = rs.getString("fio");
//				worker.post = rs.getString("post");
//				worker.subdivision = rs.getString("subdivision");
//				worker.personnelNumber = rs.getString("personnel_number");
				workers.add(worker);
			}

		} catch (SQLException e) {
			// if the error message is "out of memory",

			// it probably means no database file is found
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}

			} catch (SQLException e) {
				System.err.println(e);
			}
		}
		return workers;
	}

	public List<Commission> GetCommisons() throws Exception {
		Class.forName("org.sqlite.JDBC");

		Connection connection = null;
		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:data/MyData.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.

			ResultSet rs = statement.executeQuery("select * from commission");

			while (rs.next()) {

				Commission commission = new Commission(Integer.toString(rs.getInt("id_commission")),
						rs.getString("fio"), rs.getString("post"));
//				commission.id_commission = Integer.toString(rs.getInt("id_commission"));
//				commission.fio = rs.getString("fio");
//				commission.post = rs.getString("post");
				commissions.add(commission);
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.err.println(e);
			}
		}
		return commissions;
	}
}