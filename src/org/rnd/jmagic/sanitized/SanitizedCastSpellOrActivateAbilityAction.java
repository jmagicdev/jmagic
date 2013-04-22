package org.rnd.jmagic.sanitized;

public class SanitizedCastSpellOrActivateAbilityAction extends SanitizedPlayerAction
{
	private static final long serialVersionUID = 2L;

	public final int toBePlayed;

	public SanitizedCastSpellOrActivateAbilityAction(org.rnd.jmagic.engine.CastSpellOrActivateAbilityAction action)
	{
		super(action);
		this.toBePlayed = action.toBePlayedID;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(!super.equals(obj))
			return false;

		SanitizedCastSpellOrActivateAbilityAction other = (SanitizedCastSpellOrActivateAbilityAction)obj;
		if(this.toBePlayed != other.toBePlayed)
			return false;
		return true;
	}

	@Override
	public int hashCode()
	{
		return 31 * super.hashCode() + this.toBePlayed;
	}
}
