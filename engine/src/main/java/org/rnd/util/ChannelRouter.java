package org.rnd.util;

import java.nio.channels.*;

/**
 * <p>
 * Route data from channels to channels using only a single thread.
 * </p>
 * <p>
 * Use this class by calling {@link #addRoute} for each route, then call
 * {@link #run()} (either directly or in a {@link Thread}) to start the routing.
 * Route can be added while this is running, but it can only be run once; the
 * behavior of calling {@link #run()} again after it returns the first time is
 * undefined.
 * </p>
 * <p>
 * The motivation behind this class is behavior when using
 * {@link Channels#newInputStream(ReadableByteChannel)} and
 * {@link Channels#newOutputStream(WritableByteChannel)} on a channel: reading
 * from the resulting {@link java.io.InputStream} and writing to the resulting
 * {@link java.io.OutputStream} each synchronize on
 * {@link SelectableChannel#blockingLock()}. This means writing to the channel
 * can't be done while waiting on a read. This could be solved by using
 * non-blocking reads instead of blocking reads, but that may not be possible
 * (in the case of using {@link java.io.ObjectInputStream} on
 * {@link Channels#newInputStream(ReadableByteChannel)}). Instead, use
 * {@link Pipe} and this class together to allow a blocking read on the
 * {@link Pipe} while this class handles non-blocking reads and delivers data to
 * your {@link Pipe}.
 * </p>
 * <p>
 * This class contains a simple chat client/server example as a main method.
 * </p>
 */
public class ChannelRouter implements Runnable
{
	public static void main(String[] args) throws java.io.IOException
	{
		ChannelRouter channelRouter = new ChannelRouter(new org.rnd.util.ExceptionListener<java.io.IOException>()
		{
			@Override
			public void exceptionThrown(java.io.IOException exception)
			{
				exception.printStackTrace();
			}
		});

		int port = Integer.parseInt(args[1]);
		SocketChannel socketChannel;
		if(args[0].equals("--server"))
		{
			System.out.println("Server mode started; listening on port " + port);
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.socket().bind(new java.net.InetSocketAddress(port));
			socketChannel = serverSocketChannel.accept();
			System.out.println("Client connected");
			serverSocketChannel.close();
		}
		else
		{
			String host = args[0];
			System.out.println("Client mode started; connecting to " + host + " on port " + port);
			socketChannel = SocketChannel.open(new java.net.InetSocketAddress(host, port));
			System.out.println("Connected to server");
		}
		socketChannel.configureBlocking(false);

		Pipe localToRemote = Pipe.open();
		localToRemote.source().configureBlocking(false);
		channelRouter.addRoute(localToRemote.source(), socketChannel);

		final Pipe remoteToLocal = Pipe.open();
		remoteToLocal.sink().configureBlocking(false);
		channelRouter.addRoute(socketChannel, remoteToLocal.sink());

		Thread channelRouterThread = new Thread(channelRouter, "ChannelRouter");
		channelRouterThread.start();

		Thread remoteReaderThread = new Thread("RemoteReader")
		{
			@Override
			public void run()
			{
				try
				{
					// BufferedReader.readLine() blocks until an end-of-line is
					// written, so make sure they get written by the main thread
					java.io.InputStream temporaryInputStream = Channels.newInputStream(remoteToLocal.source());
					java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(temporaryInputStream));
					String line = in.readLine();
					while(null != line)
					{
						System.out.println(line);
						line = in.readLine();
					}
				}
				catch(ClosedByInterruptException e)
				{
					// The main thread wants us to close, so do nothing
				}
				catch(java.io.IOException e)
				{
					e.printStackTrace();
				}
			}
		};
		remoteReaderThread.start();

