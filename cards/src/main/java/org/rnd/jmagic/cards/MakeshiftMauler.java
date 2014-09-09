package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Makeshift Mauler")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR, SubType.ZOMBIE})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class MakeshiftMauler extends Card
{
	public MakeshiftMauler(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);

		// As an additional cost to cast Makeshift Mauler, exile a creature card
		// from your graveyard.
		this.addCost(exile(You.instance(), Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance()))), 1, "exile a creature card from your graveyard"));
	}
}
