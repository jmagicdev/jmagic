package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hypnotic Siren")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.SIREN})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class HypnoticSiren extends Card
{
	public static final class HypnoticSirenAbility3 extends StaticAbility
	{
		public HypnoticSirenAbility3(GameState state)
		{
			super(state, "Enchanted creature gets +1/+1 and has flying.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +1, +1));
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Flying.class));
		}
	}

	public HypnoticSiren(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Bestow (5)(U)(U) (If you cast this card for its bestow cost, it's an
		// Aura spell with enchant creature. It becomes a creature again if it's
		// not attached to a creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bestow(state, "(5)(U)(U)"));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// You control enchanted creature.
		this.addAbility(new org.rnd.jmagic.abilities.YouControlEnchantedCreature(state));

		// Enchanted creature gets +1/+1 and has flying.
		this.addAbility(new HypnoticSirenAbility3(state));
	}
}
