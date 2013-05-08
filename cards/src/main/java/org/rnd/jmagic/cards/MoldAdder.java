package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Mold Adder")
@Types({Type.CREATURE})
@SubTypes({SubType.FUNGUS, SubType.SNAKE})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class MoldAdder extends Card
{
	public static final class BigBruises extends EventTriggeredAbility
	{
		public BigBruises(GameState state)
		{
			super(state, "Whenever an opponent casts a blue or black spell, you may put a +1/+1 counter on Mold Adder.");

			// TODO : use becomes_played
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
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
