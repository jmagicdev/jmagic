package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Fanatic of Xenagos")
@Types({Type.CREATURE})
@SubTypes({SubType.CENTAUR, SubType.WARRIOR})
@ManaCost("1RG")
@ColorIdentity({Color.RED, Color.GREEN})
public final class FanaticofXenagos extends Card
{
	public static final class FanaticofXenagosAbility2 extends EventTriggeredAbility
	{
		public FanaticofXenagosAbility2(GameState state)
		{
			super(state, "When Fanatic of Xenagos enters the battlefield, if tribute wasn't paid, it gets +1/+1 and gains haste until end of turn.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.interveningIf = org.rnd.jmagic.abilities.keywords.Tribute.WasntPaid.instance();
			this.addEffect(ptChangeAndAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, +1, "Fanatic of Xenagos gets +1/+1 and gains haste until end of turn.", org.rnd.jmagic.abilities.keywords.Haste.class));
		}
	}

	public FanaticofXenagos(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Tribute 1 (As this creature enters the battlefield, an opponent of
		// your choice may place a +1/+1 counter on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Tribute(state, 1));

		// When Fanatic of Xenagos enters the battlefield, if tribute wasn't
		// paid, it gets +1/+1 and gains haste until end of turn.
		this.addAbility(new FanaticofXenagosAbility2(state));
	}
}
