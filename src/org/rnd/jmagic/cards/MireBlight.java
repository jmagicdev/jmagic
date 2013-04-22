package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mire Blight")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class MireBlight extends Card
{
	public static final class Blight extends EventTriggeredAbility
	{
		public Blight(GameState state)
		{
			super(state, "When enchanted creature is dealt damage, destroy it.");
			this.addPattern(whenIsDealtDamage(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS)));
			this.addEffect(destroy(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS), "Destroy it."));
		}
	}

	public MireBlight(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// When enchanted creature is dealt damage, destroy it.
		this.addAbility(new Blight(state));
	}
}
