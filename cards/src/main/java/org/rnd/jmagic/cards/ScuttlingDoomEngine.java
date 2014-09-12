package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scuttling Doom Engine")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("6")
@ColorIdentity({})
public final class ScuttlingDoomEngine extends Card
{
	public static final class ScuttlingDoomEngineAbility0 extends StaticAbility
	{
		public ScuttlingDoomEngineAbility0(GameState state)
		{
			super(state, "Scuttling Doom Engine can't be blocked by creatures with power 2 or less.");

			SetGenerator smallThings = HasPower.instance(Between.instance(0, 2));
			SetGenerator illegalBlock = Intersect.instance(Blocking.instance(This.instance()), CreaturePermanents.instance(), smallThings);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(illegalBlock));
			this.addEffectPart(part);
		}
	}

	public static final class ScuttlingDoomEngineAbility1 extends EventTriggeredAbility
	{
		public ScuttlingDoomEngineAbility1(GameState state)
		{
			super(state, "When Scuttling Doom Engine dies, it deals 6 damage to target opponent.");
			this.addPattern(whenThisDies());
			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
			this.addEffect(permanentDealDamage(6, target, "Scuttling Doom Engine deals 6 damage to target opponent."));
		}
	}

	public ScuttlingDoomEngine(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Scuttling Doom Engine can't be blocked by creatures with power 2 or
		// less.
		this.addAbility(new ScuttlingDoomEngineAbility0(state));

		// When Scuttling Doom Engine dies, it deals 6 damage to target
		// opponent.
		this.addAbility(new ScuttlingDoomEngineAbility1(state));
	}
}
