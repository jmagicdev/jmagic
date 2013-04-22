package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Skaab Goliath")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.GIANT})
@ManaCost("5U")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class SkaabGoliath extends Card
{
	public SkaabGoliath(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(9);

		// As an additional cost to cast Skaab Goliath, exile two creature cards
		// from your graveyard.
		this.addCost(exile(You.instance(), Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance()))), 2, "exile two creature cards from your graveyard"));

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
	}
}
