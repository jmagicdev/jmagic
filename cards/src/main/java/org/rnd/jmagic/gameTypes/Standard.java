package org.rnd.jmagic.gameTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.expansions.*;

public class Standard extends GameType
{
	public Standard()
	{
		super("Standard");

		this.addRule(new DeckSizeMinimum(60));
		this.addRule(new MaximumCardCount(4));
		this.addRule(new SideboardSize(15));
		this.addRule(new SideboardAsWishboard());
		this.addRule(new CardPool());
	}

	@Name("Standard")
	@Description("Standard, as of 2014 September 1 (Return to Ravnica through Magic 2015)")
	public static class CardPool extends org.rnd.jmagic.engine.gameTypes.CardPool
	{
		public CardPool()
		{
			super(false);

			this.allowSet(ReturnToRavnica.class);
			this.allowSet(Gatecrash.class);
			this.allowSet(DragonsMaze.class);
			this.allowSet(Magic2014CoreSet.class);
			this.allowSet(Theros.class);
			this.allowSet(BornOfTheGods.class);
			this.allowSet(JourneyIntoNyx.class);
			this.allowSet(Magic2015CoreSet.class);
		}
	}
}
