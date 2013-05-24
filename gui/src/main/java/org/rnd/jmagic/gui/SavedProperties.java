package org.rnd.jmagic.gui;

/**
 * XML-encodes properties to a file whenever any property is changed via
 * {@link #put(Object, Object)}. Use {@link #setFileName(String)} to set what
 * filename the properties are encoded to.
 */
public class SavedProperties extends java.util.Properties
{
	private static final java.util.Set<String> OLD_CLASSES;

	static
	{
		OLD_CLASSES = new java.util.HashSet<String>();
		OLD_CLASSES.add("org/rnd/jmagic/gui/Properties");
		OLD_CLASSES.add("org.rnd.jmagic.gui.Properties");
	}

	public static SavedProperties createFromFile(String filePath)
	{
		final java.util.concurrent.atomic.AtomicBoolean fix = new java.util.concurrent.atomic.AtomicBoolean(false);
		java.beans.ExceptionListener exceptionListener = new java.beans.ExceptionListener()
		{
			private boolean firstException = true;

			@Override
			public void exceptionThrown(Exception e)
			{
				if(this.firstException)
				{
					// A ClassNotFoundException will be thrown first if the
					// class of the properties object changed
					if((e instanceof ClassNotFoundException) && OLD_CLASSES.contains(e.getMessage()))
						fix.set(true);
					this.firstException = false;
				}
				LOG.log(java.util.logging.Level.WARNING, "Error during decoding properties object or closing XML decoder", e);
			}
		};

		SavedProperties ret;
		java.beans.XMLDecoder xml = null;
		try
		{
			xml = new java.beans.XMLDecoder(new java.io.BufferedInputStream(new java.io.FileInputStream(filePath)), null, exceptionListener);

			ret = (SavedProperties)xml.readObject();
			// This happens in Java 6 instead of throwing an
			// ArrayIndexOutOfBoundsException
			if(null == ret)
				throw new ArrayIndexOutOfBoundsException();
		}
		catch(java.io.FileNotFoundException e)
		{
			LOG.log(java.util.logging.Level.WARNING, "Could not find properties file", e);
			ret = new SavedProperties();
		}
		// Since the encoded properties object should be the only object in the
		// file, an error in decoding makes the file effectively empty, so this
		// communicates an error in decoding the properties object
		catch(ArrayIndexOutOfBoundsException e)
		{
			if(fix.get())
				return createFromFixedPropertiesFile(filePath);
			ret = new SavedProperties();
		}
		finally
		{
			if(null != xml)
				xml.close();
		}

		ret.setFileName(filePath);
		return ret;
	}

	private static SavedProperties createFromFixedPropertiesFile(String filePath)
	{
		try
		{
			java.nio.charset.Charset propertiesCharset = java.nio.charset.Charset.forName("UTF-8");
			java.io.FileInputStream propertiesFile = new java.io.FileInputStream(filePath);
			java.io.InputStreamReader propertiesFileDecoded = new java.io.InputStreamReader(propertiesFile, propertiesCharset);
			java.io.BufferedReader in = new java.io.BufferedReader(propertiesFileDecoded);

			StringBuilder fileContents = new StringBuilder();
			boolean foundJavaLine = false;
			String line = in.readLine();
			while(null != line)
			{
				if(foundJavaLine)
				{
					line = line.replace("<object class=\"org.rnd.jmagic.gui.Properties\">", "<object class=\"" + SavedProperties.class.getName() + "\">");
					foundJavaLine = false;
				}
				else
					foundJavaLine = line.startsWith("<java");
				fileContents.append(line);
				line = in.readLine();
			}
			in.close();

			byte[] buffer = fileContents.toString().getBytes(propertiesCharset);
			java.beans.XMLDecoder xml = new java.beans.XMLDecoder(new java.io.BufferedInputStream(new java.io.ByteArrayInputStream(buffer)));
			SavedProperties ret = (SavedProperties)xml.readObject();
			ret.setFileName(filePath);
			xml.close();
			return ret;
		}
		catch(java.io.FileNotFoundException e)
		{
			LOG.log(java.util.logging.Level.WARNING, "Could not find properties file", e);
		}
		catch(java.io.IOException e)
		{
			LOG.log(java.util.logging.Level.WARNING, "Error reading/writing properties file", e);
		}

		return new SavedProperties();
	}

	private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(SavedProperties.class.getName());

	private static final java.beans.PersistenceDelegate MAP_PERSISTENCE_DELEGATE = new java.beans.DefaultPersistenceDelegate()
	{
		@Override
		protected void initialize(Class<?> type, Object oldInstance, Object newInstance, java.beans.Encoder out)
		{
			@SuppressWarnings("unchecked") java.util.Map<Object, Object> map = (java.util.Map<Object, Object>)oldInstance;

			for(java.util.Map.Entry<Object, Object> entry: map.entrySet())
				out.writeStatement(new java.beans.Statement(oldInstance, "put", new Object[] {entry.getKey(), entry.getValue()}));
		}

		@Override
		protected java.beans.Expression instantiate(Object oldInstance, java.beans.Encoder out)
		{
			return super.instantiate(oldInstance, out);
		}
	};

	private static final long serialVersionUID = 1L;

	private String fileName = null;

	@Override
	public synchronized Object put(Object key, Object value)
	{
		Object ret = super.put(key, value);
		saveProperties();
		return ret;
	}

	private void saveProperties()
	{
		if(null == this.fileName)
			return;

		try
		{
			java.beans.XMLEncoder xml = new java.beans.XMLEncoder(new java.io.BufferedOutputStream(new java.io.FileOutputStream(this.fileName)));
			xml.setPersistenceDelegate(SavedProperties.class, MAP_PERSISTENCE_DELEGATE);
			xml.writeObject(this);
			xml.close();
		}
		catch(java.io.FileNotFoundException e)
		{
			LOG.log(java.util.logging.Level.WARNING, "Could not create properties file", e);
		}
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
}
