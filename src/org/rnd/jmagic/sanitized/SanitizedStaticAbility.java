package org.rnd.jmagic.sanitized;

public class SanitizedStaticAbility extends SanitizedIdentified
{
	private static final long serialVersionUID = 1L;

	public final int sourceID;

	public SanitizedStaticAbility(org.rnd.jmagic.engine.StaticAbility a)
	{
		super(a);

		this.sourceID = a.sourceID;
	}
}
