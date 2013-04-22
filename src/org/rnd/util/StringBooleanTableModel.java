package org.rnd.util;

public abstract class StringBooleanTableModel extends javax.swing.table.AbstractTableModel
{
	public static class TableEntry
	{
		public boolean enabled;
		public final String property;

		public TableEntry(String property, boolean enabled)
		{
			this.property = property;
			this.enabled = enabled;
		}
	}

	// Swapping these two values with switch the order of the columns.
	// Useful, but be warned that any column width adjustments will need
	// to be updated as well.
	private static final int COLUMN_ENABLED = 0;

	private static final int COLUMN_NAME = 1;
	private static final long serialVersionUID = 1L;

	protected java.util.List<TableEntry> data;

	public StringBooleanTableModel()
	{
		this.data = new java.util.LinkedList<TableEntry>();
	}

	public void addRow(String property, boolean enabled)
	{
		this.data.add(new TableEntry(property, enabled));
	}

	public void clear()
	{
		this.data.clear();
	}

	public abstract String getCheckboxColumnTitle();

	@Override
	public Class<?> getColumnClass(int c)
	{
		if(c == COLUMN_ENABLED)
			// This makes the enabled column a checkbox instead of text
			return Boolean.class;
		else if(c == COLUMN_NAME)
			// This is the same as just Object.class (it uses
			// toString())
			return String.class;
		return null;
	}

	@Override
	public int getColumnCount()
	{
		// Adapter Name, and Adapter State (not necessarily in that
		// order)
		return 2;
	}

	@Override
	public String getColumnName(int col)
	{
		if(col == COLUMN_ENABLED)
			return this.getCheckboxColumnTitle();
		else if(col == COLUMN_NAME)
			return this.getStringColumnTitle();
		return null;
	}

	/**
	 * @return A map of the interface adapter's name, to its state (true is
	 * enabled, false is disabled).
	 */
	public java.util.LinkedHashMap<String, Boolean> getData()
	{
		java.util.LinkedHashMap<String, Boolean> copy = new java.util.LinkedHashMap<String, Boolean>();
		for(TableEntry entry: this.data)
			copy.put(entry.property, entry.enabled);
		return copy;
	}

	@Override
	public int getRowCount()
	{
		// Add an empty row where users can enter in data for a new row.
		return this.data.size() + 1;
	}

	public abstract String getStringColumnTitle();

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		if(rowIndex == this.data.size())
		{
			// Values for the empty row at the end of the table where
			// new adapters are added.
			if(columnIndex == COLUMN_ENABLED)
				return false;
			else if(columnIndex == COLUMN_NAME)
				return "";
			return null;
		}

		if(columnIndex == COLUMN_ENABLED)
			return this.data.get(rowIndex).enabled;
		else if(columnIndex == COLUMN_NAME)
			return this.data.get(rowIndex).property;
		return null;
	}

	@Override
	public boolean isCellEditable(int row, int col)
	{
		// The only editable cells are the name box in the last row, and
		// the checkboxes in all the other rows.
		return ((col == COLUMN_ENABLED) != (row == this.data.size()));
	}

	/**
	 * Shifts all the selected indices in the table either up or down, depending
	 * on the up parameter. Works on ranges of consecutive indices, so that if a
	 * single member of a range can't be moved (ie: the first row in the table
	 * can't be moved up), then the entire range won't be moved).
	 * 
	 * @param up True to move the selected indices up; down otherwise.
	 * @param table The table this is the data model for.
	 */
	public void moveSelected(boolean up, javax.swing.JTable table)
	{
		int[] indices = table.getSelectedRows();
		java.util.Arrays.sort(indices);
		for(int iterator = 0; iterator < indices.length; ++iterator)
		{
			int startRange = indices[iterator];
			int endRange = startRange;
			while(iterator + 1 < indices.length && indices[iterator + 1] == endRange + 1)
			{
				++endRange;
				++iterator;
			}

			if((up && startRange > 0) || (!up && endRange < this.data.size() - 1))
			{
				int removeFrom = (up ? startRange - 1 : endRange + 1);
				int insertAt = (up ? endRange : startRange);
				this.data.add(insertAt, this.data.remove(removeFrom));
				this.fireTableRowsUpdated(Math.min(removeFrom, startRange), Math.max(insertAt, endRange));
				table.getSelectionModel().removeSelectionInterval(insertAt, insertAt);
				table.getSelectionModel().addSelectionInterval(removeFrom, removeFrom);
			}

			++iterator;
		}
	}

	/**
	 * Remove the selected indices from the table data.
	 * 
	 * @param table The table this is the data model for.
	 */
	public void removeSelected(javax.swing.JTable table)
	{
		int[] indices = table.getSelectedRows();
		java.util.Arrays.sort(indices);

		for(int i = indices.length - 1; i >= 0; --i)
		{
			int index = indices[i];
			if(index < this.data.size())
			{
				this.data.remove(index);
				this.fireTableRowsDeleted(index, index);
			}
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col)
	{
		// Adding an adapter
		if(row == this.data.size())
		{
			if(col == COLUMN_NAME)
			{
				if(!validateNewEntry((String)value))
					return;

				this.data.add(new TableEntry((String)value, false));
				this.fireTableRowsInserted(row + 1, row + 1);
			}
			return;
		}

		// Updating the state of an existing adapter
		if(col == COLUMN_ENABLED)
		{
			this.data.get(row).enabled = (Boolean)value;
			this.fireTableCellUpdated(row, col);
		}
	}

	public abstract boolean validateNewEntry(String value);
}
