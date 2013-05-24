package org.rnd.jmagic.sanitized;

import org.rnd.jmagic.engine.*;

public class SanitizedReplacementEffect implements SanitizedReplacement
{
	private static final long serialVersionUID = 2L;

	public final String name;

	/**
	 * True if the player who this is being sanitized for gets the option for
	 * whether to apply this effect
	 */
	private final boolean isOptionalForMe;

	public SanitizedReplacementEffect(GameState state, Player whoFor, ReplacementEffect re)
	{
		this.name = re.name;
		this.isOptionalForMe = re.optional.evaluate(re.game, re.getSourceObject(state)).contains(whoFor);
	}

	@Override
	public boolean isOptionalForMe()
	{
		return this.isOptionalForMe;
	}

	@Override
	public String toString()
	{
		return this.name;
	}
}
