package org.rnd.jmagic.gui.dialogs;

import org.rnd.jmagic.gui.*;

public class TextChoicePanel extends javax.swing.JPanel
{
	private static final long serialVersionUID = 1L;

	private Play gui;
	private java.util.List<Object> choices;
	private java.util.List<Object> chosen;
	private boolean chooseExactlyOne;
	private javax.swing.JButton doneButton;

	public <T> TextChoicePanel(Play gui, java.util.List<T> choices, java.util.Comparator<T> comparator, boolean chooseExactlyOne)
	{
		super(new java.awt.BorderLayout());

		this.gui = gui;
		this.choices = new java.util.LinkedList<Object>(choices);
		if(chooseExactlyOne)
			this.chosen = null;
		else
			this.chosen = new java.util.LinkedList<Object>();
		this.chooseExactlyOne = chooseExactlyOne;

		javax.swing.Box choiceBox = javax.swing.Box.createVerticalBox();
		javax.swing.ButtonGroup group = (chooseExactlyOne ? new javax.swing.ButtonGroup() : null);

		java.util.List<T> sorted = new java.util.LinkedList<T>(choices);
		java.util.Collections.sort(sorted, comparator);
		int scrollHeight = 1;
		for(final T choice: sorted)
		{
			final javax.swing.JToggleButton button;
			if(chooseExactlyOne)
			{
				button = new javax.swing.JRadioButton();
				group.add(button);
			}
			else
				button = new javax.swing.JCheckBox();

			button.addActionListener(new java.awt.event.ActionListener()
			{
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					TextChoicePanel.this.toggleChoice(choice);
				}
			});

			javax.swing.JTextPane text = new JMagicTextPane(true);
			text.addMouseListener(new java.awt.event.MouseAdapter()
			{
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e)
				{
					button.doClick();
					super.mouseClicked(e);
				}
			});
			text.setText(choice.toString());

			button.setAlignmentY(0.2f);
			text.setAlignmentY(0.0f);

			javax.swing.Box box = javax.swing.Box.createHorizontalBox();
			box.add(button);
			box.add(text);
			box.add(javax.swing.Box.createHorizontalGlue());
			choiceBox.add(box);
			scrollHeight = box.getPreferredSize().height;
		}

		final javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(choiceBox, javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		java.awt.Dimension choiceBoxSize = choiceBox.getPreferredSize();
		int maximumHeight = gui.mainWindow.getSize().height - 200;
		if(maximumHeight < choiceBoxSize.height)
			scrollPane.getViewport().setPreferredSize(new java.awt.Dimension(choiceBoxSize.width, maximumHeight));
		scrollPane.getVerticalScrollBar().setUnitIncrement(scrollHeight);
		this.add(scrollPane, java.awt.BorderLayout.CENTER);

		this.doneButton = new javax.swing.JButton("Done");
		this.doneButton.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				TextChoicePanel.this.gui.choose = new java.util.LinkedList<Integer>();
				for(int i = 0; i < TextChoicePanel.this.choices.size(); ++i)
					if(TextChoicePanel.this.chosen.contains(TextChoicePanel.this.choices.get(i)))
						TextChoicePanel.this.gui.choose.add(i);
				TextChoicePanel.this.gui.choiceReady();
			}
		});
		this.doneButton.setEnabled(!chooseExactlyOne);
		javax.swing.Box doneBox = javax.swing.Box.createHorizontalBox();
		doneBox.add(javax.swing.Box.createHorizontalGlue());
		doneBox.add(this.doneButton);
		this.add(doneBox, java.awt.BorderLayout.PAGE_END);

		// Scroll back up to the top after all the text areas are done updating
		// the viewport
		java.awt.EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				scrollPane.getViewport().setViewPosition(new java.awt.Point(0, 0));
			}
		});
	}

	private void toggleChoice(Object choice)
	{
		if(this.chooseExactlyOne)
		{
			this.chosen = java.util.Collections.singletonList(choice);
		}
		else
		{
			if(this.chosen.contains(choice))
				this.chosen.remove(choice);
			else
				this.chosen.add(choice);
		}

		this.doneButton.setEnabled(true);
	}
}
