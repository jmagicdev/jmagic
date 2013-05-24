package org.rnd.jmagic;

public class Version implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	public final int major;

	public final int minor;

	public final int revision;

	public Version()
	{
		this.major = 0;
		this.minor = 12;
		this.revision = 0;
	}

	public boolean isCompatibleWith(Version other)
	{
		if(this.major != other.major)
			return false;
		if(this.minor != other.minor)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return this.major + "." + this.minor + "." + this.revision;
	}
}
