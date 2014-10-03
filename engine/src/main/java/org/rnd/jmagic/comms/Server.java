package org.rnd.jmagic.comms;

/**
 * <p>
 * A reference implementation for playing jMagic over a socket. After
 * constructing the object, call
 * {@link #addLocalPlayer(org.rnd.jmagic.engine.PlayerInterface, org.rnd.jmagic.comms.ChatManager.Callback)}
 * for each player which will be playing within the same VM as the server.
 * </p>
 * <p>
 * For players connecting via socket, all communication is via serialized
 * objects created by {@link java.io.ObjectOutputStream} as follows:
 * </p>
 * <ol>
 * <li>Write the serialization stream header by calling the constructor
 * {@link java.io.ObjectOutputStream#ObjectOutputStream(java.io.OutputStream)}</li>
 * <li>Write a {@link org.rnd.jmagic.Version} representing the version of the
 * client using {@link java.io.ObjectOutputStream#writeObject(Object)}</li>
 * <li>Write a {@link Server.SocketType} representing the type of socket using
 * {@link java.io.ObjectOutputStream#writeObject(Object)}</li>
 * <li>Flush the connection to make sure the server receives it by calling
 * {@link java.io.ObjectOutputStream#flush()}</li>
 * <li>Read a {@link org.rnd.jmagic.Version} using
 * {@link java.io.ObjectInputStream#readObject()} to verify a version match or
 * mismatch</li>
 * </ol>
 */
public class Server implements Runnable
{
	/**
	 * These are the only socket types recognized by {@link Server}.
	 */
	public static enum SocketType
	{
		/**
		 * This must be the first socket connected. After verifying version
		 * compatibility, the client must read a {@link java.util.UUID} using
		 * {@link java.io.ObjectInputStream#readObject()} which serves as a key
		 * for any other sockets. All traffic afterwards must be handled using
		 * {@link StreamPlayer}.
		 */
		ENGINE,
		/**
		 * This must only be connected after receiving a key from the
		 * {@link #ENGINE} socket. Immediately after writing {@link #CHAT} in
		 * the protocol described by {@link Server}, the client must write the
		 * key to the socket using
		 * {@link java.io.ObjectOutputStream#writeObject(Object)}. After
		 * receiving a {@link org.rnd.jmagic.Version} from the server, the
		 * client must continually call
		 * {@link java.io.ObjectInputStream#readUTF()} to receive chat messages
		 * from all players (including the player connected by this socket)
		 * prefixed by the name of that player.
		 */
		CHAT
	}

	private static final int HEARTBEAT_MILLISECONDS = 15000;

	private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(Server.class.getName());

	private org.rnd.util.ChannelRouter channelRouter;

	private java.util.List<java.nio.channels.Channel> channels;

	private ChatManager chatManager;

	private java.util.List<Thread> chatThreads;

	private Thread connectionThread = null;

	private org.rnd.jmagic.engine.Game game;

	private String gameDescription = null;

	private GameFinder gameFinder = null;

	private org.rnd.jmagic.engine.GameType gameType;

	private java.util.Map<java.net.InetAddress, org.bitlet.weupnp.GatewayDevice> gateways;

	private String hostPlayerName = null;

	private java.util.concurrent.ConcurrentMap<java.util.UUID, org.rnd.jmagic.engine.Player> players;

	private int playerLimit;

	private int port;

	private boolean readyToRun = true;

	private java.nio.channels.Selector selector = null;

	private java.nio.channels.ServerSocketChannel serverChannel = null;

	private org.rnd.jmagic.Version version;

	public Server(org.rnd.jmagic.engine.GameType gameType, int numPlayers, int port)
	{
		try
		{
			this.channelRouter = new org.rnd.util.ChannelRouter(new org.rnd.util.ExceptionListener<java.io.IOException>()
			{
				@Override
				public void exceptionThrown(java.io.IOException exception)
				{
					LOG.log(java.util.logging.Level.SEVERE, "IO error while routing across channels", exception);
				}
			});
		}
		catch(java.io.IOException e)
		{
			LOG.log(java.util.logging.Level.SEVERE, "IO error while setting up channel router", e);
		}

		this.channels = new java.util.LinkedList<java.nio.channels.Channel>();

		this.chatManager = new ChatManager();

		this.chatThreads = new java.util.LinkedList<Thread>();

		this.game = new org.rnd.jmagic.engine.Game(gameType);

		if(null == gameType)
		{
			LOG.severe("Must provide a game type");
			this.readyToRun = false;
		}
		this.gameType = gameType;

		if(numPlayers < 1)
		{
			LOG.severe("Number of players must be greater than 0");
			this.readyToRun = false;
		}
		this.playerLimit = numPlayers;

		this.players = new java.util.concurrent.ConcurrentHashMap<java.util.UUID, org.rnd.jmagic.engine.Player>();

		if((port < 0) || (65535 < port))
		{
			LOG.severe("Port must be between 0 and 65535");
			this.readyToRun = false;
		}
		this.port = port;

		this.version = new org.rnd.jmagic.Version();
	}

