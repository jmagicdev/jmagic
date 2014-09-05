package org.rnd.jmagic.gui;

import org.rnd.jmagic.gui.dialogs.*;

public class Start
{
	/**
	 * Creating a local interface failed.
	 */
	private static class CreateLocalException extends Exception
	{
		private static final long serialVersionUID = 0L;

		public CreateLocalException()
		{
			super();
		}

		public CreateLocalException(Throwable cause)
		{
			super(cause);
		}
	}

	private class ServerHost implements java.awt.event.ActionListener
	{
		private boolean useGameFinder;

		public ServerHost(boolean useGameFinder)
		{
			this.useGameFinder = useGameFinder;
		}

		@Override
		public void actionPerformed(java.awt.event.ActionEvent event)
		{
			final String gameFinderLocation;
			if(this.useGameFinder)
				gameFinderLocation = Start.this.properties.getProperty(PROPERTIES_GAME_FINDER_LOCATION);
			else
				gameFinderLocation = null;

			final int numPlayers;
			try
			{
				numPlayers = Integer.parseInt(Start.this.playersField.getText());
			}
			catch(NumberFormatException e)
			{
				LOG.log(java.util.logging.Level.SEVERE, "Number of players must be an integer", e);
				return;
			}

			final int port;
			try
			{
				port = Integer.parseInt(Start.this.portField.getText());
			}
			catch(NumberFormatException e)
			{
				LOG.log(java.util.logging.Level.SEVERE, "Port must be an integer", e);
				return;
			}

			final String name = Start.this.nameField.getText();
			if(0 == name.length())
			{
				LOG.severe("Name must be specified");
				return;
			}

			LOG.fine("Starting host thread");
			final Thread game = new Thread("Host")
			{
				@Override
				public void run()
				{
					org.rnd.jmagic.engine.Deck deck = getDeck();
					// The errors are logged elsewhere, so don't say anything
					if(null == deck)
					{
						hideSplash();
						return;
					}

					org.rnd.jmagic.comms.Server server = new org.rnd.jmagic.comms.Server(Start.this.gameType, numPlayers, port);

					try
					{
						if(0 == port)
							for(int i = 0; i < numPlayers; ++i)
								addLocal(deck, name + i, server);
						else
							addLocal(deck, name, server);
					}
					catch(CreateLocalException e)
					{
						// The error has already been logged; just quit
						hideSplash();
						return;
					}

					if(null != gameFinderLocation)
					{
						String descriptionMessage;
						if(FORMAT_STRINGS.contains(Start.this.gameType.getName()))
							descriptionMessage = "Describe your game";
						else
							descriptionMessage = "Describe your game; make sure to specify what rules are being used since this is a custom format";

						String description;
						do
						{
							description = javax.swing.JOptionPane.showInputDialog(Start.this.frame, descriptionMessage);
							// The user canceled when giving a description
							if(null == description)
							{
								hideSplash();
								return;
							}
							description = description.trim();
						}
						while(description.isEmpty());

						server.useGameFinder(gameFinderLocation, description);
					}

					server.run();
					hideSplash();
				}

				private void addLocal(org.rnd.jmagic.engine.Deck deck, String name, org.rnd.jmagic.comms.Server server) throws CreateLocalException
				{
					SwingAdapter swing = new SwingAdapter(deck, name);
					org.rnd.jmagic.comms.ChatManager.MessagePoster messagePoster = server.addLocalPlayer(createLocal(swing), swing.getChatCallback());
					if(null == messagePoster)
					{
						// This error is logged to the same location as if
						// createLocal() threw a CreateLocalException, so throw
						// it here too to make error-handling easier upstream
						throw new CreateLocalException();
					}
					swing.setMessagePoster(messagePoster);
				}
			};
			game.start();
			saveProperties();

			Start.this.splash.addWindowListener(new java.awt.event.WindowAdapter()
			{
				@Override
				public void windowClosing(java.awt.event.WindowEvent e)
				{
					int option = javax.swing.JOptionPane.showConfirmDialog(Start.this.splash, "Are you sure you want to cancel hosting this game?", "jMagic Interrupt", javax.swing.JOptionPane.YES_NO_OPTION);
					if(javax.swing.JOptionPane.YES_OPTION == option)
					{
						LOG.fine("Interrupting the host thread");
						game.interrupt();
					}
				}
			});

			// This will block until the splash dialog is invisible
			Start.this.splash.setVisible(true);
		}
	}

	private static class StartOptions extends ConfigurationFrame.OptionPanel
	{
		private static final long serialVersionUID = 1L;

		private static void loadDataIntoTable(java.util.Properties properties, String dataKey, String enabledKey, org.rnd.util.StringBooleanTableModel model)
		{
			model.clear();

			String dataProperty = properties.getProperty(dataKey);
			if(0 == dataProperty.length())
				return;

			String[] data = dataProperty.split("[|]");
			char[] enabled = properties.getProperty(enabledKey).toCharArray();
			for(int i = 0; i < data.length; ++i)
				// '1' is true, '0' is false. anything else is also false.
				model.addRow(data[i], (enabled[i] == '1'));
		}

		private static void saveDataFromTable(java.util.Properties properties, String dataKey, String enabledKey, org.rnd.util.StringBooleanTableModel model)
		{
			java.util.Map<String, Boolean> data = model.getData();
			StringBuilder dataProperty = new StringBuilder();
			StringBuilder enabledProperty = new StringBuilder();

			for(java.util.Map.Entry<String, Boolean> entry: data.entrySet())
			{
				if(dataProperty.length() != 0)
					dataProperty.append('|');
				dataProperty.append(entry.getKey());
				enabledProperty.append(entry.getValue() ? '1' : '0');
			}

			properties.setProperty(dataKey, dataProperty.toString());
			properties.setProperty(enabledKey, enabledProperty.toString());
		}

		private org.rnd.util.StringBooleanTableModel adapterModel;

		private javax.swing.JTextField cardArtLocation;

		private javax.swing.JTextField gameFinderLocation;

		private org.rnd.util.StringBooleanTableModel jarModel;

		private org.rnd.util.StringBooleanTableModel packageModel;

		public StartOptions()
		{
			super("jMagic");

			javax.swing.SpringLayout layout = new javax.swing.SpringLayout();
			this.setLayout(layout);

			this.cardArtLocation = new javax.swing.JTextField(35);

			javax.swing.Box artLocationBox = javax.swing.Box.createHorizontalBox();
			artLocationBox.add(new javax.swing.JLabel("Card art location:"));
			artLocationBox.add(javax.swing.Box.createHorizontalStrut(5));
			artLocationBox.add(this.cardArtLocation);

			javax.swing.JButton browseArt = new javax.swing.JButton("Browse");
			browseArt.addActionListener(new java.awt.event.ActionListener()
			{
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
					if(0 != StartOptions.this.cardArtLocation.getText().length())
						chooser.setCurrentDirectory(new java.io.File(StartOptions.this.cardArtLocation.getText()));
					chooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
					chooser.setAcceptAllFileFilterUsed(false);

					if(javax.swing.JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(StartOptions.this))
						StartOptions.this.cardArtLocation.setText(chooser.getSelectedFile().getPath());
				}
			});
			artLocationBox.add(browseArt);

