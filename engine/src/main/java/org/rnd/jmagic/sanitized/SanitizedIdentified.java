package org.rnd.jmagic.sanitized;

public class SanitizedIdentified implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	public final int ID;
	public final boolean isKeyword;
	public final String name;

	public SanitizedIdentified(org.rnd.jmagic.engine.Identified i)
	{
		this(i, i.getName());
	}

	public SanitizedIdentified(org.rnd.jmagic.engine.Identified i, String nameOverride)
	{
		this.ID = i.ID;
		this.isKeyword = i.isKeyword() && !(i instanceof org.rnd.jmagic.abilities.keywords.Level);
		this.name = nameOverride;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		SanitizedIdentified other = (SanitizedIdentified)obj;
		if(this.ID != other.ID)
			return false;
		return true;
	}

	@Override
	public int hashCode()
	{
		return this.ID;
	}

	@Override
	public String toString()
	{
		return this.name;
	}
}
