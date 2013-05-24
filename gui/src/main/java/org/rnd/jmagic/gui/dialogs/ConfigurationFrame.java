package org.rnd.jmagic.gui.dialogs;

public class ConfigurationFrame extends javax.swing.JFrame
{
	public abstract static class OptionPanel extends javax.swing.JPanel
	{
		private static final long serialVersionUID = 1L;

		private String name;

		public OptionPanel(String name)
		{
			super(true);

			this.name = name;
		}

		@Override
		public String getName()
		{
			return this.name;
		}

		/**
		 * This is called before the panel is displayed to load the "current"
		 * properties.
		 */
		public abstract void loadSettings(java.util.Properties properties);

		/**
		 * This is called when the panel is saved, to write the updated
		 * properties to file.
		 */
		public abstract void saveChanges(java.util.Properties properties);
	}

	private static final long serialVersionUID = 1L;

	private java.util.Set<OptionPanel> optionPanels;
	private final javax.swing.DefaultListModel listModel;
	private final javax.swing.JPanel content;
	private final java.util.Properties properties;

	public ConfigurationFrame(java.util.Properties properties)
	{
		super("jMagic Settings");

		this.properties = properties;

		this.optionPanels = new java.util.HashSet<OptionPanel>();

		this.listModel = new javax.swing.DefaultListModel();
		final java.awt.CardLayout contentLayout = new java.awt.CardLayout();
		this.content = new javax.swing.JPanel(contentLayout);

		javax.swing.JPanel buttonPanel = new javax.swing.JPanel(new java.awt.FlowLayout());
		{
			javax.swing.JButton applyButton = new javax.swing.JButton(new javax.swing.AbstractAction("Apply")
			{
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					ConfigurationFrame.this.save();
				}
			});
			buttonPanel.add(applyButton);

			javax.swing.JButton okButton = new javax.swing.JButton(new javax.swing.AbstractAction("OK")
			{
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					ConfigurationFrame.this.save();
					ConfigurationFrame.this.dispose();
				}
			});
			buttonPanel.add(okButton);

			javax.swing.AbstractAction cancelAction = new javax.swing.AbstractAction("Cancel")
			{
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					ConfigurationFrame.this.dispose();
				}
			};
			javax.swing.JButton cancelButton = new javax.swing.JButton(cancelAction);
			buttonPanel.add(cancelButton);

			javax.swing.KeyStroke cancelKeyStroke = javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
			cancelButton.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(cancelKeyStroke, "Cancel");
			cancelButton.getActionMap().put("Cancel", cancelAction);
		}

		javax.swing.JPanel right = new javax.swing.JPanel();
		right.setLayout(new javax.swing.BoxLayout(right, javax.swing.BoxLayout.Y_AXIS));
		right.setPreferredSize(new java.awt.Dimension(600, 600));
		right.add(this.content);
		right.add(buttonPanel);

		final javax.swing.JList tree = new javax.swing.JList(this.listModel);
		tree.setPreferredSize(new java.awt.Dimension(150, 600));
		tree.addListSelectionListener(new javax.swing.event.ListSelectionListener()
		{
			@Override
			public void valueChanged(javax.swing.event.ListSelectionEvent e)
			{
				String selected = (String)tree.getSelectedValue();

				contentLayout.show(ConfigurationFrame.this.content, selected);
			}
		});
		final javax.swing.JSplitPane split = new javax.swing.JSplitPane(javax.swing.JSplitPane.HORIZONTAL_SPLIT, tree, right);

		this.add(split);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	}

	public void addOptionPanel(OptionPanel panel)
	{
		this.optionPanels.add(panel);
		int i;
		for(i = 0; i < this.listModel.size(); ++i)
			if(this.listModel.get(i).toString().compareTo(panel.getName()) > 0)
				break;
		this.listModel.add(i, panel.getName());
		this.content.add(panel, panel.getName());
	}

	public void load()
	{
		for(OptionPanel option: this.optionPanels)
			option.loadSettings(this.properties);
	}

	public void save()
	{
		for(OptionPanel option: this.optionPanels)
			option.saveChanges(this.properties);
	}
}
