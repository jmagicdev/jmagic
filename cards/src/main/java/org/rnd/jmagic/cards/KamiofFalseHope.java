package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Kami of False Hope")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = BetrayersOfKamigawa.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class KamiofFalseHope extends Card
{
	public static final class KamiofFalseHopeAbility0 extends ActivatedAbility
	{
		public KamiofFalseHopeAbility0(GameState state)
		{
			super(state, "Sacrifice Kami of False Hope: Prevent all combat damage that would be dealt this turn.");
			this.addCost(sacrificeThis("Kami of False Hope"));
			this.addEffect(createFloatingReplacement(new org.rnd.jmagic.abilities.PreventCombatDamage(this.game), "Prevent all combat damage that would be dealt this turn."));
		}
	}

	public KamiofFalseHope(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Sacrifice Kami of False Hope: Prevent all combat damage that would be
		// dealt this turn.
		this.addAbility(new KamiofFalseHopeAbility0(state));
	}
}
