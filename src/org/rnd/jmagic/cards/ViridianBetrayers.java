package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Viridian Betrayers")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.ELF})
@ManaCost("1GG")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class ViridianBetrayers extends Card
{
	public static final class ViridianBetrayersAbility0 extends StaticAbility
	{
		public ViridianBetrayersAbility0(GameState state)
		{
			super(state, "Viridian Betrayers has infect as long as an opponent is poisoned.");

			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Infect.class));

			this.canApply = Both.instance(this.canApply, Intersect.instance(OpponentsOf.instance(You.instance()), Poisoned.instance()));
		}
	}

	public ViridianBetrayers(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Viridian Betrayers has infect as long as an opponent is poisoned. (It
		// deals damage to creatures in the form of -1/-1 counters and to
		// players in the form of poison counters.)
		this.addAbility(new ViridianBetrayersAbility0(state));
	}
}
