package org.rnd.jmagic.gui.dialogs;

import org.rnd.jmagic.gui.*;

public class ActionsListDialog extends javax.swing.JInternalFrame
{
	private static final long serialVersionUID = 1L;
	private final Play gui;
	private final java.util.List<javax.swing.Box> boxes;

	public ActionsListDialog(Play gui)
	{
		this.setLayout(new javax.swing.BoxLayout(this.getContentPane(), javax.swing.BoxLayout.Y_AXIS));
		this.setResizable(false);
		this.setTitle("X = undo");
		this.boxes = new java.util.LinkedList<javax.swing.Box>();
		this.gui = gui;
	}

	public void addAction(final org.rnd.jmagic.sanitized.SanitizedPlayerAction action)
	{
		final javax.swing.Box box = javax.swing.Box.createHorizontalBox();
		this.boxes.add(box);

		javax.swing.JButton removeButton = new javax.swing.JButton(new javax.swing.ImageIcon(ActionsListDialog.class.getResource("/org/rnd/jmagic/gui/delete.png")));
		removeButton.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				ActionsListDialog.this.gui.choose.remove(ActionsListDialog.this.boxes.indexOf(box));
				ActionsListDialog.this.gui.removeIndication(action.sourceID);
				if(ActionsListDialog.this.gui.choose.isEmpty())
					ActionsListDialog.this.setVisible(false);

				ActionsListDialog.this.boxes.remove(box);
				ActionsListDialog.this.remove(box);
				ActionsListDialog.this.pack();
			}
		});

		box.add(removeButton);

		javax.swing.JTextPane text = new JMagicTextPane(true);
		text.setText(action.toString());
		box.add(text);

		box.add(javax.swing.Box.createHorizontalGlue());

		this.add(box);
		this.pack();
		this.setVisible(true);
	}

	public void clearList()
	{
		for(javax.swing.Box box: this.boxes)
			this.remove(box);
		this.boxes.clear();
	}
}