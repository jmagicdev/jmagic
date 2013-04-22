package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stormblood Berserker")
@Types({Type.CREATURE})
@SubTypes({SubType.BERSERKER, SubType.HUMAN})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class StormbloodBerserker extends Card
{
	public static final class StormbloodBerserkerAbility1 extends StaticAbility
	{
		public StormbloodBerserkerAbility1(GameState state)
		{
			super(state, "Stormblood Berserker can't be blocked except by two or more creatures.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Count.instance(Blocking.instance(This.instance())), numberGenerator(1))));
			this.addEffectPart(part);

		}
	}

	public StormbloodBerserker(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Bloodthirst 2 (If an opponent was dealt damage this turn, this
		// creature enters the battlefield with two +1/+1 counters on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bloodthirst.Final(state, 2));

		// Stormblood Berserker can't be blocked except by two or more
		// creatures.
		this.addAbility(new StormbloodBerserkerAbility1(state));
	}
}