	private boolean acceptConnections()
	{
		final Object connectionLock = new Object();
		final java.util.concurrent.atomic.AtomicBoolean error = new java.util.concurrent.atomic.AtomicBoolean(false);

		this.connectionThread = new Thread("AcceptConnections")
		{
			@Override
			public void run()
			{
				while(true)
				{
					int numKeys = 0;
					try
					{
						numKeys = Server.this.selector.select(HEARTBEAT_MILLISECONDS);
					}
					catch(java.nio.channels.ClosedSelectorException e)
					{
						// The main thread is telling us to stop, so gracefully
						// exit
						return;
					}
					catch(java.io.IOException e)
					{
						error.set(true);
						synchronized(connectionLock)
						{
							connectionLock.notify();
						}
						LOG.log(java.util.logging.Level.WARNING, "IO error while selecting", e);
						return;
					}

					if(0 < numKeys)
					{
						Server.this.selector.selectedKeys().clear();
						acceptOneConnection();
						synchronized(connectionLock)
						{
							connectionLock.notify();
						}
					}
					else if(Thread.currentThread().isInterrupted())
					{
						// We can only be interrupted by the server thread, so
						// no need to set the error flag
						LOG.fine("Interrupting listening for clients due to user interrupt");
						return;
					}
					else
					{
						LOG.fine("Heartbeat period timeout; sending heartbeat");
						heartbeatGameFinder();
					}
				}
			}
		};

		try
		{
			synchronized(connectionLock)
			{
				this.connectionThread.start();
				while(this.players.size() < this.playerLimit)
				{
					connectionLock.wait(500);
					if(error.get())
						return false;
				}
				return true;
			}
		}
		catch(InterruptedException e)
		{
			this.connectionThread.interrupt();
		}

		return false;
	}

	private void acceptOneConnection()
	{
		try
		{
			LOG.info("Accepting a connection");
			java.nio.channels.SocketChannel channel = this.serverChannel.accept();
			channel.configureBlocking(false);
			this.channels.add(channel);

			// Always create the output stream first and flush it before
			// creating the input stream so a stream header is immediately
			// available
			java.io.ObjectOutputStream out = this.channelRouter.addRouteFromObjectOutputStream(channel);
			out.writeObject(this.version);
			out.flush();

			java.io.ObjectInputStream in = this.channelRouter.addRouteToObjectInputStream(channel);

			// Read a Version object from the other side to see if we're
			// compatible
			org.rnd.jmagic.Version otherVersion = (org.rnd.jmagic.Version)in.readObject();
			if(!this.version.isCompatibleWith(otherVersion))
			{
				LOG.log(java.util.logging.Level.INFO, "The player attempted to connect with a non-compatible version (local: " + this.version + ", remote: " + otherVersion + "); rejecting player");
				return;
			}

			SocketType header = (SocketType)(in.readObject());

			switch(header)
			{
			case ENGINE:
				if(this.players.size() == this.playerLimit)
				{
					LOG.info("Too many players are trying to connect");
					channel.close();
					return;
				}

				// Generate a random key for the player to use for future
				// connections
				java.util.UUID newKey = java.util.UUID.randomUUID();

				out.writeObject(newKey);
				out.flush();

				org.rnd.jmagic.engine.PlayerInterface playerInterface = new StreamPlayer(in, out);
				org.rnd.jmagic.engine.Player player = this.game.addInterface(playerInterface);
				if(null == player)
				{
					LOG.info("Player " + playerInterface.getName() + " deck error; rejecting player");
					channel.close();
					return;
				}

				this.channels.add(channel);
				this.players.put(newKey, player);

				LOG.info("Player " + player.getName() + " connected with key " + newKey);
				updateGameFinder();
				break;

			case CHAT:
				java.util.UUID oldKey = (java.util.UUID)(in.readObject());
				if(!this.players.containsKey(oldKey))
				{
					LOG.info("Player with key " + oldKey + " does not exist in players map");
					channel.close();
					return;
				}

				final String playerName = this.players.get(oldKey).getName();
				this.channels.add(channel);

				StreamChatterServer chatter = new StreamChatterServer(in, out, new org.rnd.util.ExceptionListener<java.io.IOException>()
				{
					@Override
					public void exceptionThrown(java.io.IOException exception)
					{
						LOG.log(java.util.logging.Level.WARNING, "IO error for player " + playerName, exception);
					}
				});
				chatter.setMessagePoster(this.chatManager.addClient(playerName, chatter));
				Thread chatThread = new Thread(chatter, "ChatInputFromSocketFor" + oldKey);
				this.chatThreads.add(chatThread);
				chatThread.start();
				break;
			}
		}
		catch(ClassCastException e)
		{
			LOG.log(java.util.logging.Level.INFO, "A connection attempt was made without following the protocol", e);
		}
		catch(ClassNotFoundException e)
		{
			LOG.log(java.util.logging.Level.INFO, "The player sent an unrecognized class; rejecting player", e);
		}
		catch(java.io.IOException e)
		{
			LOG.log(java.util.logging.Level.INFO, "An IO error occured during the player connecting; rejecting player", e);
		}
		catch(org.rnd.jmagic.engine.Game.InterruptedGameException e)
		{
			LOG.fine("Interrupting listening for clients due to user interrupt");
		}
	}

