package com.hql.factory;

import java.io.Serializable;
import java.util.List;

public class TableInfo implements Serializable {

	private static final long serialVersionUID = -5777243077517836019L;

	private String table_name;
	
	private String table_comment;

	private Column idCol;
	
	private String include = "\r\n";

	private List<Column> cols;

	private List<Column> noIdCols;
	
	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String tableName) {
		table_name = tableName;
	}

	public String getTable_comment() {
		return table_comment;
	}

	public void setTable_comment(String tableComment) {
		table_comment = tableComment;
	}

	public String getLowerClassName() {
		String name = table_name;
		while (name.indexOf("_") > 0) {
			int index = name.indexOf("_");
			name = name.subSequence(0, index)
					+ name.substring(index + 1, index + 2)
							.toUpperCase()
					+ name.substring(index + 2);
		}
		return name;
	}
	
	public String getAllLowerClassName() {
		String name = table_name;
		return name.replace("_", "").toLowerCase();
	}
	
	public String getClassName() {
		String name = getLowerClassName();
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	public List<Column> getCols() {
		return cols;
	}

	public void setCols(List<Column> cols) {
		this.cols = cols;
	}

	public void setIdCol(Column idCol) {
		this.idCol = idCol;
	}

	public Column getIdCol() {
		return idCol;
	}

	public void setInclude(String include) {
		this.include = this.include + include;
	}

	public String getInclude() {
		return include;
	}

	public List<Column> getNoIdCols() {
		return noIdCols;
	}

	public void setNoIdCols(List<Column> noIdCols) {
		this.noIdCols = noIdCols;
	}
}
