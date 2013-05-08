package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Headless Skaab")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.ZOMBIE})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class HeadlessSkaab extends Card
{
	public HeadlessSkaab(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(6);

		// As an additional cost to cast Headless Skaab, exile a creature card
		// from your graveyard.
		this.addCost(exile(You.instance(), Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance()))), 1, "exile a creature card from your graveyard"));

		// Headless Skaab enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));
	}
}
