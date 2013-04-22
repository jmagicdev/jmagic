package org.rnd.jmagic.interfaceAdapters;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gui.*;
import org.rnd.jmagic.sanitized.*;

public class YieldAdapter extends SimpleConfigurableInterface
{
	private final class YieldOptionPanel extends org.rnd.jmagic.gui.dialogs.ConfigurationFrame.OptionPanel
	{
		private static final long serialVersionUID = 1L;

		private org.rnd.util.StringBooleanTableModel tableModel;

		public YieldOptionPanel()
		{
			super("Yields");

			this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));

			this.tableModel = new org.rnd.util.StringBooleanTableModel()
			{
				private static final long serialVersionUID = 1L;

				@Override
				public String getCheckboxColumnTitle()
				{
					return "Enabled";
				}

				@Override
				public String getStringColumnTitle()
				{
					return "Yield Pattern";
				}

				@Override
				public boolean validateNewEntry(String value)
				{
					try
					{
						java.util.regex.Pattern.compile(value);
						return true;
					}
					catch(java.util.regex.PatternSyntaxException e)
					{
						return false;
					}
				}
			};

			final javax.swing.JTable table = new javax.swing.JTable(this.tableModel);
			table.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 70));
			// Make the second column take up most of the space.
			table.getColumnModel().getColumn(1).setPreferredWidth(450);

			javax.swing.JButton interfaceAdapterRemove = new javax.swing.JButton(new javax.swing.ImageIcon(Start.class.getResource("delete.png")));
			interfaceAdapterRemove.addActionListener(new java.awt.event.ActionListener()
			{
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					YieldOptionPanel.this.tableModel.removeSelected(table);
				}
			});

			javax.swing.Box interfaceAdaptersBox = javax.swing.Box.createHorizontalBox();
			interfaceAdaptersBox.setBorder(javax.swing.BorderFactory.createTitledBorder("Yields"));
			interfaceAdaptersBox.add(new javax.swing.JScrollPane(table));
			interfaceAdaptersBox.add(javax.swing.Box.createHorizontalStrut(5));
			interfaceAdaptersBox.add(interfaceAdapterRemove);

			this.add(interfaceAdaptersBox);
		}

		@Override
		public void loadSettings(java.util.Properties properties)
		{
			this.tableModel.clear();
			for(java.util.Map.Entry<java.util.regex.Pattern, Boolean> entry: YieldAdapter.this.patterns.entrySet())
				this.tableModel.addRow(entry.getKey().pattern(), entry.getValue().booleanValue());
		}

		@Override
		public void saveChanges(java.util.Properties properties)
		{
			YieldAdapter.this.patterns.clear();
			for(java.util.Map.Entry<String, Boolean> entry: this.tableModel.getData().entrySet())
			{
				YieldAdapter.this.patterns.put(java.util.regex.Pattern.compile(entry.getKey()), entry.getValue());
			}
		}
	}

	private java.util.Map<java.util.regex.Pattern, Boolean> patterns;
	private SanitizedGameState state = null;

	public YieldAdapter(ConfigurableInterface adapt)
	{
		super(adapt);

		this.patterns = new java.util.HashMap<java.util.regex.Pattern, Boolean>();
		this.state = null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends java.io.Serializable> java.util.List<Integer> choose(ChooseParameters<T> parameterObject)
	{
		if(parameterObject.type == org.rnd.jmagic.engine.PlayerInterface.ChoiceType.NORMAL_ACTIONS && this.state != null)
		{
			SanitizedZone stack = (SanitizedZone)this.state.get(this.state.stack);
			if(stack != null && !stack.objects.isEmpty())
			{
				SanitizedIdentified topOfStack = this.state.get(stack.objects.get(0));
				if(topOfStack != null)
				{
					String name = topOfStack.toString();
					for(java.util.Map.Entry<java.util.regex.Pattern, Boolean> entry: this.patterns.entrySet())
						if(entry.getValue().booleanValue() && entry.getKey().matcher(name).matches())
							return java.util.Collections.emptyList();
					int yieldIndex = parameterObject.choices.size();

					ChooseParameters<T> newParameters = new ChooseParameters<T>(parameterObject);
					if(org.rnd.jmagic.engine.generators.Maximum.get(newParameters.number) == 0)
						newParameters.number = new Set(new org.rnd.util.NumberRange(0, 1));
					newParameters.choices.add((T)new SanitizedPlayerAction("Always yield to this.", topOfStack.ID, topOfStack.ID));
					java.util.List<Integer> ret = super.choose(newParameters);
					if(ret.contains(yieldIndex))
					{
						String pattern = java.util.regex.Pattern.quote(name);
						this.patterns.put(java.util.regex.Pattern.compile(pattern), true);
						return java.util.Collections.emptyList();
					}
					return ret;
				}
			}
		}

		return super.choose(parameterObject);
	}

	@Override
	public void alertState(SanitizedGameState sanitizedGameState)
	{
		this.state = sanitizedGameState;
		super.alertState(sanitizedGameState);
	}

	@Override
	public org.rnd.jmagic.gui.dialogs.ConfigurationFrame.OptionPanel getOptionPanel()
	{
		return new YieldOptionPanel();
	}
}
