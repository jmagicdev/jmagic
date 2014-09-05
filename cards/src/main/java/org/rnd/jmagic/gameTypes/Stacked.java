package org.rnd.jmagic.gameTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;

public class Stacked extends GameType
{
	public Stacked()
	{
		super("Stacked (cheater!)");

		this.addRule(new org.rnd.jmagic.engine.gameTypes.Stacked());
		this.addRule(new SideboardAsWishboard());
	}
}
