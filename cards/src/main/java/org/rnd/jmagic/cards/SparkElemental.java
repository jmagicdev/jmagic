package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Spark Elemental")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class SparkElemental extends Card
{
	public static final class Fizzle extends EventTriggeredAbility
	{
		public Fizzle(GameState state)
		{
			super(state, "At the beginning of the end step, sacrifice Spark Elemental.");
			this.addPattern(atTheBeginningOfTheEndStep());
			this.addEffect(sacrificeThis("Spark Elemental"));
		}
	}

	public SparkElemental(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
		this.addAbility(new Fizzle(state));
	}
}
