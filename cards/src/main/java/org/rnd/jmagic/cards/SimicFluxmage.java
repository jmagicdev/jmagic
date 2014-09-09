package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Simic Fluxmage")
@Types({Type.CREATURE})
@SubTypes({SubType.MERFOLK, SubType.WIZARD})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class SimicFluxmage extends Card
{
	public static final class SimicFluxmageAbility1 extends ActivatedAbility
	{
		public SimicFluxmageAbility1(GameState state)
		{
			super(state, "(1)(U), (T): Move a +1/+1 counter from Simic Fluxmage onto target creature.");
			this.setManaCost(new ManaPool("(1)(U)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

			EventFactory move = new EventFactory(EventType.MOVE_COUNTERS, "Move a +1/+1 counter from Simic Fluxmage onto target creature.");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.FROM, ABILITY_SOURCE_OF_THIS);
			move.parameters.put(EventType.Parameter.TO, target);
			move.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			move.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));
			this.addEffect(move);
		}
	}

	public SimicFluxmage(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Evolve (Whenever a creature enters the battlefield under your
		// control, if that creature has greater power or toughness than this
		// creature, put a +1/+1 counter on this creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Evolve(state));

		// (1)(U), (T): Move a +1/+1 counter from Simic Fluxmage onto target
		// creature.
		this.addAbility(new SimicFluxmageAbility1(state));
	}
}
