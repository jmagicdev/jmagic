package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Corrupted Conscience")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class CorruptedConscience extends Card
{
	public static final class CorruptedConscienceAbility2 extends StaticAbility
	{
		public CorruptedConscienceAbility2(GameState state)
		{
			super(state, "Enchanted creature has infect.");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Infect.class));
		}
	}

	public CorruptedConscience(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// You control enchanted creature.
		this.addAbility(new org.rnd.jmagic.abilities.YouControlEnchantedCreature(state));

		// Enchanted creature has infect. (It deals damage to creatures in the
		// form of -1/-1 counters and to players in the form of poison
		// counters.)
		this.addAbility(new CorruptedConscienceAbility2(state));
	}
}
