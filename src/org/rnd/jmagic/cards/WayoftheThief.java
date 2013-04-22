package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Way of the Thief")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class WayoftheThief extends Card
{
	public static final class WayoftheThiefAbility1 extends StaticAbility
	{
		public WayoftheThiefAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +2/+2.");
			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());
			this.addEffectPart(modifyPowerAndToughness(enchantedCreature, +2, +2));
		}
	}

	public static final class WayoftheThiefAbility2 extends StaticAbility
	{
		public WayoftheThiefAbility2(GameState state)
		{
			super(state, "Enchanted creature is unblockable as long as you control a Gate.");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());
			this.addEffectPart(unblockable(enchantedCreature));

			SetGenerator youControlAGate = Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.GATE));
			this.canApply = Both.instance(this.canApply, youControlAGate);
		}
	}

	public WayoftheThief(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +2/+2.
		this.addAbility(new WayoftheThiefAbility1(state));

		// Enchanted creature is unblockable as long as you control a Gate.
		this.addAbility(new WayoftheThiefAbility2(state));
	}
}
