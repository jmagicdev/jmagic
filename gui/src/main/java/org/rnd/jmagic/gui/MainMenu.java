package org.rnd.jmagic.gui;

import org.rnd.jmagic.gui.dialogs.*;

public class MainMenu extends javax.swing.JMenuBar
{
	private static final long serialVersionUID = 1L;

	public MainMenu(final Play gui)
	{
		javax.swing.JMenu game = new javax.swing.JMenu("Game");

		javax.swing.JMenuItem settings = new javax.swing.JMenuItem("Settings");
		settings.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				ConfigurationFrame options = gui.configuration;
				options.load();
				options.pack();
				options.setVisible(true);
			}
		});

		javax.swing.JMenuItem quit = new javax.swing.JMenuItem("Quit");
		quit.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				gui.mainWindow.dispose();
			}
		});
		quit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		game.add(settings);
		game.add(quit);
		this.add(game);

		javax.swing.JMenuItem about = new javax.swing.JMenuItem("About jMagic");
		about.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				javax.swing.JOptionPane.showMessageDialog(gui.mainWindow, "jMagic version " + new org.rnd.jmagic.Version() + ".\n\nThe developers of jMagic have nothing (official) to do with Wizards of the Coast or Magic: the Gathering.\n\nYou may distribute jMagic freely as long as you don't claim credit for it.", "About jMagic", javax.swing.JOptionPane.PLAIN_MESSAGE);
			}
		});

		javax.swing.JMenu help = new javax.swing.JMenu("Help");
		help.add(about);
		this.add(help);
	}
}
