package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Unflinching Courage")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1GW")
@Printings({@Printings.Printed(ex = Expansion.DRAGONS_MAZE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class UnflinchingCourage extends Card
{
	public static final class UnflinchingCourageAbility1 extends StaticAbility
	{
		public UnflinchingCourageAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +2/+2 and has trample and lifelink.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +2, +2));
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Trample.class, org.rnd.jmagic.abilities.keywords.Lifelink.class));
		}
	}

	public UnflinchingCourage(GameState state)
	{
		super(state);


		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +2/+2 and has trample and lifelink.
		this.addAbility(new UnflinchingCourageAbility1(state));
	}
}
