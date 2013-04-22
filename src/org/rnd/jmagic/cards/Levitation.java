package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Levitation")
@Types({Type.ENCHANTMENT})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.URZAS_LEGACY, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Levitation extends Card
{
	public static final class GrantFlying extends StaticAbility
	{
		public GrantFlying(GameState state)
		{
			super(state, "Creatures you control have flying.");

			this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Flying.class));
		}
	}

	public Levitation(GameState state)
	{
		super(state);

		this.addAbility(new GrantFlying(state));
	}
}
