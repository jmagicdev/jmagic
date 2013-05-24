package org.rnd.jmagic.comms;

public class StreamChatterServer implements ChatManager.Callback, Runnable
{
	private java.io.ObjectInputStream in;

	private org.rnd.util.ExceptionListener<java.io.IOException> ioExceptionListener;

	private ChatManager.MessagePoster messagePoster;

	private java.io.ObjectOutputStream out;

	public StreamChatterServer(java.io.ObjectInputStream in, java.io.ObjectOutputStream out, org.rnd.util.ExceptionListener<java.io.IOException> ioExceptionListener)
	{
		this.in = in;
		this.ioExceptionListener = ioExceptionListener;
		this.out = out;
	}

	@Override
	public void gotMessage(java.lang.String message)
	{
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
		if(null == this.messagePoster)
			return;

		try
		{
			while(true)
				this.messagePoster.postMessage(this.in.readUTF());
		}
		catch(java.io.IOException e)
		{
			this.ioExceptionListener.exceptionThrown(e);
		}
	}

	public void setMessagePoster(ChatManager.MessagePoster messagePoster)
	{
		this.messagePoster = messagePoster;
	}
}
