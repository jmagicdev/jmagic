package org.rnd.jmagic.sanitized;

import org.rnd.jmagic.engine.*;

public class SanitizedActivatedAbility extends SanitizedNonStaticAbility
{
	private static final long serialVersionUID = 1L;

	public final boolean costsTap;
	public final boolean costsUntap;

	public SanitizedActivatedAbility(ActivatedAbility a, Player whoFor)
	{
		super(a, whoFor);

		this.costsTap = a.costsTap;
		this.costsUntap = a.costsUntap;
	}
}
