package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Relentless Skaabs")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class RelentlessSkaabs extends Card
{
	public RelentlessSkaabs(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// As an additional cost to cast Relentless Skaabs, exile a creature
		// card from your graveyard.
		this.addCost(exile(You.instance(), Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance()))), 1, "exile a creature card from your graveyard"));

		// Undying (When this creature dies, if it had no +1/+1 counters on it,
		// return it to the battlefield under its owner's control with a +1/+1
		// counter on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Undying(state));
	}
}
