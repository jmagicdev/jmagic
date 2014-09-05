package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ball Lightning")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("RRR")
@Printings({@Printings.Printed(ex = Magic2010.class, r = Rarity.RARE), @Printings.Printed(ex = FifthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = FourthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = TheDark.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class BallLightning extends Card
{
	public static final class EndStepDie extends EventTriggeredAbility
	{
		public EndStepDie(GameState state)
		{
			super(state, "At the beginning of the end step, sacrifice Ball Lightning.");

			// At the beginning of the end step,
			this.addPattern(atTheBeginningOfTheEndStep());

			// sacrifice Ball Lightning.
			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.CAUSE, This.instance());
			parameters.put(EventType.Parameter.PLAYER, You.instance());
			parameters.put(EventType.Parameter.PERMANENT, ABILITY_SOURCE_OF_THIS);
			this.addEffect(new EventFactory(EventType.SACRIFICE_PERMANENTS, parameters, "Sacrifice Ball Lightning."));
		}
	}

	public BallLightning(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(1);

		// Trample (If this creature would deal enough damage to its blockers to
		// destroy them, you may have it deal the rest of its damage to
		// defending player or planeswalker.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Haste (This creature can attack and {T} as soon as it comes under
		// your control.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		this.addAbility(new EndStepDie(state));
	}
}
