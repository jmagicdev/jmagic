package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mana Confluence")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.JOURNEY_INTO_NYX, r = Rarity.RARE)})
@ColorIdentity({})
public final class ManaConfluence extends Card
{
	public static final class ManaConfluenceAbility0 extends ActivatedAbility
	{
		public ManaConfluenceAbility0(GameState state)
		{
			super(state, "{T}, Pay 1 life: Add one mana of any color to your mana pool.");
			this.costsTap = true;
			this.addCost(payLife(You.instance(), 1, "Pay 1 life."));
			this.addEffect(addManaToYourManaPoolFromAbility("(WUBRG)"));
		}
	}

	public ManaConfluence(GameState state)
	{
		super(state);


		// {T}, Pay 1 life: Add one mana of any color to your mana pool.
		this.addAbility(new ManaConfluenceAbility0(state));
	}
}
