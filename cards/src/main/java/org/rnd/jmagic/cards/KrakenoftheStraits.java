package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kraken of the Straits")
@Types({Type.CREATURE})
@SubTypes({SubType.KRAKEN})
@ManaCost("5UU")
@ColorIdentity({Color.BLUE})
public final class KrakenoftheStraits extends Card
{
	public static final class KrakenoftheStraitsAbility0 extends StaticAbility
	{
		public KrakenoftheStraitsAbility0(GameState state)
		{
			super(state, "Creatures with power less than the number of Islands you control can't block Kraken of the Straits.");

			SetGenerator yourIslands = Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.ISLAND));
			SetGenerator weak = HasPower.instance(Between.instance(numberGenerator(0), Count.instance(yourIslands)));
			SetGenerator restriction = Intersect.instance(weak, Blocking.instance(This.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(restriction));
			this.addEffectPart(part);
		}
	}

	public KrakenoftheStraits(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Creatures with power less than the number of Islands you control
		// can't block Kraken of the Straits.
		this.addAbility(new KrakenoftheStraitsAbility0(state));
	}
}
