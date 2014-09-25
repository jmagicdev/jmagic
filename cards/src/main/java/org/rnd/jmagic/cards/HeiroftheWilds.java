package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Heir of the Wilds")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class HeiroftheWilds extends Card
{
	public static final class HeiroftheWildsAbility1 extends EventTriggeredAbility
	{
		public HeiroftheWildsAbility1(GameState state)
		{
			super(state, "Whenever Heir of the Wilds attacks, if you control a creature with power 4 or greater, Heir of the Wilds gets +1/+1 until end of turn.");
			this.addPattern(whenThisAttacks());
			this.interveningIf = Ferocious.instance();

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, +1, "Heir of the Wilds gets +1/+1 until end of turn."));
		}
	}

	public HeiroftheWilds(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Deathtouch
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));

		// Ferocious \u2014 Whenever Heir of the Wilds attacks, if you control a
		// creature with power 4 or greater, Heir of the Wilds gets +1/+1 until
		// end of turn.
		this.addAbility(new HeiroftheWildsAbility1(state));
	}
}
