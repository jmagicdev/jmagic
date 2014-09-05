package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Stitched Drake")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAKE, SubType.ZOMBIE})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class StitchedDrake extends Card
{
	public StitchedDrake(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// As an additional cost to cast Stitched Drake, exile a creature card
		// from your graveyard.
		this.addCost(exile(You.instance(), Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance()))), 1, "exile a creature card from your graveyard"));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
