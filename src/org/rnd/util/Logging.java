package org.rnd.util;

public class Logging
{
	/**
	 * Add a log-record handler that pops up a dialog with the record message. A
	 * record with level WARNING pops-up a warning dialog box. A record with
	 * level SEVERE pops-up an error dialog box.
	 * 
	 * @param parent The JFrame to lock-out while this dialog is popped-up.
	 */
	public static void addDialogHandler(final String application, final java.util.logging.Logger logger, final javax.swing.JFrame parent)
	{
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
				java.util.logging.Level level = record.getLevel();
				final String message = record.getMessage();

				if(java.util.logging.Level.WARNING == level)
				{
					java.awt.EventQueue.invokeLater(new Runnable()
					{
						@Override
						public void run()
						{
							javax.swing.JOptionPane.showMessageDialog(parent, message, application + " Warning", javax.swing.JOptionPane.WARNING_MESSAGE);
						}
					});
				}
				else if(java.util.logging.Level.SEVERE == level)
				{
					java.awt.EventQueue.invokeLater(new Runnable()
					{
						@Override
						public void run()
						{
							javax.swing.JOptionPane.showMessageDialog(parent, message, application + " Error", javax.swing.JOptionPane.ERROR_MESSAGE);
						}
					});
				}
			}
		};
		logger.addHandler(handler);

		parent.addWindowListener(new java.awt.event.WindowAdapter()
		{
			@Override
			public void windowClosed(java.awt.event.WindowEvent e)
			{
				logger.removeHandler(handler);
			}
		});
	}

	public static java.util.logging.Logger getRootLogger(java.util.logging.Logger reference)
	{
		java.util.logging.Logger parentLogger = reference.getParent();
		java.util.logging.Logger rootLogger = reference;
		while(null != parentLogger)
		{
			rootLogger = parentLogger;
			parentLogger = parentLogger.getParent();
		}
		return rootLogger;
	}
}
