package org.rnd.jmagic.gui.dialogs;

import org.rnd.jmagic.comms.GameFinder;

public class GameFinderDialog extends javax.swing.JDialog
{
	public interface ExceptionListener<E extends Exception>
	{
		public void handleException(E e);
	}

	public interface JoinGameListener
	{
		public void joinGame(GameFinder.Game game);
	}

	private static final String[] HEADERS = new String[] {"Hosting Player", "Format", "Description"};

	private static final long serialVersionUID = 1L;

	private class UpdateGamesThread extends Thread
	{
		@Override
		public void run()
		{
			updateGames();
		}
	}

	private GameFinder finder;

	private java.util.List<GameFinder.Game> games = java.util.Collections.emptyList();

	private Object gamesLock = new Object();

	private ExceptionListener<GameFinder.GameFinderException> gameFinderExceptionListener = null;

	private ExceptionListener<java.io.IOException> ioExceptionListener = null;

	private JoinGameListener joinGameListener = null;

	private javax.swing.JTable table;

	private javax.swing.table.AbstractTableModel tableModel;

	public GameFinderDialog(String location, javax.swing.JFrame parent) throws java.net.URISyntaxException, IllegalArgumentException, java.net.MalformedURLException, java.io.IOException, GameFinder.GameFinderException
	{
		super(parent, "Game Finder (double-click to join)", true);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		this.finder = new GameFinder(location);

		this.addWindowListener(new java.awt.event.WindowAdapter()
		{
			@Override
			public void windowOpened(java.awt.event.WindowEvent e)
			{
				new UpdateGamesThread().start();
			}
		});

		javax.swing.JButton refreshButton = new javax.swing.JButton("Refresh");
		refreshButton.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				new UpdateGamesThread().start();
			}
		});
		this.add(refreshButton, java.awt.BorderLayout.PAGE_START);

		this.tableModel = new javax.swing.table.AbstractTableModel()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public int getColumnCount()
			{
				return HEADERS.length;
			}

			@Override
			public String getColumnName(int column)
			{
				return HEADERS[column];
			}

			@Override
			public int getRowCount()
			{
				synchronized(GameFinderDialog.this.gamesLock)
				{
					return GameFinderDialog.this.games.size();
				}
			}

			@Override
			public Object getValueAt(int arg0, int arg1)
			{
				GameFinder.Game game;

				synchronized(GameFinderDialog.this.gamesLock)
				{
					game = GameFinderDialog.this.games.get(arg0);
				}

				switch(arg1)
				{
				case 0:
					return game.hostPlayerName;
				case 1:
					return game.format;
				case 2:
					return game.description;
				}

				return null;
			}
		};

		this.table = new javax.swing.JTable(this.tableModel);
		this.table.addMouseListener(new java.awt.event.MouseAdapter()
		{
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e)
			{
				if(2 == e.getClickCount())
					joinSelectedGame();
			}
		});
		this.table.setAutoCreateRowSorter(true);
		this.table.setFillsViewportHeight(true);

		javax.swing.JButton joinButton = new javax.swing.JButton("Join Game");
		joinButton.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				joinSelectedGame();
			}
		});
		this.add(joinButton, java.awt.BorderLayout.PAGE_END);

		javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(this.table);
		this.add(scroll, java.awt.BorderLayout.CENTER);

		javax.swing.AbstractAction cancel = new javax.swing.AbstractAction()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				GameFinderDialog.this.dispose();
			}
		};
		javax.swing.KeyStroke cancelKeyStroke = javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		this.getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(cancelKeyStroke, "Cancel");
		this.getRootPane().getActionMap().put("Cancel", cancel);

		this.pack();
	}

	public void setGameFinderExceptionListener(ExceptionListener<GameFinder.GameFinderException> listener)
	{
		this.gameFinderExceptionListener = listener;
	}

	public void setJoinGameListener(JoinGameListener listener)
	{
		this.joinGameListener = listener;
	}

	public void setIOExceptionListener(ExceptionListener<java.io.IOException> listener)
	{
		this.ioExceptionListener = listener;
	}

	private void joinSelectedGame()
	{
		int row = this.table.getSelectedRow();
		if((-1 != row) && (null != this.joinGameListener))
			this.joinGameListener.joinGame(this.games.get(0));
	}

	private void updateGames()
	{
		try
		{
			java.util.List<GameFinder.Game> newGames = new java.util.ArrayList<GameFinder.Game>(this.finder.list());
			synchronized(this.gamesLock)
			{
				this.games = newGames;
			}
			this.tableModel.fireTableDataChanged();
		}
		catch(java.io.IOException e)
		{
			if(null != this.ioExceptionListener)
				this.ioExceptionListener.handleException(e);
		}
		catch(GameFinder.GameFinderException e)
		{
			if(null != this.gameFinderExceptionListener)
				this.gameFinderExceptionListener.handleException(e);
		}
	}
}
