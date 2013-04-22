package org.rnd.jmagic.sanitized;

import org.rnd.jmagic.engine.*;

public class SanitizedPlayLandAction extends SanitizedPlayerAction
{
	private static final long serialVersionUID = 1L;

	public final int land;
	public final boolean isPerTurnAction;

	public SanitizedPlayLandAction(PlayLandAction action)
	{
		super(action);
		this.land = action.landID;
		this.isPerTurnAction = (action instanceof PerTurnPlayLandAction);
	}

	@Override
	public boolean equals(Object obj)
	{
		if(!super.equals(obj))
			return false;
		SanitizedPlayLandAction other = (SanitizedPlayLandAction)obj;
		if(this.isPerTurnAction != other.isPerTurnAction)
			return false;
		if(this.land != other.land)
			return false;
		return true;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (this.isPerTurnAction ? 1231 : 1237);
		result = prime * result + this.land;
		return result;
	}
}
