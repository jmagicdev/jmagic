package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Erdwal Ripper")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("1RR")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class ErdwalRipper extends Card
{
	public static final class ErdwalRipperAbility1 extends EventTriggeredAbility
	{
		public ErdwalRipperAbility1(GameState state)
		{
			super(state, "Whenever Erdwal Ripper deals combat damage to a player, put a +1/+1 counter on it.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a +1/+1 counter on it."));
		}
	}

	public ErdwalRipper(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Whenever Erdwal Ripper deals combat damage to a player, put a +1/+1
		// counter on it.
		this.addAbility(new ErdwalRipperAbility1(state));
	}
}
