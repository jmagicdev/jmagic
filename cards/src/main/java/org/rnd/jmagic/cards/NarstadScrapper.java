package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Narstad Scrapper")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("5")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class NarstadScrapper extends Card
{
	public static final class NarstadScrapperAbility0 extends ActivatedAbility
	{
		public NarstadScrapperAbility0(GameState state)
		{
			super(state, "(2): Narstad Scrapper gets +1/+0 until end of turn.");
			this.setManaCost(new ManaPool("(2)"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, +0, "Narstad Scrapper gets +1/+0 until end of turn."));
		}
	}

	public NarstadScrapper(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// (2): Narstad Scrapper gets +1/+0 until end of turn.
		this.addAbility(new NarstadScrapperAbility0(state));
	}
}
