package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Crippling Blight")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class CripplingBlight extends Card
{
	public static final class CripplingBlightAbility1 extends StaticAbility
	{
		public CripplingBlightAbility1(GameState state)
		{
			super(state, "Enchanted creature gets -1/-1 and can't block.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), (-1), (-1)));
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), new org.rnd.jmagic.abilities.CantBlock.Factory("Enchanted creature")));
		}
	}

	public CripplingBlight(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets -1/-1 and can't block.
		this.addAbility(new CripplingBlightAbility1(state));
	}
}
