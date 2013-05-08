package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stormbound Geist")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class StormboundGeist extends Card
{
	public static final class StormboundGeistAbility1 extends StaticAbility
	{
		public StormboundGeistAbility1(GameState state)
		{
			super(state, "Stormbound Geist can block only creatures with flying.");

			SetGenerator hasFlying = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class);
			SetGenerator blockingNonFlyer = RelativeComplement.instance(BlockedBy.instance(This.instance()), hasFlying);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(blockingNonFlyer));
			this.addEffectPart(part);
		}
	}

	public StormboundGeist(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Stormbound Geist can block only creatures with flying.
		this.addAbility(new StormboundGeistAbility1(state));

		// Undying (When this creature dies, if it had no +1/+1 counters on it,
		// return it to the battlefield under its owner's control with a +1/+1
		// counter on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Undying(state));
	}
}
