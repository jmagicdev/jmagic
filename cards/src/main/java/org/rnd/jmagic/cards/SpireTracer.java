package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spire Tracer")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.SCOUT})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class SpireTracer extends Card
{
	public static final class SpireTracerAbility0 extends StaticAbility
	{
		public SpireTracerAbility0(GameState state)
		{
			super(state, "Spire Tracer can't be blocked except by creatures with flying or reach.");

			SetGenerator hasFlying = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class);
			SetGenerator hasReach = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Reach.class);
			SetGenerator notBlockingWithFlyingOrReach = RelativeComplement.instance(Blocking.instance(This.instance()), Union.instance(hasFlying, hasReach));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(notBlockingWithFlyingOrReach));
			this.addEffectPart(part);
		}
	}

	public SpireTracer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Spire Tracer can't be blocked except by creatures with flying or
		// reach.
		this.addAbility(new SpireTracerAbility0(state));
	}
}
