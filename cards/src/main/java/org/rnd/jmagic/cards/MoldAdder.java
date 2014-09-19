package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Mold Adder")
@Types({Type.CREATURE})
@SubTypes({SubType.FUNGUS, SubType.SNAKE})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class MoldAdder extends Card
{
	public static final class BigBruises extends EventTriggeredAbility
	{
		public BigBruises(GameState state)
		{
			super(state, "Whenever an opponent casts a blue or black spell, you may put a +1/+1 counter on Mold Adder.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
			pattern.put(EventType.Parameter.OBJECT, Intersect.instance(Spells.instance(), HasColor.instance(Color.BLUE, Color.BLACK)));
			this.addPattern(pattern);

			this.addEffect(youMay(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a +1/+1 counter on Mold Adder"), "You may put a +1/+1 counter on Mold Adder."));
		}
	}

	public MoldAdder(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new BigBruises(state));
	}
}