		java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));

		java.io.Writer temporaryWriter = new java.io.OutputStreamWriter(Channels.newOutputStream(localToRemote.sink()));
		temporaryWriter = new java.io.BufferedWriter(temporaryWriter);
		java.io.PrintWriter out = new java.io.PrintWriter(temporaryWriter, true);

		System.out.println("Press end-of-file to terminate (Ctrl+Z on Windows, Ctrl+D on other platforms)");
		String line = in.readLine();
		while(null != line)
		{
			// The end-of-line is required for BufferedReader.readLine() to
			// return and this will automatically flush due to the auto-flush
			// parameter on construction
			out.println(line);
			line = in.readLine();
		}

		remoteReaderThread.interrupt();
		channelRouterThread.interrupt();
		socketChannel.close();
	}

	/**
	 * A convenience method for the following code:
	 * 
	 * <pre>
	 * {@link java.io.InputStream} tempInputStream = {@link Channels#newInputStream(ReadableByteChannel) Channels.newInputStream(input)};
	 * tempInputStream = new {@link java.io.BufferedInputStream#BufferedInputStream(java.io.InputStream) java.io.BufferedInputStream(tempInputStream)};
	 * return new {@link java.io.ObjectInputStream#ObjectInputStream(java.io.InputStream) java.io.ObjectInputStream(tempInputStream)};
	 * </pre>
	 * 
	 * @throws java.io.IOException pass-through for any
	 * {@link java.io.IOException} that occurs in the above code
	 */
	public static java.io.ObjectInputStream wrapChannelInObjectInputStream(ReadableByteChannel input) throws java.io.IOException
	{
		java.io.InputStream tempInputStream = Channels.newInputStream(input);
		tempInputStream = new java.io.BufferedInputStream(tempInputStream);
		return new java.io.ObjectInputStream(tempInputStream);
	}

	/**
	 * A convenience method for the following code:
	 * 
	 * <pre>
	 * {@link java.io.OutputStream} tempOutputStream = {@link Channels#newOutputStream(WritableByteChannel) Channels.newOutputStream(output)};
	 * tempOutputStream = new {@link java.io.BufferedOutputStream#BufferedOutputStream(java.io.OutputStream) java.io.BufferedOutputStream(tempOutputStream)};
	 * return new {@link java.io.ObjectOutputStream#ObjectOutputStream(java.io.OutputStream) java.io.ObjectOutputStream(tempOutputStream)};
	 * </pre>
	 * 
	 * @throws java.io.IOException pass-through for any
	 * {@link java.io.IOException} that occurs in the above code
	 */
	public static java.io.ObjectOutputStream wrapChannelInObjectOutputStream(WritableByteChannel output) throws java.io.IOException
	{
		java.io.OutputStream tempOutputStream = Channels.newOutputStream(output);
		tempOutputStream = new java.io.BufferedOutputStream(tempOutputStream);
		return new java.io.ObjectOutputStream(tempOutputStream);
	}

	private static int BUFFER_SIZE = 65536;

	private static int SELECT_MILLISECONDS = 100;

	private ExceptionListener<java.io.IOException> exceptionListener;

	private java.util.concurrent.ConcurrentMap<SelectableChannel, java.nio.ByteBuffer> outputBuffers;

	private java.util.concurrent.ConcurrentMap<SelectableChannel, SelectableChannel> outputs;

	private Selector selector;

	public ChannelRouter(ExceptionListener<java.io.IOException> exceptionListener) throws java.io.IOException
	{
		this.exceptionListener = exceptionListener;
		this.outputBuffers = new java.util.concurrent.ConcurrentHashMap<SelectableChannel, java.nio.ByteBuffer>();
		this.outputs = new java.util.concurrent.ConcurrentHashMap<SelectableChannel, SelectableChannel>();
		this.selector = Selector.open();
	}

	/**
	 * Add a route from one channel to another. An input can only have one
	 * output, but multiple inputs can be routed to a single output (no order is
	 * imposed, though, so this is usually not a good idea). The collection of
	 * routes is thread-safe, so it is legal to call this method after
	 * {@link #run()} has been called.
	 * 
	 * @param input This must be configured as non-blocking by using
	 * {@link SelectableChannel#configureBlocking(boolean)}
	 * @param output This must be configured as non-blocking in the same way as
	 * input
	 */
	public <T extends SelectableChannel & ReadableByteChannel, S extends SelectableChannel & WritableByteChannel> void addRoute(T input, S output) throws ClosedChannelException
	{
		// Must put into the maps before registering with the Selector in case
		// this thread is preempted by the input being available for read before
		// there's an output ready for it
		this.outputBuffers.put(output, java.nio.ByteBuffer.allocate(BUFFER_SIZE));
		this.outputs.put(input, output);

		// Register the output channel with the selector if it isn't already
		// registered, but don't register for any operations yet; this will be
		// used later to switch the output to and from selecting for writing in
		// the run loop. In addition, do this first so the output key is always
		// available in case data is immediately available on input to be read
		if(!(output.isRegistered()))
			output.register(this.selector, 0);
		input.register(this.selector, SelectionKey.OP_READ);
	}

	/**
	 * A convenience method for the following code:
	 * 
	 * <pre>
	 * {@link Pipe} inputToOutput = {@link Pipe#open()};
	 * {@link java.nio.channels.Pipe.SourceChannel} source = {@link Pipe#source() inputToOutput.source()};
	 * {@link SelectableChannel#configureBlocking(boolean) source.configureBlocking(false)};
	 * {@link #addRoute addRoute(source, output)};
	 * 
	 * {@link java.nio.channels.Pipe.SinkChannel} sink = {@link Pipe#sink() inputToOutput.sink()};
	 * return {@link #wrapChannelInObjectOutputStream(WritableByteChannel) wrapChannelInObjectOutputStream(sink)};
	 * </pre>
	 * 
	 * Users must call {@link java.io.ObjectOutputStream#flush()} before
	 * attempting to construct a matching {@link java.io.ObjectInputStream} to
	 * ensure a serialization header is available.
	 * 
	 * @throws java.io.IOException pass-through for any
	 * {@link java.io.IOException} that occurs in the above code
	 */
	public <T extends SelectableChannel & WritableByteChannel> java.io.ObjectOutputStream addRouteFromObjectOutputStream(T output) throws java.io.IOException
	{
		Pipe inputToOutput = Pipe.open();
		Pipe.SourceChannel source = inputToOutput.source();
		source.configureBlocking(false);
		addRoute(source, output);

		Pipe.SinkChannel sink = inputToOutput.sink();
		return wrapChannelInObjectOutputStream(sink);
	}

	/**
	 * A convenience method for the following code:
	 * 
	 * <pre>
	 * {@link Pipe} inputToOutput = {@link Pipe#open()};
	 * {@link java.nio.channels.Pipe.SinkChannel} sink = {@link Pipe#sink() inputToOutput.sink()};
	 * {@link SelectableChannel#configureBlocking(boolean) sink.configureBlocking(false)};
	 * {@link #addRoute addRoute(input, sink)};
	 * 
	 * {@link java.nio.channels.Pipe.SourceChannel} source = {@link Pipe#source() inputToOutput.source()};
	 * return {@link #wrapChannelInObjectInputStream(ReadableByteChannel) wrapChannelInObjectInputStream(source)};
	 * </pre>
	 * 
	 * @throws java.io.IOException pass-through for any
	 * {@link java.io.IOException} that occurs in the above code
	 */
	public <T extends SelectableChannel & ReadableByteChannel> java.io.ObjectInputStream addRouteToObjectInputStream(T input) throws java.io.IOException
	{
		Pipe inputToOutput = Pipe.open();
		Pipe.SinkChannel sink = inputToOutput.sink();
		sink.configureBlocking(false);
		addRoute(input, sink);

		Pipe.SourceChannel source = inputToOutput.source();
		return wrapChannelInObjectInputStream(source);
	}

	private void closeChannelAndReportException(Channel channel)
	{
		try
		{
			channel.close();
		}
		catch(java.io.IOException e)
		{
			reportIOException(e);
		}
	}

	private void handleReadableChannel(SelectionKey key, SelectableChannel channel)
	{
		try
		{
			if(!key.isReadable())
				return;

			SelectableChannel output = this.outputs.get(channel);
			java.nio.ByteBuffer buffer = this.outputBuffers.get(output);

			if(-1 == ((ReadableByteChannel)channel).read(buffer))
				// This has never happened as read has always thrown an
				// exception instead, but handle it just in case
				throw new java.io.EOFException();

			SelectionKey outputKey = output.keyFor(this.selector);
			// The channel might have been closed and unregistered by the time
			// this happened
			if(null != outputKey)
				outputKey.interestOps(outputKey.interestOps() | SelectionKey.OP_WRITE);

			// The code below the catch blocks below is for error-handling only
			return;
		}
		catch(CancelledKeyException e)
		{
			// Error-handling code below to avoid code duplication
		}
		catch(ClosedChannelException e)
		{
			// Error-handling code below to avoid code duplication
		}
		catch(java.io.EOFException e)
		{
			// Error-handling code below to avoid code duplication
		}
		catch(java.io.IOException e)
		{
			reportIOException(e);

			// Continue with the error-handling code below
		}

		this.outputs.remove(channel);

		try
		{
			// No longer interested in handling this channel for read operations
			key.interestOps(key.interestOps() & ~SelectionKey.OP_READ);
		}
		catch(CancelledKeyException e)
		{
			// Do nothing
		}

		// The channel should already be closed, but just in case
		closeChannelAndReportException(channel);
	}

	private void handleWritableChannel(SelectionKey key, SelectableChannel channel)
	{
		try
		{
			if(!key.isWritable())
				return;

			java.nio.ByteBuffer buffer = this.outputBuffers.get(channel);
			buffer.flip();
			((WritableByteChannel)channel).write(buffer);

			// If there aren't any bytes left to write from the buffer, tell the
			// selector that we no longer want to write to the channel (and-not
			// disables only that bit)
			if(0 == buffer.remaining())
				key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
			buffer.compact();

			// The code below the catch blocks below is for error-handling only
			return;
		}
		catch(CancelledKeyException e)
		{
			// Error-handling code below to avoid code duplication
		}
		catch(ClosedChannelException e)
		{
			// Error-handling code below to avoid code duplication
		}
		catch(java.io.EOFException e)
		{
			// Error-handling code below to avoid code duplication
		}
		catch(java.io.IOException e)
		{
			reportIOException(e);

			// Continue with the error-handling code below
		}

		this.outputBuffers.remove(channel);

		// Close and remove any routes that point to this channel as an output
		java.util.Iterator<java.util.Map.Entry<SelectableChannel, SelectableChannel>> i = this.outputs.entrySet().iterator();
		while(i.hasNext())
		{
			java.util.Map.Entry<SelectableChannel, SelectableChannel> entry = i.next();
			if(entry.getValue().equals(channel))
			{
				closeChannelAndReportException(entry.getKey());
				i.remove();
			}
		}

		try
		{
			// No longer interested in handling this channel for write
			// operations
			key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
		}
		catch(CancelledKeyException e)
		{
			// Do nothing
		}

		// The channel should already be closed, but just in case
		closeChannelAndReportException(channel);
	}

	private void reportIOException(java.io.IOException e)
	{
		if(null != this.exceptionListener)
			this.exceptionListener.exceptionThrown(e);
	}

	@Override
	public void run()
	{
		try
		{
			while(true)
			{
				this.selector.select(SELECT_MILLISECONDS);
				if(Thread.interrupted())
					break;

				java.util.Iterator<SelectionKey> i = this.selector.selectedKeys().iterator();
				while(i.hasNext())
				{
					SelectionKey key = i.next();
					SelectableChannel channel = key.channel();
					handleReadableChannel(key, channel);
					handleWritableChannel(key, channel);
					i.remove();
				}
			}
		}
		catch(ClosedByInterruptException e)
		{
			// User-requested interrupt, so clean up
		}
		catch(java.io.IOException e)
		{
			reportIOException(e);
		}

		for(java.util.Map.Entry<SelectableChannel, SelectableChannel> e: this.outputs.entrySet())
		{
			closeChannelAndReportException(e.getKey());
			closeChannelAndReportException(e.getValue());
		}

		for(SelectableChannel c: this.outputBuffers.keySet())
			closeChannelAndReportException(c);
	}
}
