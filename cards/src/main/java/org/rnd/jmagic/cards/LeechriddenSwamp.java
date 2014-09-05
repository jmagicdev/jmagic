package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Leechridden Swamp")
@Types({Type.LAND})
@SubTypes({SubType.SWAMP})
@Printings({@Printings.Printed(ex = Shadowmoor.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class LeechriddenSwamp extends Card
{
	public static final class Leech extends ActivatedAbility
	{
		public Leech(GameState state)
		{
			super(state, "(B), (T): Each opponent loses 1 life. Activate this ability only if you control two or more black permanents.");
			this.setManaCost(new ManaPool("(B)"));
			this.costsTap = true;

			this.addEffect(loseLife(OpponentsOf.instance(You.instance()), 1, "Each opponent loses 1 life."));

			SetGenerator yourBlackPermanents = Intersect.instance(ControlledBy.instance(You.instance()), HasColor.instance(Color.BLACK), Permanents.instance());
			this.addActivateRestriction(Intersect.instance(Between.instance(null, 1), Count.instance(yourBlackPermanents)));
		}
	}

	public LeechriddenSwamp(GameState state)
	{
		super(state);

		// Leechridden Swamp enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (B), (T): Each opponent loses 1 life. Activate this ability only if
		// you control two or more black permanents.
		this.addAbility(new Leech(state));
	}
}
