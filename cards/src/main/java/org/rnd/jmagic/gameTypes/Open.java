package org.rnd.jmagic.gameTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;

public class Open extends GameType
{
	public Open()
	{
		super("Open");

		this.addRule(new SideboardAsWishboard());
	}
}
