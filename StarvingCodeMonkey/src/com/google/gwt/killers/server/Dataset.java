package com.google.gwt.killers.server;

/**
 * dataset object that holds all the data
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.Serializable;
import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.api.users.User;
import java.util.Calendar;
import java.text.SimpleDateFormat;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Dataset implements Iterable<Object[]> {
	public enum ColumnType {
		STRING, NUMBER
	}

	public class Schema {
		private int numCols;
		private ColumnType[] cols;
		private String[] colNames;

		private Schema(int numCols) throws IllegalArgumentException {
			if (numCols <= 0)
				throw new IllegalArgumentException();

			this.numCols = numCols;
			colNames = new String[numCols];

			cols = new ColumnType[numCols];
			for (int i = 0; i < numCols; i++)
				cols[i] = ColumnType.NUMBER;
		}

		public ColumnType getColumnType(int index)
				throws IllegalArgumentException {
			if (index < 0 || index >= numCols)
				throw new IllegalArgumentException();
			return cols[index];
		}

		public String getColumnName(int index) throws IllegalArgumentException {
			if (index < 0 || index >= numCols)
				throw new IllegalArgumentException();
			return colNames[index];
		}

		private void setColumnType(int index, ColumnType coltype)
				throws IllegalArgumentException {
			if (index < 0 || index >= numCols)
				throw new IllegalArgumentException();
			cols[index] = coltype;
		}

		private void setColumnName(int index, String name)
				throws IllegalArgumentException {
			if (index < 0 || index >= numCols)
				throw new IllegalArgumentException();
			colNames[index] = name;
		}
	}

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Schema schema;
	@Persistent
	private List<Object[]> rows;
	@Persistent
	private boolean beingPopulated = true; // once set to false, can fetch data
	@Persistent
	private int numCols = -1; // row width; set after first call to add row
	@Persistent
	private String[] colNames;
	private final String id;
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public Dataset() {
		rows = new ArrayList<Object[]>();
		schema = null; // set in finishPopulationAndComputeSchema()
		id = now();
	}

	public void setColumnNames(String[] columnNames)
			throws IllegalStateException {
		if (!beingPopulated)
			throw new IllegalStateException();

		if (numCols == -1)
			numCols = columnNames.length;
		else if (numCols != columnNames.length)
			throw new IllegalArgumentException(
					"wrong number of columns in header");

		colNames = columnNames;
	}

	public void addRow(Object[] row) throws IllegalStateException,
			IllegalArgumentException {
		if (!beingPopulated)
			throw new IllegalStateException();
		if (numCols == -1)
			numCols = row.length;
		else if (numCols != row.length)
			throw new IllegalArgumentException(
					"wrong number of columns in this row");

		rows.add(row);
	}

	public void finishPopulationAndComputeSchema() {
		if (!beingPopulated)
			throw new IllegalStateException();

		beingPopulated = false;

		computeSchema();

		// fix data using schema
		for (int i = 0; i < rows.size(); i++)
			for (int col = 0; col < numCols; col++) {
				Object value = rows.get(i)[col];
				switch (schema.getColumnType(col)) {
				case STRING:
					if (value == null)
						value = "";
					value = value.toString();
					break;
				case NUMBER:
					if (value == null || value.equals(""))
						value = new Double(0.0);
					break;
				default:
					throw new RuntimeException("not implemented");
				}
			}
	}

	private void computeSchema() {
		int[] strings = new int[numCols];
		int[] numbers = new int[numCols];
		int[] nulls = new int[numCols];
		int[] others = new int[numCols];
		for (int i = 0; i < rows.size(); i++)
			for (int col = 0; col < numCols; col++)
				if (rows.get(i)[col] == null)
					nulls[col]++;
				else if (rows.get(i)[col] instanceof Double)
					numbers[col]++;
				else if (rows.get(i)[col] instanceof String
						&& ((String) rows.get(i)[col]).length() > 0)
					strings[col]++;
				else
					others[col]++;
		schema = new Schema(numCols);

		for (int i = 0; i < numCols; i++) {
			if (strings[i] > numbers[i] && strings[i] > nulls[i]
					&& strings[i] > others[i])
				schema.setColumnType(i, ColumnType.STRING);
			else if (numbers[i] > strings[i] && numbers[i] > nulls[i]
					&& numbers[i] > others[i])
				schema.setColumnType(i, ColumnType.NUMBER);
			// schema.setColumnType(i, ColumnType.STRING);
			else if (nulls[i] > strings[i] && nulls[i] > numbers[i]
					&& nulls[i] > others[i])
				schema.setColumnType(i, ColumnType.NUMBER);
			// schema.setColumnType(i, ColumnType.STRING);
			else if (others[i] > strings[i] && others[i] > numbers[i]
					&& others[i] > nulls[i])
				schema.setColumnType(i, ColumnType.STRING);

			schema.setColumnName(i, colNames[i]);
		}
	}

	public Schema getSchema() {
		return schema;
	}

	public String[][] get2DArray() {
		String[][] serializable_rows = new String[rows.size() + 2][numCols];
		serializable_rows[0][0] = id;
		for (int x = 0; x < numCols; x++) {
			serializable_rows[1][x] = colNames[x];
		}
		for (int i = 2; i < rows.size() + 2; i++) {
			for (int j = 0; j < numCols; j++) {
				serializable_rows[i][j] = rows.get(i - 2)[j].toString();
			}
		}

		return serializable_rows;
	}

	public String[] getColNames() {
		return colNames;
	}

	public Iterator<Object[]> iterator() {
		return rows.iterator();
	}

	public void print() {
		for (int i = 0; i < numCols; i++) {
			System.out.print(colNames[i] + "\t");
		}
		System.out.println();

		for (int j = 0; j < rows.size(); j++) {
			for (int x = 0; x < rows.get(j).length; x++) {
				System.out.print(rows.get(j)[x] + "\t");
			}
			System.out.println();
		}
	}

	public static String now() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.format(cal.getTime());

	}
}
