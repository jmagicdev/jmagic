package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.expansions.*;

@Name("Archangel of Thune")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Magic2014CoreSet.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE})
public final class ArchangelofThune extends Card
{
	public static final class ArchangelofThuneAbility2 extends EventTriggeredAbility
	{
		public ArchangelofThuneAbility2(GameState state)
		{
			super(state, "Whenever you gain life, put a +1/+1 counter on each creature you control.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.GAIN_LIFE);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			this.addPattern(pattern);

			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, CREATURES_YOU_CONTROL, "Put a +1/+1 counter on each creature you control."));
		}
	}

	public ArchangelofThune(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Lifelink (Damage dealt by this creature also causes you to gain that
		// much life.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));

		// Whenever you gain life, put a +1/+1 counter on each creature you
		// control.
		this.addAbility(new ArchangelofThuneAbility2(state));
	}
}
