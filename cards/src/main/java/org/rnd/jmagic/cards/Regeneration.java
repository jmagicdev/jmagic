package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Regeneration")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class Regeneration extends Card
{
	public static final class RegenerateCreature extends ActivatedAbility
	{
		public RegenerateCreature(GameState state)
		{
			super(state, "(G): Regenerate enchanted creature.");

			this.setManaCost(new ManaPool("G"));

			SetGenerator enchantedCreature = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);
			this.addEffect(regenerate(enchantedCreature, "Regenerate enchanted creature."));
		}
	}

	public Regeneration(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));
		this.addAbility(new RegenerateCreature(state));
	}
}
