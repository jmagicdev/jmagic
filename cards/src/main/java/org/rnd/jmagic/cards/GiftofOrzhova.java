package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gift of Orzhova")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1(W/B)(W/B)")
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class GiftofOrzhova extends Card
{
	public static final class GiftofOrzhovaAbility1 extends StaticAbility
	{
		public GiftofOrzhovaAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +1/+1 and has flying and lifelink.");

			SetGenerator enchanted = EnchantedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(enchanted, +1, +1));
			this.addEffectPart(addAbilityToObject(enchanted, org.rnd.jmagic.abilities.keywords.Flying.class, org.rnd.jmagic.abilities.keywords.Lifelink.class));
		}
	}

	public GiftofOrzhova(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +1/+1 and has flying and lifelink.
		this.addAbility(new GiftofOrzhovaAbility1(state));
	}
}
