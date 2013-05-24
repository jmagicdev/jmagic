package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spirit Away")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("5UU")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class SpiritAway extends Card
{
	public static final class SpiritAwayAbility1 extends StaticAbility
	{
		public SpiritAwayAbility1(GameState state)
		{
			super(state, "You control enchanted creature.");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, enchantedCreature);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			this.addEffectPart(part);
		}
	}

	public static final class SpiritAwayAbility2 extends StaticAbility
	{
		public SpiritAwayAbility2(GameState state)
		{
			super(state, "Enchanted creature gets +2/+2 and has flying.");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());
			this.addEffectPart(modifyPowerAndToughness(enchantedCreature, +2, +2));
			this.addEffectPart(addAbilityToObject(enchantedCreature, org.rnd.jmagic.abilities.keywords.Flying.class));
		}
	}

	public SpiritAway(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// You control enchanted creature.
		this.addAbility(new SpiritAwayAbility1(state));

		// Enchanted creature gets +2/+2 and has flying.
		this.addAbility(new SpiritAwayAbility2(state));
	}
}