			layout.putConstraint(javax.swing.SpringLayout.WEST, artLocationBox, 5, javax.swing.SpringLayout.WEST, this);
			layout.putConstraint(javax.swing.SpringLayout.NORTH, artLocationBox, 5, javax.swing.SpringLayout.NORTH, this);
			this.add(artLocationBox);

			this.gameFinderLocation = new javax.swing.JTextField(35);

			javax.swing.Box gameFinderBox = javax.swing.Box.createHorizontalBox();
			gameFinderBox.add(new javax.swing.JLabel("Game finder:"));
			gameFinderBox.add(javax.swing.Box.createHorizontalStrut(5));
			gameFinderBox.add(this.gameFinderLocation);

			layout.putConstraint(javax.swing.SpringLayout.WEST, gameFinderBox, 5, javax.swing.SpringLayout.WEST, this);
			layout.putConstraint(javax.swing.SpringLayout.NORTH, gameFinderBox, 5, javax.swing.SpringLayout.SOUTH, artLocationBox);
			this.add(gameFinderBox);

			final org.rnd.util.StringBooleanTableModel adaptersModel = new org.rnd.util.StringBooleanTableModel()
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
					return "Interface Adapter";
				}

				@Override
				public boolean validateNewEntry(String value)
				{
					// If this adapter is already in the table, don't add
					// it.
					for(TableEntry entry: this.data)
						if(entry.property.equals(value))
							return false;

					// Verify that the new entry is actually a class and an
					// interface adapter.
					try
					{
						Class<?> cls = Class.forName(value);
						if(!org.rnd.jmagic.interfaceAdapters.SimpleConfigurableInterface.class.isAssignableFrom(cls))
							// The class isn't an interface adapter, return.
							return false;
					}
					catch(ClassNotFoundException e)
					{
						// The class doesn't exist, don't add it.
						return false;
					}

					return true;
				}
			};
			this.adapterModel = adaptersModel;

			final javax.swing.JTable adapterTable = new javax.swing.JTable(adaptersModel);
			adapterTable.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 70));
			// Make the second column take up most of the space.
			adapterTable.getColumnModel().getColumn(1).setPreferredWidth(450);

			javax.swing.JButton interfaceAdapterMoveUp = new javax.swing.JButton(new javax.swing.ImageIcon(Start.class.getResource("move_up.png")));
			interfaceAdapterMoveUp.addActionListener(new java.awt.event.ActionListener()
			{
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					adaptersModel.moveSelected(true, adapterTable);
				}
			});

			javax.swing.JButton interfaceAdapterRemove = new javax.swing.JButton(new javax.swing.ImageIcon(Start.class.getResource("delete.png")));
			interfaceAdapterRemove.addActionListener(new java.awt.event.ActionListener()
			{
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					adaptersModel.removeSelected(adapterTable);
				}
			});

			javax.swing.JButton interfaceAdapterMoveDown = new javax.swing.JButton(new javax.swing.ImageIcon(Start.class.getResource("move_down.png")));
			interfaceAdapterMoveDown.addActionListener(new java.awt.event.ActionListener()
			{
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					adaptersModel.moveSelected(false, adapterTable);
				}
			});

			javax.swing.Box interfaceAdaptersButtonsBox = javax.swing.Box.createVerticalBox();
			interfaceAdaptersButtonsBox.add(interfaceAdapterMoveUp);
			interfaceAdaptersButtonsBox.add(javax.swing.Box.createVerticalStrut(5));
			interfaceAdaptersButtonsBox.add(interfaceAdapterRemove);
			interfaceAdaptersButtonsBox.add(javax.swing.Box.createVerticalStrut(5));
			interfaceAdaptersButtonsBox.add(interfaceAdapterMoveDown);

			javax.swing.Box interfaceAdaptersBox = javax.swing.Box.createHorizontalBox();
			interfaceAdaptersBox.setBorder(javax.swing.BorderFactory.createTitledBorder("Interface Adapters"));
			interfaceAdaptersBox.add(new javax.swing.JScrollPane(adapterTable));
			interfaceAdaptersBox.add(javax.swing.Box.createHorizontalStrut(5));
			interfaceAdaptersBox.add(interfaceAdaptersButtonsBox);

			layout.putConstraint(javax.swing.SpringLayout.WEST, interfaceAdaptersBox, 5, javax.swing.SpringLayout.WEST, this);
			layout.putConstraint(javax.swing.SpringLayout.NORTH, interfaceAdaptersBox, 5, javax.swing.SpringLayout.SOUTH, gameFinderBox);
			this.add(interfaceAdaptersBox);

			final org.rnd.util.StringBooleanTableModel jarModel = new org.rnd.util.StringBooleanTableModel()
			{
				private static final long serialVersionUID = 1L;

				@Override
				public String getCheckboxColumnTitle()
				{
					return "Load";
				}

				@Override
				public String getStringColumnTitle()
				{
					return "Path to jar file";
				}

				@Override
				public boolean validateNewEntry(String value)
				{
					try
					{
						java.net.URL rootURL = new java.net.URL("file:///" + System.getProperty("user.dir") + java.io.File.separator);
						java.net.URL jarURL = new java.net.URL(rootURL, value);
						return (new java.io.File(jarURL.getFile()).exists());
					}
					catch(java.net.MalformedURLException e)
					{
						return false;
					}
				}
			};
			this.jarModel = jarModel;

			final javax.swing.JTable jarTable = new javax.swing.JTable(jarModel);
			jarTable.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 70));
			// Make the second column take up most of the space.
			jarTable.getColumnModel().getColumn(1).setPreferredWidth(450);

			javax.swing.JButton jarMoveUp = new javax.swing.JButton(new javax.swing.ImageIcon(Start.class.getResource("move_up.png")));
			jarMoveUp.addActionListener(new java.awt.event.ActionListener()
			{
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					jarModel.moveSelected(true, jarTable);
				}
			});

			javax.swing.JButton jarRemove = new javax.swing.JButton(new javax.swing.ImageIcon(Start.class.getResource("delete.png")));
			jarRemove.addActionListener(new java.awt.event.ActionListener()
			{
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					jarModel.removeSelected(jarTable);
				}
			});

			javax.swing.JButton jarMoveDown = new javax.swing.JButton(new javax.swing.ImageIcon(Start.class.getResource("move_down.png")));
			jarMoveDown.addActionListener(new java.awt.event.ActionListener()
			{
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					jarModel.moveSelected(false, jarTable);
				}
			});

			javax.swing.Box jarButtonsBox = javax.swing.Box.createVerticalBox();
			jarButtonsBox.add(jarMoveUp);
			jarButtonsBox.add(javax.swing.Box.createVerticalStrut(5));
			jarButtonsBox.add(jarRemove);
			jarButtonsBox.add(javax.swing.Box.createVerticalStrut(5));
			jarButtonsBox.add(jarMoveDown);

			javax.swing.Box jarBox = javax.swing.Box.createHorizontalBox();
			jarBox.setBorder(javax.swing.BorderFactory.createTitledBorder("External Jars (restart required to load changes)"));
			jarBox.add(new javax.swing.JScrollPane(jarTable));
			jarBox.add(javax.swing.Box.createHorizontalStrut(5));
			jarBox.add(jarButtonsBox);

			layout.putConstraint(javax.swing.SpringLayout.WEST, jarBox, 5, javax.swing.SpringLayout.WEST, this);
			layout.putConstraint(javax.swing.SpringLayout.NORTH, jarBox, 5, javax.swing.SpringLayout.SOUTH, interfaceAdaptersBox);
			this.add(jarBox);

			final org.rnd.util.StringBooleanTableModel packageModel = new org.rnd.util.StringBooleanTableModel()
			{
				private static final long serialVersionUID = 1L;

				@Override
				public String getCheckboxColumnTitle()
				{
					return "Load";
				}

				@Override
				public String getStringColumnTitle()
				{
					return "Cards Package";
				}

				@Override
				public boolean validateNewEntry(String value)
				{
					return true;
				}
			};
			this.packageModel = packageModel;

			final javax.swing.JTable packageTable = new javax.swing.JTable(packageModel);
			packageTable.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 70));
			// Make the second column take up most of the space.
			packageTable.getColumnModel().getColumn(1).setPreferredWidth(450);

			javax.swing.JButton packageMoveUp = new javax.swing.JButton(new javax.swing.ImageIcon(Start.class.getResource("move_up.png")));
			packageMoveUp.addActionListener(new java.awt.event.ActionListener()
			{
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					packageModel.moveSelected(true, jarTable);
				}
			});

			javax.swing.JButton packageRemove = new javax.swing.JButton(new javax.swing.ImageIcon(Start.class.getResource("delete.png")));
			packageRemove.addActionListener(new java.awt.event.ActionListener()
			{
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					packageModel.removeSelected(jarTable);
				}
			});

			javax.swing.JButton packageMoveDown = new javax.swing.JButton(new javax.swing.ImageIcon(Start.class.getResource("move_down.png")));
			packageMoveDown.addActionListener(new java.awt.event.ActionListener()
			{
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					packageModel.moveSelected(false, jarTable);
				}
			});

			javax.swing.Box packageButtonsBox = javax.swing.Box.createVerticalBox();
			packageButtonsBox.add(packageMoveUp);
			packageButtonsBox.add(javax.swing.Box.createVerticalStrut(5));
			packageButtonsBox.add(packageRemove);
			packageButtonsBox.add(javax.swing.Box.createVerticalStrut(5));
			packageButtonsBox.add(packageMoveDown);

			javax.swing.Box packageBox = javax.swing.Box.createHorizontalBox();
			packageBox.setBorder(javax.swing.BorderFactory.createTitledBorder("Cards Packages (restart required to load changes)"));
			packageBox.add(new javax.swing.JScrollPane(packageTable));
			packageBox.add(javax.swing.Box.createHorizontalStrut(5));
			packageBox.add(packageButtonsBox);

			layout.putConstraint(javax.swing.SpringLayout.WEST, packageBox, 5, javax.swing.SpringLayout.WEST, this);
			layout.putConstraint(javax.swing.SpringLayout.NORTH, packageBox, 5, javax.swing.SpringLayout.SOUTH, jarBox);
			this.add(packageBox);

			javax.swing.JButton resetButton = new javax.swing.JButton("Reset Settings");
			resetButton.addActionListener(new java.awt.event.ActionListener()
			{
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					StartOptions.this.reset();
				}
			});
			javax.swing.Box resetBox = javax.swing.Box.createHorizontalBox();
			resetBox.add(resetButton);

			layout.putConstraint(javax.swing.SpringLayout.WEST, resetBox, 5, javax.swing.SpringLayout.WEST, this);
			layout.putConstraint(javax.swing.SpringLayout.NORTH, resetBox, 5, javax.swing.SpringLayout.SOUTH, packageBox);
			this.add(resetBox);
		}

		@Override
		public void loadSettings(java.util.Properties properties)
		{
			this.cardArtLocation.setText(properties.getProperty(Start.PROPERTIES_CARD_IMAGE_LOCATION));
			this.gameFinderLocation.setText(properties.getProperty(Start.PROPERTIES_GAME_FINDER_LOCATION));

			loadDataIntoTable(properties, Start.PROPERTIES_ADAPTERS, Start.PROPERTIES_ADAPTER_STATE, this.adapterModel);
			loadDataIntoTable(properties, Start.PROPERTIES_EXTERNAL_JARS, Start.PROPERTIES_EXTERNAL_JARS_LOAD, this.jarModel);
			loadDataIntoTable(properties, Start.PROPERTIES_CARDS_PACKAGES, Start.PROPERTIES_CARDS_PACKAGES_LOAD, this.packageModel);
		}

		private void reset()
		{
			java.util.Properties defaults = new java.util.Properties();
			for(java.util.Map.Entry<String, String> entry: defaultProperties.entrySet())
				defaults.setProperty(entry.getKey(), entry.getValue());
			this.adapterModel.getData();
			this.loadSettings(defaults);
			this.adapterModel.fireTableDataChanged();
			this.jarModel.fireTableDataChanged();
			this.packageModel.fireTableDataChanged();
		}

		@Override
		public void saveChanges(java.util.Properties properties)
		{
			String cardArt = this.cardArtLocation.getText();
			properties.setProperty(Start.PROPERTIES_CARD_IMAGE_LOCATION, cardArt);
			if(0 != cardArt.length())
				CardGraphics.setCardImageLocation(cardArt);

			String gameFinder = this.gameFinderLocation.getText();
			properties.setProperty(Start.PROPERTIES_GAME_FINDER_LOCATION, gameFinder);

			saveDataFromTable(properties, Start.PROPERTIES_ADAPTERS, Start.PROPERTIES_ADAPTER_STATE, this.adapterModel);
			saveDataFromTable(properties, Start.PROPERTIES_EXTERNAL_JARS, Start.PROPERTIES_EXTERNAL_JARS_LOAD, this.jarModel);
			saveDataFromTable(properties, Start.PROPERTIES_CARDS_PACKAGES, Start.PROPERTIES_CARDS_PACKAGES_LOAD, this.packageModel);
		}
	}

	private static final String CUSTOM_GAME_TYPE_VALUE = "Custom";

	private static final java.util.Map<String, String> defaultProperties = new java.util.HashMap<String, String>();

	private static final java.util.Set<String> FORMAT_STRINGS = new java.util.HashSet<String>();

	static
	{
		for(org.rnd.jmagic.engine.GameType gameType: org.rnd.jmagic.engine.GameType.list())
			FORMAT_STRINGS.add(gameType.getName());
	}

	private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger("org.rnd.jmagic.gui.Start");

	/**
	 * What log file to write to under the directory set where "%d" will be
	 * replaced with the log number
	 */
	private static final String LOG_FILE_PATTERN = "log%d.xml";

	/**
	 * How many log files can exist at maximum before the oldest is overwritten
	 */
	private static final int LOG_LIMIT = 10;

	private static final String PROPERTIES_ADAPTER_STATE = "Start.Adapters.State";

	private static final String PROPERTIES_ADAPTERS = "Start.Adapters";

	private static final String PROPERTIES_CARD_IMAGE_LOCATION = "Start.CardImageLocation";

	public static final String PROPERTIES_CARDS_PACKAGES = "Start.CardPackages";

	public static final String PROPERTIES_CARDS_PACKAGES_LOAD = "Start.CardPackages.Load";

	public static final String PROPERTIES_CUSTOM_GAME_TYPES = "Start.CustomGameTypes";

	private static final String PROPERTIES_DECK = "Start.Deck";

	private static final String PROPERTIES_EXTERNAL_JARS = "Start.ExternalJars";

	private static final String PROPERTIES_EXTERNAL_JARS_LOAD = "Start.ExternalJars.Load";

	private static final String PROPERTIES_FILE = "properties.xml";

	private static final String PROPERTIES_GAME_FINDER_LOCATION = "Start.GameFinderLocation";

	private static final String PROPERTIES_GAME_TYPE = "Start.GameType";

	private static final String PROPERTIES_HOST = "Start.Host";

	private static final String PROPERTIES_NAME = "Start.Name";

	private static final String PROPERTIES_PLAYERS = "Start.Players";

	private static final String PROPERTIES_PORT = "Start.Port";

	static
	{
		defaultProperties.put(PROPERTIES_ADAPTERS, "org.rnd.jmagic.interfaceAdapters.AutomaticPassInterface|org.rnd.jmagic.interfaceAdapters.ShortcutInterface|org.rnd.jmagic.interfaceAdapters.ManaAbilitiesAdapter|org.rnd.jmagic.interfaceAdapters.YieldAdapter");
		defaultProperties.put(PROPERTIES_ADAPTER_STATE, "1111");
		defaultProperties.put(PROPERTIES_EXTERNAL_JARS, "");
		defaultProperties.put(PROPERTIES_EXTERNAL_JARS_LOAD, "");
		defaultProperties.put(PROPERTIES_CARD_IMAGE_LOCATION, "");
		defaultProperties.put(PROPERTIES_CARDS_PACKAGES, "org.rnd.jmagic.cards");
		defaultProperties.put(PROPERTIES_CARDS_PACKAGES_LOAD, "1");
		defaultProperties.put(PROPERTIES_DECK, "");
		defaultProperties.put(PROPERTIES_GAME_FINDER_LOCATION, "");
		defaultProperties.put(PROPERTIES_GAME_TYPE, "Standard");
		defaultProperties.put(PROPERTIES_HOST, "localhost");
		defaultProperties.put(PROPERTIES_NAME, "player");
		defaultProperties.put(PROPERTIES_PLAYERS, "2");
		defaultProperties.put(PROPERTIES_PORT, "4099");
	}

	private static String getDirectory()
	{
		String ret = System.getenv("HOME");
		if(null != ret)
			return ret + java.io.File.separator + ".jmagic";

		ret = System.getenv("APPDATA");
		if(null != ret)
			return ret + java.io.File.separator + "jMagic";

		return null;
	}

	private static void initializeLogging(String directory)
	{
		// This will create the directory if it doesn't exist, and the
		// properties file is stored to the same directory, so don't worry about
		// creating it later
		java.io.File file = new java.io.File(directory);
		if(!file.exists() && !file.mkdirs())
			LOG.warning("Could not create directory to store log files in");

		java.io.File logFile = null;
		for(int i = 0; i < LOG_LIMIT; ++i)
		{
			java.io.File current = new java.io.File(directory + java.io.File.separator + LOG_FILE_PATTERN.replace("%d", Integer.toString(i)));
			if((null == logFile) || (current.lastModified() < logFile.lastModified()))
				logFile = current;
		}

		// Set up the logger to capture all events to a file
		try
		{
			java.util.logging.FileHandler handler = new java.util.logging.FileHandler(logFile.getAbsolutePath())
			{
				@Override
				public synchronized void publish(java.util.logging.LogRecord record)
				{
					super.publish(record);
					Throwable t = record.getThrown();
					if(null != t)
					{
						Throwable cause = t.getCause();
						if(null != cause)
						{
							record.setMessage("Throwable cause of " + t);
							record.setThrown(cause);
							this.publish(record);
						}
					}
				}
			};
			handler.setLevel(java.util.logging.Level.ALL);

			org.rnd.util.Logging.getRootLogger(LOG).addHandler(handler);
		}
		catch(java.io.IOException e)
		{
			LOG.log(java.util.logging.Level.WARNING, "IO error while opening the log file", e);
		}
	}

	public static void main(String args[])
	{
		String directory = getDirectory();
		if(null == directory)
		{
			javax.swing.SwingUtilities.invokeLater(new Runnable()
			{
				@Override
				public void run()
				{
					javax.swing.JOptionPane.showMessageDialog(null, "Could not determine a directory to store jMagic files to (no HOME or APPDATA environment variables set?)", "jMagic Error", javax.swing.JOptionPane.ERROR_MESSAGE);
				}
			});
		}
		else
			initializeLogging(directory);
		LOG.info("jMagic version " + new org.rnd.jmagic.Version() + " started");

		try
		{
			// Set the look-and-feel to the system look-and-feel if possible
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		}
		catch(ClassNotFoundException e)
		{
			LOG.log(java.util.logging.Level.WARNING, "Could not find system look-and-feel class", e);
		}
		catch(InstantiationException e)
		{
			LOG.log(java.util.logging.Level.WARNING, "Could not instantiate system look-and-feel class", e);
		}
		catch(IllegalAccessException e)
		{
			LOG.log(java.util.logging.Level.WARNING, "System look-and-feel class does not have a public instantiatior", e);
		}
		catch(javax.swing.UnsupportedLookAndFeelException e)
		{
			LOG.log(java.util.logging.Level.WARNING, "System look-and-feel isn't supported", e);
		}

		final SavedProperties properties;
		if(null == directory)
			properties = new SavedProperties();
		else
			properties = SavedProperties.createFromFile(directory + java.io.File.separator + PROPERTIES_FILE);

		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				Start start = new Start();
				start.setProperties(properties);
				org.rnd.util.Logging.addDialogHandler("jMagic", LOG, start.frame);
				start.frame.setVisible(true);
			}
		});
	}

	private javax.swing.JTextArea cardList;

	private java.util.Set<org.rnd.jmagic.engine.GameType> customGameTypes;

	private javax.swing.JTextField deckField;

	private java.io.File deckFile;

	private javax.swing.JFrame frame;

	private org.rnd.jmagic.engine.GameType gameType;

	private javax.swing.JComboBox<Object> gameTypeComboBox;

	private GameTypeDialog gameTypeDialog;

	private javax.swing.JTextField hostField;

	private javax.swing.JTextField nameField;

	private javax.swing.JTextField playersField;

	private javax.swing.JTextField portField;

	private SavedProperties properties;

	private Splash splash;

	private Start()
	{
		this.deckFile = null;
		this.gameType = null;

		this.frame = new javax.swing.JFrame("jMagic Start");

		this.gameTypeDialog = null;

		this.splash = new Splash(this.frame);

		this.nameField = new javax.swing.JTextField();

		this.portField = new javax.swing.JTextField();
		this.portField.setToolTipText("Default is 4099; set this to 0 only if hosting and all players should play locally (on the same screen)");

		this.deckField = new javax.swing.JTextField();
		this.deckField.setEnabled(false);

		javax.swing.JButton browseDeck = new javax.swing.JButton("Browse");
		browseDeck.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
				if(null != Start.this.deckFile)
					fileChooser.setCurrentDirectory(Start.this.deckFile);

				if(javax.swing.JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(Start.this.frame))
				{
					Start.this.deckFile = fileChooser.getSelectedFile();
					Start.this.deckField.setText(Start.this.deckFile.getName());
				}
			}
		});

		javax.swing.JMenuItem optionsMenuItem = new javax.swing.JMenuItem("Settings");
		optionsMenuItem.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				ConfigurationFrame options = new ConfigurationFrame(Start.this.properties);
				options.addOptionPanel(new StartOptions());
				options.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
				options.load();
				options.pack();
				options.setVisible(true);
			}
		});

		javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem("Exit");
		exitMenuItem.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				// Change the accelerator below if this code changes
				Start.this.frame.dispose();
			}
		});
		exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		javax.swing.JMenu startMenu = new javax.swing.JMenu("jMagic");
		startMenu.add(optionsMenuItem);
		startMenu.add(exitMenuItem);

		javax.swing.JMenuBar menuBar = new javax.swing.JMenuBar();
		menuBar.add(startMenu);
		this.frame.setJMenuBar(menuBar);

		this.hostField = new javax.swing.JTextField();

		javax.swing.JButton connectButton = new javax.swing.JButton("Connect");
		connectButton.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent event)
			{
				Start.this.connectFromEventThread(null, -1);
			}
		});

		javax.swing.JButton findGames = new javax.swing.JButton("Find Games");
		findGames.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent event)
			{
				try
				{
					String location = Start.this.properties.getProperty(PROPERTIES_GAME_FINDER_LOCATION);
					final GameFinderDialog finder = new GameFinderDialog(location, Start.this.frame);
					finder.setGameFinderExceptionListener(new GameFinderDialog.ExceptionListener<org.rnd.jmagic.comms.GameFinder.GameFinderException>()
					{
						@Override
						public void handleException(org.rnd.jmagic.comms.GameFinder.GameFinderException e)
						{
							LOG.warning(e.getMessage() + "; can't use game finder");
							finder.dispose();
						}
					});
					finder.setIOExceptionListener(new GameFinderDialog.ExceptionListener<java.io.IOException>()
					{
						@Override
						public void handleException(java.io.IOException e)
						{
							LOG.log(java.util.logging.Level.WARNING, "Error reading from game-finder; can't use game-finder", e);
							finder.dispose();
						}
					});
					finder.setJoinGameListener(new GameFinderDialog.JoinGameListener()
					{
						@Override
						public void joinGame(org.rnd.jmagic.comms.GameFinder.Game game)
						{
							Start.this.connectFromEventThread(game.IP, game.port);
							finder.dispose();
						}
					});

					// This will block until the dialog is invisible
					finder.setVisible(true);
				}
				catch(java.net.URISyntaxException e)
				{
					LOG.log(java.util.logging.Level.WARNING, "Can't understand game-finder URL; can't use game-finder", e);
				}
				catch(IllegalArgumentException e)
				{
					LOG.log(java.util.logging.Level.WARNING, "Game-finder URL is not absolute; can't use game-finder", e);
				}
				catch(java.net.MalformedURLException e)
				{
					LOG.log(java.util.logging.Level.WARNING, "Game-finder URL is malformed; can't use game-finder", e);
				}
				catch(java.io.IOException e)
				{
					LOG.log(java.util.logging.Level.WARNING, "Error reading from game-finder; can't use game-finder", e);
				}
				catch(org.rnd.jmagic.comms.GameFinder.GameFinderException e)
				{
					LOG.warning(e.getMessage());
				}
			}
		});

		javax.swing.JPanel connectPanel = new javax.swing.JPanel(new java.awt.GridBagLayout());

		java.awt.Insets insets = new java.awt.Insets(1, 1, 1, 1);

		{
			java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
			c.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
			c.fill = java.awt.GridBagConstraints.BOTH;
			c.gridx = 0;
			c.gridy = 0;
			c.insets = insets;
			c.ipadx = 10;
			c.weightx = 0;
			c.weighty = 0;
			connectPanel.add(new javax.swing.JLabel("Host"), c);
		}

		{
			java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
			c.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
			c.fill = java.awt.GridBagConstraints.BOTH;
			c.gridx = 1;
			c.gridy = 0;
			c.insets = insets;
			c.weightx = 1;
			c.weighty = 0;
			connectPanel.add(this.hostField, c);
		}

		{
			java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
			c.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
			c.fill = java.awt.GridBagConstraints.BOTH;
			c.gridx = 1;
			c.gridy = 1;
			c.insets = insets;
			c.weightx = 1;
			c.weighty = 0;
			connectPanel.add(connectButton, c);
		}

		{
			java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
			c.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
			c.fill = java.awt.GridBagConstraints.BOTH;
			c.gridx = 1;
			c.gridy = 2;
			c.insets = insets;
			c.weightx = 1;
			c.weighty = 0;
			connectPanel.add(findGames, c);
		}

		this.gameTypeComboBox = new javax.swing.JComboBox<Object>();
		for(org.rnd.jmagic.engine.GameType gameType: org.rnd.jmagic.engine.GameType.list())
			this.gameTypeComboBox.addItem(gameType);
		this.gameTypeComboBox.addItem(CUSTOM_GAME_TYPE_VALUE);

		final java.util.concurrent.atomic.AtomicReference<Object> lastSelectedItem = new java.util.concurrent.atomic.AtomicReference<Object>();
		this.gameTypeComboBox.addItemListener(new java.awt.event.ItemListener()
		{
			@Override
			public void itemStateChanged(java.awt.event.ItemEvent e)
			{
				if(java.awt.event.ItemEvent.DESELECTED == e.getStateChange())
					lastSelectedItem.set(e.getItem());
				// Must be SELECTED otherwise
				else if(e.getItem().equals(CUSTOM_GAME_TYPE_VALUE))
				{
					// Since showing a modal dialog blocks the current thread
					// until it is hidden, do so later so the rest of the
					// drop-down events can be processed (like hiding the
					// drop-down list)
					java.awt.EventQueue.invokeLater(new Runnable()
					{
						@Override
						public void run()
						{
							if(null == Start.this.gameTypeDialog)
							{
								java.util.Set<org.rnd.jmagic.engine.GameType> presets = new java.util.HashSet<org.rnd.jmagic.engine.GameType>();
								for(org.rnd.jmagic.engine.GameType t: org.rnd.jmagic.engine.GameType.list())
									presets.add(t);
								presets.addAll(Start.this.customGameTypes);
								Start.this.gameTypeDialog = new GameTypeDialog(Start.this.frame, presets);
							}

							Start.this.gameTypeDialog.setVisible(true);
							org.rnd.jmagic.engine.GameType custom = Start.this.gameTypeDialog.getGameType();
							if(null == custom)
								Start.this.gameTypeComboBox.setSelectedItem(lastSelectedItem.get());
							else
							{
								Start.this.gameType = custom;
								if(!custom.getName().equals(CUSTOM_GAME_TYPE_VALUE))
								{
									Start.this.customGameTypes.add(custom);
									Start.this.gameTypeComboBox.addItem(custom);
									Start.this.gameTypeComboBox.setSelectedItem(custom);
								}
							}
						}
					});
				}
				else
					Start.this.gameType = (org.rnd.jmagic.engine.GameType)(e.getItem());
			}
		});

		this.playersField = new javax.swing.JTextField();

		this.cardList = new javax.swing.JTextArea(30, 40);
		this.cardList.setEditable(false);
		this.cardList.setText("Loading cards...");

		javax.swing.JButton hostButton = new javax.swing.JButton("Host");
		hostButton.addActionListener(new ServerHost(false));

		javax.swing.JButton hostWithGameFinderButton = new javax.swing.JButton("Host Publicly");
		hostWithGameFinderButton.setToolTipText("Use the game-finding server (location specified in jMagic->Settings)");
		hostWithGameFinderButton.addActionListener(new ServerHost(true));

		javax.swing.JButton cardsButton = new javax.swing.JButton("Supported Cards");
		cardsButton.addActionListener(new java.awt.event.ActionListener()
		{
			private java.util.List<Integer> indexes;
			private int lastIndex = -1;
			private java.util.regex.Matcher search = null;
			private javax.swing.JTextField searchTextField = null;

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(Start.this.cardList, javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

				this.searchTextField = new javax.swing.JTextField("Type a string and press enter to search");
				this.searchTextField.addActionListener(new java.awt.event.ActionListener()
				{
					@Override
					public void actionPerformed(java.awt.event.ActionEvent e)
					{
						selectNext();
					}
				});
				this.searchTextField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener()
				{
					@Override
					public void changedUpdate(javax.swing.event.DocumentEvent e)
					{
						// Simple-text fields don't call this function
					}

					@Override
					public void insertUpdate(javax.swing.event.DocumentEvent e)
					{
						updateMatcher();
					}

					@Override
					public void removeUpdate(javax.swing.event.DocumentEvent e)
					{
						updateMatcher();
					}
				});

				javax.swing.JDialog cardsDialog = new javax.swing.JDialog(Start.this.frame, "Cards supported while hosting", true);
				cardsDialog.add(scrollPane, java.awt.BorderLayout.CENTER);
				cardsDialog.add(this.searchTextField, java.awt.BorderLayout.PAGE_END);
				cardsDialog.pack();
				cardsDialog.setVisible(true);
			}

			private void selectNext()
			{
				if(0 == this.indexes.size())
					return;

				this.lastIndex = (this.lastIndex + 1) % this.indexes.size();
				Start.this.cardList.setCaretPosition(this.indexes.get(this.lastIndex));
			}

			private void updateMatcher()
			{
				javax.swing.text.Highlighter highlighter = Start.this.cardList.getHighlighter();
				highlighter.removeAllHighlights();

				this.indexes = new java.util.ArrayList<Integer>();
				this.search = java.util.regex.Pattern.compile(this.searchTextField.getText(), java.util.regex.Pattern.CASE_INSENSITIVE).matcher(Start.this.cardList.getText());
				try
				{
					while(this.search.find())
					{
						this.indexes.add(this.search.start());
						highlighter.addHighlight(this.search.start(), this.search.end(), javax.swing.text.DefaultHighlighter.DefaultPainter);
					}
				}
				catch(javax.swing.text.BadLocationException e)
				{
					LOG.log(java.util.logging.Level.WARNING, "Invalid range for highlighting cards to search", e);
				}

				this.lastIndex = this.indexes.size() - 1;
				selectNext();
			}
		});
		cardsButton.setToolTipText("View all the cards that would be supported if you were hosting");

		javax.swing.JPanel hostPanel = new javax.swing.JPanel(new java.awt.GridBagLayout());

		{
			java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
			c.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
			c.fill = java.awt.GridBagConstraints.BOTH;
			c.gridx = 0;
			c.gridy = 0;
			c.insets = insets;
			c.ipadx = 10;
			c.weightx = 0;
			c.weighty = 0;
			hostPanel.add(new javax.swing.JLabel("Game type"), c);
		}

		{
			java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
			c.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
			c.fill = java.awt.GridBagConstraints.BOTH;
			c.gridx = 1;
			c.gridy = 0;
			c.insets = insets;
			c.weightx = 1;
			c.weighty = 0;
			hostPanel.add(this.gameTypeComboBox, c);
		}
		/*
		{
			java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
			c.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
			c.fill = java.awt.GridBagConstraints.BOTH;
			c.gridx = 0;
			c.gridy = 1;
			c.insets = insets;
			c.ipadx = 10;
			c.weightx = 0;
			c.weighty = 0;
			hostPanel.add(new javax.swing.JLabel("Players"), c);
		}

		{
			java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
			c.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
			c.fill = java.awt.GridBagConstraints.BOTH;
			c.gridx = 1;
			c.gridy = 1;
			c.insets = insets;
			c.weightx = 1;
			c.weighty = 0;
			hostPanel.add(this.playersField, c);
		}
		*/
		{
			java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
			c.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
			c.fill = java.awt.GridBagConstraints.BOTH;
			c.gridx = 1;
			c.gridy = 2;
			c.insets = insets;
			c.weightx = 1;
			c.weighty = 0;
			hostPanel.add(hostButton, c);
		}

		{
			java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
			c.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
			c.fill = java.awt.GridBagConstraints.BOTH;
			c.gridx = 1;
			c.gridy = 3;
			c.insets = insets;
			c.weightx = 1;
			c.weighty = 0;
			hostPanel.add(hostWithGameFinderButton, c);
		}

		{
			java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
			c.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
			c.fill = java.awt.GridBagConstraints.BOTH;
			c.gridx = 1;
			c.gridy = 4;
			c.insets = insets;
			c.weightx = 1;
			c.weighty = 0;
			hostPanel.add(cardsButton, c);
		}

		javax.swing.JTabbedPane hostOrConnect = new javax.swing.JTabbedPane();
		hostOrConnect.addTab("Connect", connectPanel);
		hostOrConnect.addTab("Host", hostPanel);

		this.frame.addWindowListener(new java.awt.event.WindowAdapter()
		{
			@Override
			public void windowClosed(java.awt.event.WindowEvent e)
			{
				Start.this.splash.dispose();
			}
		});
		this.frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.frame.setLayout(new java.awt.GridBagLayout());
		this.frame.setResizable(false);

		{
			java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
			c.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
			c.fill = java.awt.GridBagConstraints.BOTH;
			c.gridx = 0;
			c.gridy = 0;
			c.insets = insets;
			c.ipadx = 10;
			c.weightx = 0;
			c.weighty = 0;
			this.frame.add(new javax.swing.JLabel("Name"), c);
		}

		{
			java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
			c.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
			c.fill = java.awt.GridBagConstraints.BOTH;
			c.gridx = 1;
			c.gridy = 0;
			c.insets = insets;
			c.weightx = 1;
			c.weighty = 0;
			this.frame.add(this.nameField, c);
		}

		{
			java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
			c.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
			c.fill = java.awt.GridBagConstraints.BOTH;
			c.gridx = 0;
			c.gridy = 1;
			c.insets = insets;
			c.ipadx = 10;
			c.weightx = 0;
			c.weighty = 0;
			this.frame.add(new javax.swing.JLabel("Port"), c);
		}

		{
			java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
			c.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
			c.fill = java.awt.GridBagConstraints.BOTH;
			c.gridx = 1;
			c.gridy = 1;
			c.insets = insets;
			c.weightx = 1;
			c.weighty = 0;
			this.frame.add(this.portField, c);
		}

		{
			java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
			c.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
			c.fill = java.awt.GridBagConstraints.BOTH;
			c.gridx = 0;
			c.gridy = 2;
			c.insets = insets;
			c.ipadx = 10;
			c.weightx = 0;
			c.weighty = 0;
			this.frame.add(new javax.swing.JLabel("Deck"), c);
		}

		{
			java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
			c.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
			c.fill = java.awt.GridBagConstraints.BOTH;
			c.gridx = 1;
			c.gridy = 2;
			c.insets = insets;
			c.weightx = 1;
			c.weighty = 0;
			this.frame.add(this.deckField, c);
		}

		{
			java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
			c.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
			c.fill = java.awt.GridBagConstraints.BOTH;
			c.gridx = 1;
			c.gridy = 3;
			c.insets = insets;
			c.weightx = 1;
			c.weighty = 0;
			this.frame.add(browseDeck, c);
		}

		{
			java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
			c.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
			c.fill = java.awt.GridBagConstraints.BOTH;
			c.gridwidth = 2;
			c.gridx = 0;
			c.gridy = 4;
			c.insets = insets;
			c.weightx = 1;
			c.weighty = 1;
			this.frame.add(hostOrConnect, c);
		}

		this.frame.pack();
	}

	private void connectFromEventThread(String host, int port)
	{
		final String newHost;
		if(null == host)
		{
			newHost = Start.this.hostField.getText();
			if(0 == newHost.length())
			{
				LOG.severe("Host must be specified");
				return;
			}
		}
		else
			newHost = host;

		final int newPort;
		if(-1 == port)
		{
			try
			{
				newPort = Integer.parseInt(Start.this.portField.getText());
				if((newPort < 0) || (65535 < newPort))
				{
					LOG.severe("Port must be between 0 and 65535");
					return;
				}
			}
			catch(NumberFormatException e)
			{
				LOG.log(java.util.logging.Level.SEVERE, "Port must be an integer", e);
				return;
			}
		}
		else
			newPort = port;

		final String name = Start.this.nameField.getText();
		if(0 == name.length())
		{
			LOG.warning("Name must be specified");
			return;
		}

		LOG.fine("Starting connect thread");
		final Thread game = new Thread("Connect")
		{
			@Override
			public void run()
			{
				org.rnd.jmagic.engine.Deck deck = getDeck();
				// The errors are logged elsewhere, so don't say anything
				if(null == deck)
					return;

				try
				{
					SwingAdapter swing = new SwingAdapter(deck, name);
					org.rnd.jmagic.comms.Client client = new org.rnd.jmagic.comms.Client(newHost, newPort, name, createLocal(swing), swing.getChatCallback());
					swing.setMessagePoster(client.getMessagePoster());

					client.run();
					hideSplash();
				}
				catch(java.io.IOException e)
				{
					LOG.log(java.util.logging.Level.SEVERE, "IOException while constructing client", e);
				}
				catch(CreateLocalException e)
				{
					// The error has already been logged; just quit
					return;
				}
			}
		};
		game.start();
		saveProperties();

		Start.this.splash.addWindowListener(new java.awt.event.WindowAdapter()
		{
			@Override
			public void windowClosing(java.awt.event.WindowEvent e)
			{
				int option = javax.swing.JOptionPane.showConfirmDialog(Start.this.splash, "Are you sure you want to cancel connecting to the host?", "jMagic Interrupt", javax.swing.JOptionPane.YES_NO_OPTION);
				if(javax.swing.JOptionPane.YES_OPTION == option)
				{
					LOG.fine("Interrupting the connect thread");
					game.interrupt();
				}
			}
		});

		// This will block until the splash dialog is invisible
		Start.this.splash.setVisible(true);
	}

	private org.rnd.jmagic.interfaceAdapters.ConfigurableInterface createLocal(SwingAdapter swing) throws CreateLocalException
	{
		org.rnd.jmagic.interfaceAdapters.ConfigurableInterface local = new org.rnd.jmagic.interfaceAdapters.ConfigurableInterfaceDecorator(swing, new org.rnd.jmagic.interfaceAdapters.SimplePlayerInterface(swing)
		{
			@Override
			public void alertError(ErrorParameters parameters)
			{
				if(!this.disposed)
				{
					String message;
					if(parameters instanceof org.rnd.jmagic.engine.PlayerInterface.ErrorParameters.CardLoadingError)
						message = "The following cards weren't loaded properly:\n" + org.rnd.util.SeparatedList.get("\n", "", ((org.rnd.jmagic.engine.PlayerInterface.ErrorParameters.CardLoadingError)parameters).cardNames);
					else if(parameters instanceof org.rnd.jmagic.engine.PlayerInterface.ErrorParameters.HostError)
						message = "The host has encountered an error. The current game will no longer continue.";
					else if(parameters instanceof org.rnd.jmagic.engine.PlayerInterface.ErrorParameters.IllegalCardsError)
						message = "The following cards aren't legal in a deck list: " + org.rnd.util.SeparatedList.get("and", ((org.rnd.jmagic.engine.PlayerInterface.ErrorParameters.IllegalCardsError)parameters).cardNames);
					else if(parameters instanceof org.rnd.jmagic.engine.PlayerInterface.ErrorParameters.DeckCheckError)
						message = "The following deck check failed: " + ((org.rnd.jmagic.engine.PlayerInterface.ErrorParameters.DeckCheckError)parameters).rule;
					else if(parameters instanceof org.rnd.jmagic.engine.PlayerInterface.ErrorParameters.CardCheckError)
						message = "The following card check failed: " + ((org.rnd.jmagic.engine.PlayerInterface.ErrorParameters.CardCheckError)parameters).card;
					else
						message = "An unknown error occurred in the host.";
					javax.swing.JOptionPane.showMessageDialog(Start.this.splash, message, "jMagic Error", javax.swing.JOptionPane.ERROR_MESSAGE);
					Start.this.hideSplash();
				}

				super.alertError(parameters);
			}

			@Override
			public void alertState(org.rnd.jmagic.sanitized.SanitizedGameState sanitizedGameState)
			{
				if(!this.disposed)
				{
					// When this occurs for the first time, assume the GUI is
					// about to pop up, so dispose of the Start frame
					java.awt.EventQueue.invokeLater(new Runnable()
					{
						@Override
						public void run()
						{
							Start.this.frame.dispose();
						}
					});

					this.disposed = true;
				}

				super.alertState(sanitizedGameState);
			}

			private boolean disposed = false;
		});
		local = new org.rnd.jmagic.interfaceAdapters.ConfigurableInterfaceDecorator(local, new org.rnd.jmagic.interfaceAdapters.MulticostAdapter(local));
		local = new org.rnd.jmagic.interfaceAdapters.ConfigurableInterfaceDecorator(local, new org.rnd.jmagic.interfaceAdapters.CountersAcrossCreaturesInterface(local));

		String[] adapters = this.properties.getProperty(PROPERTIES_ADAPTERS).split("[|]");
		char[] adapterStates = this.properties.getProperty(PROPERTIES_ADAPTER_STATE).toCharArray();

		for(int i = 0; i < adapters.length; ++i)
		{
			if(adapterStates[i] != '1')
				continue;

			try
			{
				Class<?> clazz = Class.forName(adapters[i]);
				if(org.rnd.jmagic.interfaceAdapters.ConfigurableInterface.class.isAssignableFrom(clazz))
				{
					local = (org.rnd.jmagic.interfaceAdapters.ConfigurableInterface)clazz.getConstructor(org.rnd.jmagic.interfaceAdapters.ConfigurableInterface.class).newInstance(local);
				}
				else if(org.rnd.jmagic.engine.PlayerInterface.class.isAssignableFrom(clazz))
				{
					org.rnd.jmagic.engine.PlayerInterface pi = (org.rnd.jmagic.engine.PlayerInterface)clazz.getConstructor(org.rnd.jmagic.engine.PlayerInterface.class).newInstance(local);
					local = new org.rnd.jmagic.interfaceAdapters.ConfigurableInterfaceDecorator(local, pi);
				}
				else
				{
					LOG.severe("Non-InterfaceAdapter class specified.");
					throw new CreateLocalException();
				}
			}
			catch(ClassNotFoundException e)
			{
				LOG.log(java.util.logging.Level.SEVERE, "Could not find class " + adapters[i], e);
				throw new CreateLocalException(e);
			}
			catch(NoSuchMethodException e)
			{
				LOG.log(java.util.logging.Level.SEVERE, adapters[i] + " does not define a useful constructor", e);
				throw new CreateLocalException(e);
			}
			catch(IllegalAccessException e)
			{
				LOG.log(java.util.logging.Level.SEVERE, adapters[i] + "'s InterfaceAdapter constructor isn't public", e);
				throw new CreateLocalException(e);
			}
			catch(InstantiationException e)
			{
				LOG.log(java.util.logging.Level.SEVERE, adapters[i] + " is not a concrete class for an instance to be created", e);
				throw new CreateLocalException(e);
			}
			catch(java.lang.reflect.InvocationTargetException e)
			{
				LOG.log(java.util.logging.Level.SEVERE, "An exception was thrown when calling " + adapters[i] + "'s InterfaceAdapter constructor", e);
				throw new CreateLocalException(e);
			}
		}

		local.setProperties(this.properties);
		return local;
	}

	private org.rnd.jmagic.engine.Deck getDeck()
	{
		if(null == this.deckFile)
		{
			LOG.severe("Null deck file!  Something is very wrong.");
			return null;
		}

		if(this.deckFile.getPath().isEmpty())
		{
			return new org.rnd.jmagic.engine.Deck();
		}

		if(!this.deckFile.canRead())
		{
			LOG.severe("Can't read deck file");
			return null;
		}

		try
		{
			return org.rnd.jmagic.CardLoader.getDeck(new java.io.BufferedReader(new java.io.FileReader(this.deckFile.getAbsolutePath())));
		}
		catch(java.io.IOException e)
		{
			LOG.log(java.util.logging.Level.SEVERE, "Error reading deck from file; see the log for details", e);
			return null;
		}
	}

	private void hideSplash()
	{
		java.awt.EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				// Get rid of the window listeners host/connect added
				for(java.awt.event.WindowListener l: Start.this.splash.getWindowListeners())
					Start.this.splash.removeWindowListener(l);
				Start.this.splash.setVisible(false);
			}
		});
		Start.this.splash.clear();
	}

	private void saveProperties()
	{
		this.properties.setProperty(PROPERTIES_DECK, this.deckFile.getAbsolutePath());
		this.properties.put(PROPERTIES_CUSTOM_GAME_TYPES, this.customGameTypes);
		// TODO: This is a hack; we don't want the custom game-type dialog
		// popping up immediately so don't save it if it isn't in the list of
		// defaults
		if(!this.gameType.getName().equals(CUSTOM_GAME_TYPE_VALUE))
			this.properties.setProperty(PROPERTIES_GAME_TYPE, this.gameType.toString());
		this.properties.setProperty(PROPERTIES_HOST, this.hostField.getText());
		this.properties.setProperty(PROPERTIES_NAME, this.nameField.getText());
		this.properties.setProperty(PROPERTIES_PLAYERS, this.playersField.getText());
		this.properties.setProperty(PROPERTIES_PORT, this.portField.getText());
	}

	private boolean setGameType(String gameTypeName)
	{
		for(int i = 0; i < this.gameTypeComboBox.getItemCount(); ++i)
		{
			Object item = this.gameTypeComboBox.getItemAt(i);
			if(item.toString().equals(gameTypeName))
			{
				this.gameType = (org.rnd.jmagic.engine.GameType)item;
				this.gameTypeComboBox.setSelectedIndex(i);
				return true;
			}
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	public void setProperties(final SavedProperties properties)
	{
		this.properties = properties;

		for(java.util.Map.Entry<String, String> entry: defaultProperties.entrySet())
			if(!this.properties.containsKey(entry.getKey()))
				this.properties.setProperty(entry.getKey(), entry.getValue());

		this.portField.setText(this.properties.getProperty(PROPERTIES_PORT));
		this.deckFile = new java.io.File(this.properties.getProperty(PROPERTIES_DECK));
		this.deckField.setText(this.deckFile.getName());

		this.customGameTypes = (java.util.Set<org.rnd.jmagic.engine.GameType>)this.properties.get(PROPERTIES_CUSTOM_GAME_TYPES);
		if(null == this.customGameTypes)
			this.customGameTypes = new java.util.HashSet<org.rnd.jmagic.engine.GameType>();
		else
			for(org.rnd.jmagic.engine.GameType t: this.customGameTypes)
				this.gameTypeComboBox.addItem(t);

		String gameTypeName = this.properties.getProperty(PROPERTIES_GAME_TYPE);
		if(!this.setGameType(gameTypeName))
		{
			LOG.warning("Value " + gameTypeName + " of property " + PROPERTIES_GAME_TYPE + " isn't a GameType; setting it to the default");
			String defaultGameTypeName = defaultProperties.get(PROPERTIES_GAME_TYPE);
			if(!this.setGameType(defaultGameTypeName))
				LOG.severe("Default value " + defaultGameTypeName + " of property " + PROPERTIES_GAME_TYPE + " isn't a GameType! HALP!");
		}

		this.hostField.setText(this.properties.getProperty(PROPERTIES_HOST));
		this.nameField.setText(this.properties.getProperty(PROPERTIES_NAME));
		this.playersField.setText(this.properties.getProperty(PROPERTIES_PLAYERS));

		String cardImageLocation = properties.getProperty(PROPERTIES_CARD_IMAGE_LOCATION);
		if(cardImageLocation == null)
			properties.setProperty(PROPERTIES_CARD_IMAGE_LOCATION, "");
		else if(0 != cardImageLocation.length())
			CardGraphics.setCardImageLocation(cardImageLocation);
	}
}
