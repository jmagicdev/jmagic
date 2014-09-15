package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scourge of Skola Vale")
@Types({Type.CREATURE})
@SubTypes({SubType.HYDRA})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class ScourgeofSkolaVale extends Card
{
	public static final class ScourgeofSkolaValeAbility2 extends ActivatedAbility
	{
		public ScourgeofSkolaValeAbility2(GameState state)
		{
			super(state, "(T), Sacrifice another creature: Put a number of +1/+1 counters on Scourge of Skola Vale equal to the sacrificed creature's toughness.");
			this.costsTap = true;

			SetGenerator anotherCreature = RelativeComplement.instance(HasType.instance(Type.CREATURE), ABILITY_SOURCE_OF_THIS);
			EventFactory sacrifice = sacrifice(You.instance(), 1, anotherCreature, "sacrifice another creature");
			this.addCost(sacrifice);

			SetGenerator num = ToughnessOf.instance(CostChoice.instance(You.instance(), sacrifice));
			this.addEffect(putCounters(num, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a number of +1/+1 counters on Scourge of Skola Vale equal to the sacrificed creature's toughness."));
		}
	}

	public ScourgeofSkolaVale(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Scourge of Skola Vale enters the battlefield with two +1/+1 counters
		// on it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, this.getName(), 2, Counter.CounterType.PLUS_ONE_PLUS_ONE));

		// (T), Sacrifice another creature: Put a number of +1/+1 counters on
		// Scourge of Skola Vale equal to the sacrificed creature's toughness.
		this.addAbility(new ScourgeofSkolaValeAbility2(state));
	}
}
