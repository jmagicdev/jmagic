package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mardu Roughrider")
@Types({Type.CREATURE})
@SubTypes({SubType.ORC, SubType.WARRIOR})
@ManaCost("2RWB")
@ColorIdentity({Color.RED, Color.WHITE, Color.BLACK})
public final class MarduRoughrider extends Card
{
	public static final class MarduRoughriderAbility0 extends EventTriggeredAbility
	{
		public MarduRoughriderAbility0(GameState state)
		{
			super(state, "Whenever Mardu Roughrider attacks, target creature can't block this turn.");
			this.addPattern(whenThisAttacks());

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Blocking.instance(), targetedBy(target))));

			this.addEffect(createFloatingEffect("Target creature can't block this turn.", part));
		}
	}

	public MarduRoughrider(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		// Whenever Mardu Roughrider attacks, target creature can't block this
		// turn.
		this.addAbility(new MarduRoughriderAbility0(state));
	}
}
