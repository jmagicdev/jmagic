package org.rnd.jmagic.sanitized;

public class SanitizedTarget implements java.io.Serializable
{
	private static final long serialVersionUID = 2L;

	public final int targetID;
	public final String name;

	/**
	 * Division isn't final because it is written to when divide is called.
	 */
	public int division;

	public SanitizedTarget(org.rnd.jmagic.engine.Target t)
	{
		this.targetID = t.targetID;
		this.division = t.division;
		this.name = t.name;
	}

	public SanitizedTarget(org.rnd.jmagic.engine.Identified i)
	{
		this.targetID = i.ID;
		this.division = 0;
		this.name = i.getName();
	}

	@Override
	public String toString()
	{
		return this.name;
	}
}
