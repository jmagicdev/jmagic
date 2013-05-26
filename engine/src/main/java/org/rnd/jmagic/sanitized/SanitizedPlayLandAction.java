package org.rnd.jmagic.sanitized;

import org.rnd.jmagic.engine.*;

public class SanitizedPlayLandAction extends SanitizedPlayerAction
{
	private static final long serialVersionUID = 2L;

	public final int land;

	public SanitizedPlayLandAction(PlayLandAction action)
	{
		super(action);
		this.land = action.landID;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(!super.equals(obj))
			return false;
		SanitizedPlayLandAction other = (SanitizedPlayLandAction)obj;
		if(this.land != other.land)
			return false;
		return true;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.land;
		return result;
	}
}
