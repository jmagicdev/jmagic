package org.rnd.jmagic.gui.dialogs;

import java.awt.event.*;

import javax.swing.*;

import org.rnd.jmagic.gui.*;
import org.rnd.jmagic.sanitized.*;

public class ModeChoicePanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private Play gui;
	private java.util.List<SanitizedMode> choices;
	private java.util.Set<SanitizedMode> choice;
	private int minimum;
	private int maximum;
	private JButton doneButton;

	public ModeChoicePanel(Play gui, final java.util.List<SanitizedMode> modes, org.rnd.jmagic.engine.Set number)
	{
		super();

		this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

		this.gui = gui;
		this.choices = modes;
		this.choice = new java.util.HashSet<SanitizedMode>();
		number = org.rnd.jmagic.engine.generators.Intersect.get(number, new org.rnd.jmagic.engine.Set(new org.rnd.util.NumberRange(0, modes.size())));
		this.minimum = org.rnd.jmagic.engine.generators.Minimum.get(number);
		this.maximum = org.rnd.jmagic.engine.generators.Maximum.get(number);

		for(final SanitizedMode mode: modes)
		{
			String modeName = mode.toString();
			modeName = Character.toUpperCase(modeName.charAt(0)) + modeName.substring(1);
			JCheckBox check = new JCheckBox(new AbstractAction(modeName)
			{
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e)
				{
					ModeChoicePanel.this.toggleChoice(mode);
				}
			});
			Box checkBox = Box.createHorizontalBox();
			checkBox.add(check);
			checkBox.add(Box.createHorizontalGlue());
			this.add(checkBox);
		}

		this.doneButton = new JButton(new AbstractAction("Done")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e)
			{
				ModeChoicePanel.this.gui.choose = new java.util.LinkedList<Integer>();
				for(int i = 0; i < ModeChoicePanel.this.choices.size(); ++i)
					if(ModeChoicePanel.this.choice.contains(ModeChoicePanel.this.choices.get(i)))
						ModeChoicePanel.this.gui.choose.add(i);
				ModeChoicePanel.this.gui.choiceReady();
			}
		});
		this.doneButton.setEnabled(false);
		Box doneBox = Box.createHorizontalBox();
		doneBox.add(Box.createHorizontalGlue());
		doneBox.add(this.doneButton);
		this.add(doneBox);
	}

	private void toggleChoice(SanitizedMode mode)
	{
		if(this.choice.contains(mode))
			this.choice.remove(mode);
		else
			this.choice.add(mode);

		// Enable the done button only if the correct number of choices have
		// been made
		this.doneButton.setEnabled(this.choice.size() >= this.minimum && this.choice.size() <= this.maximum);
	}
}
