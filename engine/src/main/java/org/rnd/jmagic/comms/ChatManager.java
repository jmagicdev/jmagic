package org.rnd.jmagic.comms;

public class ChatManager
{
	public interface Callback
	{
		public void gotMessage(String message);
	}

	public interface MessagePoster
	{
		public void postMessage(String message);
	}

	private java.util.List<Callback> clients = new java.util.concurrent.CopyOnWriteArrayList<Callback>();

	public MessagePoster addClient(final String playerName, Callback callback)
	{
		this.clients.add(callback);
		return new MessagePoster()
		{
			@Override
			public void postMessage(String message)
			{
				String fullMessage = playerName + ": " + message;
				for(Callback c: ChatManager.this.clients)
					c.gotMessage(fullMessage);
			}
		};
	}
}
