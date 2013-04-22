package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chained Throatseeker")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR})
@ManaCost("5U")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class ChainedThroatseeker extends Card
{
	public static final class ChainedThroatseekerAbility1 extends StaticAbility
	{
		public ChainedThroatseekerAbility1(GameState state)
		{
			super(state, "Chained Throatseeker can't attack unless defending player is poisoned.");

			SetGenerator attackingUnpoisoned = Not.instance(Intersect.instance(DefendingPlayer.instance(This.instance()), Poisoned.instance()));
			SetGenerator attacking = Intersect.instance(This.instance(), Attacking.instance());
			SetGenerator restriction = Both.instance(attacking, attackingUnpoisoned);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(restriction));
			this.addEffectPart(part);
		}
	}

	public ChainedThroatseeker(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));

		// Chained Throatseeker can't attack unless defending player is
		// poisoned.
		this.addAbility(new ChainedThroatseekerAbility1(state));
	}
}
