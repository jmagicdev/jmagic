package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Fathom Mage")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("2GU")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class FathomMage extends Card
{
	public static final class FathomMageAbility1 extends EventTriggeredAbility
	{
		public FathomMageAbility1(GameState state)
		{
			super(state, "Whenever a +1/+1 counter is placed on Fathom Mage, you may draw a card.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.PUT_ONE_COUNTER);
			pattern.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			pattern.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));
			this.addPattern(pattern);

			this.addEffect(youMay(drawACard()));
		}
	}

	public FathomMage(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Evolve (Whenever a creature enters the battlefield under your
		// control, if that creature has greater power or toughness than this
		// creature, put a +1/+1 counter on this creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Evolve(state));

		// Whenever a +1/+1 counter is placed on Fathom Mage, you may draw a
		// card.
		this.addAbility(new FathomMageAbility1(state));
	}
}
