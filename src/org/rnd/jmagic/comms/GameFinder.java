package org.rnd.jmagic.comms;

public class GameFinder
{
	public static class Game
	{
		public final int currentPlayers;

		public final String description;

		public final String format;

		public final String hostPlayerName;

		public final String IP;

		public final int maxPlayers;

		public final int port;

		public Game(String hostPlayerName, String IP, int port, String description, int currentPlayers, int maxPlayers, String format)
		{
			this.currentPlayers = currentPlayers;
			this.description = description;
			this.format = format;
			this.hostPlayerName = hostPlayerName;
			this.IP = IP;
			this.maxPlayers = maxPlayers;
			this.port = port;
		}
	}

	public static class GameFinderException extends java.lang.Exception
	{
		private static final long serialVersionUID = 1L;

		public GameFinderException(String message)
		{
			super(message);
		}
	}

	private java.net.URL cancelURL;

	private java.net.URL createURL;

	private java.net.URL heartbeatURL;

	private java.net.URL listURL;

	private String token = null;

	private java.net.URL updateURL;

	private java.net.URL versionURL;

	public GameFinder(String location) throws java.net.URISyntaxException, IllegalArgumentException, java.net.MalformedURLException, java.io.IOException, GameFinderException
	{
		if(null == location)
			throw new GameFinderException("Game finder URL is null");
		if(location.isEmpty())
			throw new GameFinderException("Game finder URL is empty");

		java.net.URI head = new java.net.URI(location);

		this.cancelURL = head.resolve("cancel.php").toURL();
		this.createURL = head.resolve("create.php").toURL();
		this.heartbeatURL = head.resolve("heartbeat.php").toURL();
		this.listURL = head.resolve("list.php").toURL();
		this.updateURL = head.resolve("update.php").toURL();
		this.versionURL = head.resolve("version.php").toURL();

		version();
	}

	public void cancel() throws java.io.IOException
	{
		if(null == this.token)
			return;

		java.net.HttpURLConnection connection = getPostConnection(this.cancelURL);

		java.io.OutputStreamWriter out = new java.io.OutputStreamWriter(connection.getOutputStream(), "UTF-8");
		out.write("token=" + java.net.URLEncoder.encode(this.token, "UTF-8"));
		out.close();

		// Need to get the input stream even if we don't use it so the request
		// will actually be sent
		connection.getInputStream().close();
	}

	public void create(String hostPlayerName, int port, String description, int maxPlayers, String format) throws java.io.IOException, GameFinderException
	{
		java.net.HttpURLConnection connection = getPostConnection(this.createURL);

		java.io.OutputStreamWriter out = new java.io.OutputStreamWriter(connection.getOutputStream(), "UTF-8");
		out.write("hostPlayerName=" + java.net.URLEncoder.encode(hostPlayerName, "UTF-8"));
		out.write("&port=" + java.net.URLEncoder.encode(Integer.toString(port), "UTF-8"));
		out.write("&description=" + java.net.URLEncoder.encode(description, "UTF-8"));
		out.write("&maxPlayers=" + java.net.URLEncoder.encode(Integer.toString(maxPlayers), "UTF-8"));
		out.write("&format=" + java.net.URLEncoder.encode(format, "UTF-8"));
		out.close();

		java.io.BufferedReader input = new java.io.BufferedReader(new java.io.InputStreamReader(connection.getInputStream(), "UTF-8"));
		String response = "";
		String line = input.readLine();
		while(null != line)
		{
			response += line;
			line = input.readLine();
		}
		input.close();

		response = response.trim();
		if(response.startsWith("Error"))
			throw new GameFinderException("Game-finder returned error: " + response);

		this.token = response;
	}

	private static java.net.HttpURLConnection getPostConnection(java.net.URL url) throws java.io.IOException
	{
		java.net.HttpURLConnection ret = (java.net.HttpURLConnection)(url.openConnection());
		ret.setRequestMethod("POST");
		ret.setDoOutput(true);
		return ret;
	}

	public void heartbeat() throws java.io.IOException
	{
		if(null == this.token)
			return;

		java.net.HttpURLConnection connection = getPostConnection(this.heartbeatURL);

		java.io.OutputStreamWriter out = new java.io.OutputStreamWriter(connection.getOutputStream(), "UTF-8");
		out.write("token=" + java.net.URLEncoder.encode(this.token, "UTF-8"));
		out.close();

		// Need to get the input stream even if we don't use it so the request
		// will actually be sent
		connection.getInputStream().close();
	}

	public java.util.List<Game> list() throws java.io.IOException, GameFinderException
	{
		java.net.HttpURLConnection connection = (java.net.HttpURLConnection)(this.listURL.openConnection());

		java.util.List<Game> ret = new java.util.LinkedList<Game>();
		java.io.BufferedReader input = new java.io.BufferedReader(new java.io.InputStreamReader(connection.getInputStream(), "UTF-8"));
		String line = input.readLine();
		while(null != line)
		{
			line = line.trim();
			if(!line.isEmpty())
			{
				String[] parts = line.split(":");
				if(7 != parts.length)
					throw new GameFinderException("Invalid format from the game-finder of line " + line);
				ret.add(new Game(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3], Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), parts[6]));
			}
			line = input.readLine();
		}
		input.close();
		return ret;
	}

	public void update() throws java.io.IOException
	{
		if(null == this.token)
			return;

		java.net.HttpURLConnection connection = getPostConnection(this.updateURL);

		java.io.OutputStreamWriter out = new java.io.OutputStreamWriter(connection.getOutputStream(), "UTF-8");
		out.write("token=" + java.net.URLEncoder.encode(this.token, "UTF-8"));
		out.close();

		// Need to get the input stream even if we don't use it so the request
		// will actually be sent
		connection.getInputStream().close();
	}

	private void version() throws java.io.IOException, GameFinderException
	{
		java.net.HttpURLConnection connection = (java.net.HttpURLConnection)(this.versionURL.openConnection());

		java.io.BufferedReader input = new java.io.BufferedReader(new java.io.InputStreamReader(connection.getInputStream(), "UTF-8"));
		String response = "";
		String line = input.readLine();
		while(null != line)
		{
			response += line;
			line = input.readLine();
		}
		String[] versions = response.trim().split("\\.");
		if((2 != versions.length) || (0 != Integer.parseInt(versions[0])) || (7 != Integer.parseInt(versions[1])))
			throw new GameFinderException("The game-finder is version " + response + "; this version of jMagic only supports a version 0.7 game-finder");
	}
}
