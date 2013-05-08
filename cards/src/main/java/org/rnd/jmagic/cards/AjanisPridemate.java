package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Ajani's Pridemate")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT, SubType.SOLDIER})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class AjanisPridemate extends Card
{
	public static final class AjanisPridemateAbility0 extends EventTriggeredAbility
	{
		public AjanisPridemateAbility0(GameState state)
		{
			super(state, "Whenever you gain life, you may put a +1/+1 counter on Ajani's Pridemate.");
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.GAIN_LIFE);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			this.addPattern(pattern);
			this.addEffect(youMay(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a +1/+1 counter on Ajani's Pridemate."), "You may put a +1/+1 counter on Ajani's Pridemate."));
		}
	}

	public AjanisPridemate(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever you gain life, you may put a +1/+1 counter on Ajani's
		// Pridemate. (For example, if an effect causes you to gain 3 life, you
		// may put one +1/+1 counter on this creature.)
		this.addAbility(new AjanisPridemateAbility0(state));
	}
}
