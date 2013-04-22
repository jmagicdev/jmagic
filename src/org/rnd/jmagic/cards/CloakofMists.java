package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cloak of Mists")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class CloakofMists extends Card
{
	public static final class MakeUnblockable extends StaticAbility
	{
		public MakeUnblockable(GameState state)
		{
			super(state, "Enchanted creature is unblockable.");
			this.addEffectPart(unblockable(EnchantedBy.instance(This.instance())));
		}
	}

	public CloakofMists(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature is unblockable.
		this.addAbility(new MakeUnblockable(state));
	}
}
