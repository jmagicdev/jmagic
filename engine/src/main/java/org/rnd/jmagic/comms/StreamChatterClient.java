package org.rnd.jmagic.comms;

public class StreamChatterClient implements ChatManager.MessagePoster, Runnable
{
	private ChatManager.Callback callback;

	private java.io.ObjectInputStream in;

	private org.rnd.util.ExceptionListener<java.io.IOException> ioExceptionListener;

	private java.io.ObjectOutputStream out;

	public StreamChatterClient(ChatManager.Callback callback, org.rnd.util.ExceptionListener<java.io.IOException> ioExceptionListener)
	{
		this.callback = callback;
		this.in = null;
		this.ioExceptionListener = ioExceptionListener;
		this.out = null;
	}

	@Override
	public void postMessage(java.lang.String message)
	{
		if(null == this.out)
			return;

		try
		{
			this.out.writeUTF(message);
			this.out.flush();
		}
		catch(java.io.IOException e)
		{
			this.ioExceptionListener.exceptionThrown(e);
		}
	}

	@Override
	public void run()
	{
		if(null == this.in)
			return;

		try
		{
			while(true)
				this.callback.gotMessage(this.in.readUTF());
		}
		catch(java.nio.channels.ClosedByInterruptException e)
		{
			// The user requested this, so just exit
		}
		catch(java.io.IOException e)
		{
			this.ioExceptionListener.exceptionThrown(e);
		}
	}

	public void setStreams(java.io.ObjectInputStream in, java.io.ObjectOutputStream out)
	{
		this.in = in;
		this.out = out;
	}
}