	public ChatManager.MessagePoster addLocalPlayer(org.rnd.jmagic.engine.PlayerInterface playerInterface, ChatManager.Callback chatCallback)
	{
		org.rnd.jmagic.engine.Player player = this.game.addInterface(playerInterface);
		if(null == player)
		{
			// Don't log anything as any errors are passed to
			// playerInterface.alertError which is logged elsewhere
			return null;
		}

		String playerName = player.getName();
		if(null == this.hostPlayerName)
			this.hostPlayerName = playerName;
		// Generate a fake key for the local player
		this.players.put(java.util.UUID.randomUUID(), player);
		return this.chatManager.addClient(playerName, chatCallback);
	}

	private void alertHostError()
	{
		try
		{
			for(org.rnd.jmagic.engine.Player p: this.game.actualState.players)
				p.comm.alertError(new org.rnd.jmagic.engine.PlayerInterface.ErrorParameters.HostError());
		}
		// We have to catch RuntimeException here because alertError has no
		// other way to indicate an error
		catch(RuntimeException e)
		{
			LOG.log(java.util.logging.Level.SEVERE, "Error while alerting players to the game error", e);
		}
	}

	private void cancelGameFinder()
	{
		try
		{
			if(null != this.gameFinder)
			{
				LOG.info("Cancelling game from game-finder");
				this.gameFinder.cancel();
			}
		}
		catch(java.io.IOException e)
		{
			LOG.log(java.util.logging.Level.WARNING, "Error cancelling game on game-finder; state of game might not be accurate on game-finder", e);
		}
	}

	private void closeConnections()
	{
		this.connectionThread.interrupt();

		for(Thread t: this.chatThreads)
			t.interrupt();

		try
		{
			this.serverChannel.close();
			this.selector.close();
		}
		catch(java.io.IOException e)
		{
			LOG.log(java.util.logging.Level.WARNING, "IO error while cleaning up", e);
		}

		for(java.nio.channels.Channel c: this.channels)
		{
			try
			{
				c.close();
			}
			catch(java.io.IOException e)
			{
				LOG.log(java.util.logging.Level.WARNING, "IO error while closing connection", e);
			}
		}
	}

	private void forwardUPNP()
	{
		try
		{
			String description = "jMagic on port " + this.port;
			this.gateways = new java.util.HashMap<java.net.InetAddress, org.bitlet.weupnp.GatewayDevice>();

			org.bitlet.weupnp.GatewayDiscover discovery = new org.bitlet.weupnp.GatewayDiscover();
			for(java.util.Map.Entry<java.net.InetAddress, org.bitlet.weupnp.GatewayDevice> entry: discovery.discover().entrySet())
			{
				java.net.InetAddress address = entry.getKey();
				org.bitlet.weupnp.GatewayDevice gateway = entry.getValue();
				org.bitlet.weupnp.PortMappingEntry mapping = new org.bitlet.weupnp.PortMappingEntry();
				if(gateway.getSpecificPortMappingEntry(this.port, "TCP", mapping))
					LOG.warning("Port forwarding on address " + address + " already exists; port forwarding not set up");
				else if(gateway.addPortMapping(this.port, this.port, address.getHostAddress(), "TCP", description))
				{
					this.gateways.put(address, gateway);
					LOG.info("Set up port forwarding on address " + address.getHostAddress());
				}
				else
					LOG.warning("Could not set up port forwarding on address " + address.getHostAddress());
			}
		}
		catch(javax.xml.parsers.ParserConfigurationException | org.xml.sax.SAXException | java.io.IOException e)
		{
			LOG.log(java.util.logging.Level.WARNING, "Error when trying to set up port forwarding", e);
		}
	}

	private void heartbeatGameFinder()
	{
		try
		{
			if(null != this.gameFinder)
				this.gameFinder.heartbeat();
		}
		catch(java.io.IOException e)
		{
			LOG.log(java.util.logging.Level.WARNING, "Error heartbeating game on game-finder; game may be removed from game-finder", e);
		}
	}

