package aufgabe7;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Table {
	private List<String> columnNames;
	private List<List<String>> columnData;
	private int max;


	public Table() {
		columnNames = new ArrayList<String>();
		columnData = new ArrayList<List<String>>();
		max = -1;
	}

	public void addColumn(String data) {
		columnNames.add(data);
	}

	public void addColumnData(int index, String data, ResultSet myRs) {
		columnData.get(index).add(data);
		if (index > max) {
			max = index;
		}

	}

	public int getTableCount() {
		return columnNames.size();
	}

	public String getColumn(int index) {
		return columnNames.get(index);
	}

	public List<String> getRow(int index) {

		return columnData.get(index);

	}

	public int getRowCount() {
		return max + 1;
	}
	
	public void addRow(){
		columnData.add(new ArrayList<String>());
	}

}
