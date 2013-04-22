package org.rnd.jmagic.gui;

public class Splash extends javax.swing.JDialog
{
	private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger("org.rnd.jmagic.gui.Splash");

	private static final long serialVersionUID = 1L;

	private javax.swing.JScrollPane scrollPane;
	private javax.swing.JTextArea textBox;

	public Splash(final javax.swing.JFrame parent)
	{
		super(parent, "jMagic Starting", true);

		this.textBox = new javax.swing.JTextArea(10, 60);
		this.textBox.setEditable(false);
		this.textBox.setLineWrap(true);
		this.textBox.setWrapStyleWord(true);

		this.scrollPane = new javax.swing.JScrollPane(Splash.this.textBox);
		this.scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		this.add(Splash.this.scrollPane);
		this.pack();
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);

		java.awt.Point location = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		location.translate((int)(this.getWidth() * -.5), (int)(this.getHeight() * -.5));
		this.setLocation(location);

		final java.util.logging.Handler handler = new java.util.logging.Handler()
		{
			@Override
			public void close() throws SecurityException
			{
				// Nothing to do here
			}

			@Override
			public void flush()
			{
				// Nothing to do here
			}

			@Override
			public void publish(java.util.logging.LogRecord record)
			{
				if(java.util.logging.Level.INFO == record.getLevel())
					addLine(record.getMessage());
			}
		};
		org.rnd.util.Logging.getRootLogger(LOG).addHandler(handler);

		this.addWindowListener(new java.awt.event.WindowAdapter()
		{
			@Override
			public void windowClosed(java.awt.event.WindowEvent e)
			{
				org.rnd.util.Logging.getRootLogger(LOG).removeHandler(handler);
			}
		});
	}

	/**
	 * Add a line after any lines already added, or at the top if no lines have
	 * been added yet. This is executed in the event processing thread, so it is
	 * safe to call this from any thread.
	 * 
	 * @param newLine The line to add
	 */
	public void addLine(final String newLine)
	{
		java.awt.EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				if(0 == Splash.this.textBox.getText().length())
					Splash.this.textBox.setText("> " + newLine);
				else
					Splash.this.textBox.append("\n> " + newLine);
			}
		});
	}

	/**
	 * Remove all lines added by {@link #addLine(String)}. This is executed in
	 * the event processing thread, so it is safe to call this from any thread.
	 */
	public void clear()
	{
		java.awt.EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				Splash.this.textBox.setText("");
			}
		});
	}
}
