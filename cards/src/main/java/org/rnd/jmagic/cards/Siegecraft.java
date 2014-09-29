package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Siegecraft")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class Siegecraft extends Card
{
	public static final class SiegecraftAbility1 extends StaticAbility
	{
		public SiegecraftAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +2/+4.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +2, +4));
		}
	}

	public Siegecraft(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +2/+4.
		this.addAbility(new SiegecraftAbility1(state));
	}
}
