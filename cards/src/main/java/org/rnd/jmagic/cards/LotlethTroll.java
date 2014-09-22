package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lotleth Troll")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.TROLL})
@ManaCost("BG")
@ColorIdentity({Color.BLACK, Color.GREEN})
public final class LotlethTroll extends Card
{
	public static final class LotlethTrollAbility1 extends ActivatedAbility
	{
		public LotlethTrollAbility1(GameState state)
		{
			super(state, "Discard a creature card: Put a +1/+1 counter on Lotleth Troll.");

			// Discard a creature card
			this.addCost(discardCards(You.instance(), 1, HasType.instance(Type.CREATURE), "Discard a creature card"));
			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Lotleth Troll"));
		}
	}

	public LotlethTroll(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Discard a creature card: Put a +1/+1 counter on Lotleth Troll.
		this.addAbility(new LotlethTrollAbility1(state));

		// (B): Regenerate Lotleth Troll.
		this.addAbility(new org.rnd.jmagic.abilities.Regenerate.Final(state, "(B)", "Lotleth Troll"));
	}
}