	private boolean listenForConnections()
	{
		try
		{
			this.serverChannel = java.nio.channels.ServerSocketChannel.open();
			this.serverChannel.configureBlocking(false);
			this.serverChannel.socket().bind(new java.net.InetSocketAddress(this.port));
			LOG.info("Listening for connections on port " + this.port);

			this.selector = java.nio.channels.Selector.open();
			this.serverChannel.register(this.selector, java.nio.channels.SelectionKey.OP_ACCEPT);

			return true;
		}
		catch(java.io.IOException e)
		{
			LOG.log(java.util.logging.Level.SEVERE, "Could not open port to listen for connections", e);
			return false;
		}
	}

	private void registerWithGameFinder()
	{
		try
		{
			if(null == this.gameFinder)
				return;

			this.gameFinder.create(this.hostPlayerName, this.port, this.gameDescription, this.playerLimit, this.gameType.getName());
			LOG.info("Game registered with game-finder");
		}
		catch(java.io.IOException e)
		{
			LOG.log(java.util.logging.Level.WARNING, "Error reading from game-finder; not using game-finder", e);
			this.gameFinder = null;
		}
		catch(GameFinder.GameFinderException e)
		{
			LOG.warning(e.getMessage() + "; not using game-finder");
			this.gameFinder = null;
		}
	}

	@Override
	public void run()
	{
		if(!this.readyToRun)
			return;

		Thread routingThread = new Thread(this.channelRouter, "ChannelRouter");
		routingThread.start();

		if(setup())
		{
			runGame();

			// Skip any networking cleanup for local-only games
			if(0 != this.port)
			{
				closeConnections();
				stopForwardUPNP();
			}
		}

		routingThread.interrupt();
	}

	private void runGame()
	{
		if(Thread.currentThread().isInterrupted())
			return;

		try
		{
			org.rnd.jmagic.engine.Player winner = this.game.run();
			LOG.info("Game completed successfully with winner " + winner);
		}
		catch(org.rnd.jmagic.engine.Game.InterruptedGameException e)
		{
			LOG.fine("Host thread interrupted");
			alertHostError();
		}
		catch(RuntimeException e)
		{
			LOG.log(java.util.logging.Level.SEVERE, "Error while hosting the game", e);
			alertHostError();
		}
	}

	private boolean setup()
	{
		// Skip any networking setup for local-only games
		if(0 == this.port)
			return true;

		if(listenForConnections())
		{
			forwardUPNP();
			registerWithGameFinder();

			if(acceptConnections())
				return true;
			else
			{
				cancelGameFinder();
				closeConnections();
				stopForwardUPNP();
			}
		}

		return false;
	}

	private void stopForwardUPNP()
	{
		try
		{
			for(java.util.Map.Entry<java.net.InetAddress, org.bitlet.weupnp.GatewayDevice> entry: this.gateways.entrySet())
			{
				String address = entry.getKey().getHostAddress();
				org.bitlet.weupnp.GatewayDevice gateway = entry.getValue();
				if(gateway.deletePortMapping(this.port, "TCP"))
					LOG.info("Removed port forwarding on address " + address);
				else
					LOG.warning("Could not remove port forwarding on address " + address);
			}
		}
		catch(org.xml.sax.SAXException | java.io.IOException e)
		{
			LOG.log(java.util.logging.Level.WARNING, "Error when trying to remove port forwarding", e);
		}
	}

	private void updateGameFinder()
	{
		try
		{
			if(null != this.gameFinder)
			{
				LOG.info("Updating game-finder updated with new player");
				this.gameFinder.update();
			}
		}
		catch(java.io.IOException e)
		{
			LOG.log(java.util.logging.Level.WARNING, "Error updating game-finder; state of game might not be accurate on game-finder", e);
		}
	}

	/**
	 * At least one local player must be added before running the {@link Server}
	 */
	public void useGameFinder(String url, String description)
	{
		try
		{
			this.gameDescription = description;

			this.gameFinder = new GameFinder(url);
		}
		catch(java.net.URISyntaxException e)
		{
			LOG.log(java.util.logging.Level.WARNING, "Can't understand game-finder URL; not using game-finder", e);
		}
		catch(IllegalArgumentException e)
		{
			LOG.log(java.util.logging.Level.WARNING, "Game-finder URL is not absolute; not using game-finder", e);
		}
		catch(java.net.MalformedURLException e)
		{
			LOG.log(java.util.logging.Level.WARNING, "Game-finder URL is malformed; not using game-finder", e);
		}
		catch(java.io.IOException e)
		{
			LOG.log(java.util.logging.Level.WARNING, "Error reading from game-finder; not using game-finder", e);
		}
		catch(GameFinder.GameFinderException e)
		{
			LOG.warning(e.getMessage() + "; not using game-finder");
		}
	}
}
