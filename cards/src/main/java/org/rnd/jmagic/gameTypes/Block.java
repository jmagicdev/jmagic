package org.rnd.jmagic.gameTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.expansions.*;

public class Block extends GameType
{
	public Block()
	{
		super("THS Block Constructed");

		this.addRule(new DeckSizeMinimum(60));
		this.addRule(new MaximumCardCount(4));
		this.addRule(new SideboardSize(15));
		this.addRule(new SideboardAsWishboard());
		this.addRule(new CardPool());
	}

	@Name("THS Block Constructed")
	@Description("Theros Block Constructed, as of 2014 September 1 (Theros through Journey Into Nyx)")
	public static class CardPool extends org.rnd.jmagic.engine.gameTypes.CardPool
	{
		public CardPool()
		{
			super(false);

			this.allowSet(Theros.class);
			this.allowSet(BornOfTheGods.class);
			this.allowSet(JourneyIntoNyx.class);
		}
	}
}
