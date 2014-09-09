package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Time of Heroes")
@Types({Type.ENCHANTMENT})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class TimeofHeroes extends Card
{
	public static final class LevelersAreHeroic extends StaticAbility
	{
		public LevelersAreHeroic(GameState state)
		{
			super(state, "Each creature you control with a level counter on it gets +2/+2.");

			SetGenerator levelOneGuys = Intersect.instance(CREATURES_YOU_CONTROL, HasCounterOfType.instance(Counter.CounterType.LEVEL));
			this.addEffectPart(modifyPowerAndToughness(levelOneGuys, +2, +2));
		}
	}

	public TimeofHeroes(GameState state)
	{
		super(state);

		// Each creature you control with a level counter on it gets +2/+2.
		this.addAbility(new LevelersAreHeroic(state));
	}
}
