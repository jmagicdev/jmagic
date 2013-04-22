package org.rnd.jmagic.comms;

/**
 * <p>
 * A reference implementation for playing jMagic over a socket. This is designed
 * to be used with the {@link Server} class on the other end of the sockets.
 */
public class Client implements Runnable
{
	private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(Client.class.getName());

	private org.rnd.util.ChannelRouter channelRouter;

	private String host;

	private String name;

	private org.rnd.jmagic.engine.PlayerInterface playerInterface;

	private int port;

	private StreamChatterClient streamChatterClient;

	private org.rnd.jmagic.Version version;

	public Client(String host, int port, String name, org.rnd.jmagic.engine.PlayerInterface playerInterface, ChatManager.Callback chatCallback) throws java.io.IOException
	{
		this.channelRouter = new org.rnd.util.ChannelRouter(new org.rnd.util.ExceptionListener<java.io.IOException>()
		{
			@Override
			public void exceptionThrown(java.io.IOException exception)
			{
				LOG.log(java.util.logging.Level.SEVERE, "I/O error while running ChannelRouter", exception);
			}
		});

		this.host = host;
		this.name = name;
		this.playerInterface = playerInterface;
		this.port = port;

		this.streamChatterClient = new StreamChatterClient(chatCallback, new org.rnd.util.ExceptionListener<java.io.IOException>()
		{
			@Override
			public void exceptionThrown(java.io.IOException exception)
			{
				LOG.log(java.util.logging.Level.SEVERE, "IOException while reading from System.in or writing to server", exception);
			}
		});

		this.version = new org.rnd.jmagic.Version();
	}

	private void connectChat(java.util.UUID key)
	{
		try
		{
			java.nio.channels.SocketChannel channel = java.nio.channels.SocketChannel.open(new java.net.InetSocketAddress(this.host, this.port));
			channel.configureBlocking(false);

			// Always create the output stream first and flush it before
			// creating the input stream so a stream header is immediately
			// available
			java.io.ObjectOutputStream out = this.channelRouter.addRouteFromObjectOutputStream(channel);
			out.writeObject(this.version);
			out.writeObject(Server.SocketType.CHAT);
			out.writeObject(key);
			out.flush();

			java.io.ObjectInputStream in = this.channelRouter.addRouteToObjectInputStream(channel);

			// Read a Version object from the other side to see if we're
			// compatible
			org.rnd.jmagic.Version otherVersion = (org.rnd.jmagic.Version)in.readObject();
			if(!this.version.isCompatibleWith(otherVersion))
			{
				LOG.severe("Version mismatch between client (" + this.version + ") and host(" + otherVersion + ")");
				return;
			}

			this.streamChatterClient.setStreams(in, out);
			this.streamChatterClient.run();
		}
		catch(java.net.UnknownHostException e)
		{
			LOG.severe("Could not find host \"" + this.host + "\"");
		}
		catch(java.nio.channels.ClosedByInterruptException e)
		{
			LOG.fine("Interrupting chat connection due to user interrupt");
		}
		catch(java.io.IOException e)
		{
			LOG.log(java.util.logging.Level.SEVERE, "I/O error while trying to connect", e);
		}
		catch(ClassNotFoundException e)
		{
			LOG.log(java.util.logging.Level.SEVERE, "Server is not speaking using expected protocol", e);
		}
	}

	public ChatManager.MessagePoster getMessagePoster()
	{
		return this.streamChatterClient;
	}

	@Override
	public void run()
	{
		LOG.info("Trying to connect to " + this.host + " on port " + this.port);

		Thread routingThread = new Thread(this.channelRouter, "ChannelRouter");
		routingThread.start();

		java.nio.channels.SocketChannel channel = null;
		Thread chatInputThread = null;

		try
		{
			channel = java.nio.channels.SocketChannel.open(new java.net.InetSocketAddress(this.host, this.port));
			channel.configureBlocking(false);

			// Always create the output stream first and flush it before
			// creating the input stream so a stream header is immediately
			// available
			java.io.ObjectOutputStream out = this.channelRouter.addRouteFromObjectOutputStream(channel);
			out.writeObject(this.version);
			out.writeObject(Server.SocketType.ENGINE);
			out.flush();

			java.io.ObjectInputStream in = this.channelRouter.addRouteToObjectInputStream(channel);

			// Read a Version object from the other side to see if we're
			// compatible
			org.rnd.jmagic.Version otherVersion = (org.rnd.jmagic.Version)in.readObject();
			if(!this.version.isCompatibleWith(otherVersion))
			{
				LOG.severe("Version mismatch between client (" + this.version + ") and host(" + otherVersion + ")");
				routingThread.interrupt();
				return;
			}

			final java.util.UUID key = (java.util.UUID)(in.readObject());

			LOG.info("Starting game as player \"" + this.name + "\"");

			chatInputThread = new Thread("ChatInputFromSocket")
			{
				@Override
				public void run()
				{
					connectChat(key);
				}
			};
			chatInputThread.start();

			StreamPlayer.run(in, out, this.playerInterface);
		}
		catch(java.net.UnknownHostException e)
		{
			LOG.severe("Could not find host \"" + this.host + "\"");
		}
		catch(ClassNotFoundException e)
		{
			LOG.log(java.util.logging.Level.SEVERE, "The server sent an unknown class-type", e);
		}
		catch(java.nio.channels.ClosedByInterruptException e)
		{
			LOG.fine("Interrupting opening connection due to user interrupt");
		}
		catch(java.io.IOException e)
		{
			LOG.log(java.util.logging.Level.SEVERE, "An IO error occured while communicating with the server", e);
		}
		catch(org.rnd.jmagic.engine.Game.InterruptedGameException e)
		{
			// The interface will throw this exception if it wants to end the
			// game prematurely, so just exit
			LOG.fine("Ending jMagic due to user interrupt");
		}

		try
		{
			if(null != channel)
				channel.close();
		}
		catch(java.io.IOException e)
		{
			LOG.log(java.util.logging.Level.WARNING, "IOException when closing socket", e);
		}

		LOG.fine("Ending jMagic");
		if(null != chatInputThread)
			chatInputThread.interrupt();
		routingThread.interrupt();
	}
}
