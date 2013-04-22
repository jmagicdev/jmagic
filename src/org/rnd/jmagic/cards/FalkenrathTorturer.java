package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Falkenrath Torturer")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class FalkenrathTorturer extends Card
{
	public static final class FalkenrathTorturerAbility0 extends ActivatedAbility
	{
		public FalkenrathTorturerAbility0(GameState state)
		{
			super(state, "Sacrifice a creature: Falkenrath Torturer gains flying until end of turn. If the sacrificed creature was a Human, put a +1/+1 counter on Falkenrath Torturer.");

			EventFactory sacrifice = sacrificeACreature();
			this.addCost(sacrifice);

			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Flying.class, "Falkenrath Torturer gains flying until end of turn."));

			SetGenerator deadCreature = OldObjectOf.instance(EffectResult.instance(sacrifice));
			SetGenerator itWasAHuman = EvaluatePattern.instance(new SubTypePattern(SubType.HUMAN), deadCreature);
			this.addEffect(ifThen(itWasAHuman, putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a +1/+1 counter on Falkenrath Torturer."), "If the sacrificed creature was a Human, put a +1/+1 counter on Falkenrath Torturer."));
		}
	}

	public FalkenrathTorturer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Sacrifice a creature: Falkenrath Torturer gains flying until end of
		// turn. If the sacrificed creature was a Human, put a +1/+1 counter on
		// Falkenrath Torturer.
		this.addAbility(new FalkenrathTorturerAbility0(state));
	}
}
