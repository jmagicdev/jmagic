package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Street Wraith")
@Types({Type.CREATURE})
@SubTypes({SubType.WRAITH})
@ManaCost("3BB")
@Printings({@Printings.Printed(ex = FutureSight.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class StreetWraith extends Card
{
	public StreetWraith(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Swampwalk
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Swampwalk(state));

		// Cycling\u2014Pay 2 life.
		EventFactory pay2life = payLife(You.instance(), 2, "Pay 2 life");
		CostCollection cost = new CostCollection(org.rnd.jmagic.abilities.keywords.Cycling.COST_TYPE, pay2life);
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cycling(state, cost));
	}
}
