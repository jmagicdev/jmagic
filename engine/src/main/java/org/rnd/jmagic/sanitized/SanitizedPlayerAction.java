package org.rnd.jmagic.sanitized;

import org.rnd.jmagic.engine.*;

public class SanitizedPlayerAction implements java.io.Serializable
{
	private static final long serialVersionUID = 2L;

	public final String name;
	public final int actOnID;
	public final int sourceID;

	public SanitizedPlayerAction(PlayerAction a)
	{
		this.name = a.name;

		// This is "(T): Add (G) to your mana pool."
		this.actOnID = a.getSourceObjectID();

		// This is the forest.
		this.sourceID = a.sourceID;
	}

	public SanitizedPlayerAction(String name, int actOnID, int sourceID)
	{
		this.name = name;
		this.actOnID = actOnID;
		this.sourceID = sourceID;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		result = prime * result + this.actOnID;
		result = prime * result + this.sourceID;
		return result;
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
		SanitizedPlayerAction other = (SanitizedPlayerAction)obj;
		if(this.name == null)
		{
			if(other.name != null)
				return false;
		}
		else if(!this.name.equals(other.name))
			return false;
		if(this.actOnID != other.actOnID)
			return false;
		if(this.sourceID != other.sourceID)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return this.name;
	}
}
