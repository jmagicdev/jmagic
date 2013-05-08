package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Parallax Dementia")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.NEMESIS, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class ParallaxDementia extends Card
{
	public static final class ParallaxDementiaAbility2 extends StaticAbility
	{
		public ParallaxDementiaAbility2(GameState state)
		{
			super(state, "Enchanted creature gets +3/+2.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +3, +2));
		}
	}

	public static final class ParallaxDementiaAbility3 extends EventTriggeredAbility
	{
		public ParallaxDementiaAbility3(GameState state)
		{
			super(state, "When Parallax Dementia leaves the battlefield, destroy enchanted creature. That creature can't be regenerated.");
			this.addPattern(whenThisLeavesTheBattlefield());

			SetGenerator enchantedCreature = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);
			this.addEffects(bury(this, enchantedCreature, "Destroy enchanted creature. That creature can't be regenerated."));
		}
	}

	public ParallaxDementia(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Fading 1
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Fading(state, 1));

		// Enchanted creature gets +3/+2.
		this.addAbility(new ParallaxDementiaAbility2(state));

		// When Parallax Dementia leaves the battlefield, destroy enchanted
		// creature. That creature can't be regenerated.
		this.addAbility(new ParallaxDementiaAbility3(state));
	}
}
