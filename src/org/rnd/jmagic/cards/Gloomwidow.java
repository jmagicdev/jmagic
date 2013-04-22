package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gloomwidow")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIDER})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.SHADOWMOOR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class Gloomwidow extends Card
{
	public static final class GloomwidowAbility1 extends StaticAbility
	{
		public GloomwidowAbility1(GameState state)
		{
			super(state, "Gloomwidow can block only creatures with flying.");

			SetGenerator hasFlying = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class);
			SetGenerator blockingNonFlyer = RelativeComplement.instance(BlockedBy.instance(This.instance()), hasFlying);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(blockingNonFlyer));
			this.addEffectPart(part);
		}
	}

	public Gloomwidow(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Reach
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));

		// Gloomwidow can block only creatures with flying.
		this.addAbility(new GloomwidowAbility1(state));
	}
}
