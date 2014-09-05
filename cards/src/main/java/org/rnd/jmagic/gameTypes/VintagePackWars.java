package org.rnd.jmagic.gameTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.gameTypes.packWars.*;

public class VintagePackWars extends GameType
{
	public VintagePackWars()
	{
		super("Vintage Pack Wars");

		this.addRule(new PackWars.GameRules(new VintageBoosterFactory(), new VintageBoosterFactory(), new LandBoosterFactory(3)));
		this.addRule(new UtopiaLands());
	}
}
