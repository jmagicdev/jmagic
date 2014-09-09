package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Desecration Demon")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("2BB")
@ColorIdentity({Color.BLACK})
public final class DesecrationDemon extends Card
{
	public static final class DesecrationDemonAbility1 extends EventTriggeredAbility
	{
		public DesecrationDemonAbility1(GameState state)
		{
			super(state, "At the beginning of each combat, any opponent may sacrifice a creature. If a player does, tap Desecration Demon and put a +1/+1 counter on it.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, StepOf.instance(Players.instance(), Phase.PhaseType.COMBAT, Step.StepType.BEGINNING_OF_COMBAT));
			this.addPattern(pattern);

			SetGenerator eachPlayer = DynamicEvaluation.instance();

			EventFactory sacrifice = sacrifice(eachPlayer, 1, CreaturePermanents.instance(), "Sacrifice a creature.");
			EventFactory thatOpponentMaySacrifice = playerMay(eachPlayer, sacrifice, "An opponent may sacrifice a creature.");

			EventFactory anyOpponentSacrifices = new EventFactory(FOR_EACH_PLAYER, "Any opponent may sacrifice a creature.");
			anyOpponentSacrifices.parameters.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
			anyOpponentSacrifices.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachPlayer));
			anyOpponentSacrifices.parameters.put(EventType.Parameter.EFFECT, Identity.instance(thatOpponentMaySacrifice));

			EventFactory tap = tap(ABILITY_SOURCE_OF_THIS, "Tap Desecration Demon");
			EventFactory counter = putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "and put a +1/+1 counter on it.");

			EventFactory sacTap = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "Any opponent may sacrifice a creature. If a player does, tap Desecration Demon and put a +1/+1 counter on it.");
			sacTap.parameters.put(EventType.Parameter.IF, Identity.instance(anyOpponentSacrifices));
			sacTap.parameters.put(EventType.Parameter.THEN, Identity.instance(sequence(tap, counter)));
			this.addEffect(sacTap);
		}
	}

	public DesecrationDemon(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// At the beginning of each combat, any opponent may sacrifice a
		// creature. If a player does, tap Desecration Demon and put a +1/+1
		// counter on it.
		this.addAbility(new DesecrationDemonAbility1(state));
	}
}
