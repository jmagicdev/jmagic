package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Treetop Bracers")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.NEMESIS, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class TreetopBracers extends Card
{
	public static final class Tarzan extends StaticAbility
	{
		public Tarzan(GameState state)
		{
			super(state, "Enchanted creature gets +1/+1 and can't be blocked except by creatures with flying.");

			SetGenerator enchantedCreature = EquippedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(enchantedCreature, 1, 1));

			ContinuousEffect.Part part2 = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part2.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(RelativeComplement.instance(Blocking.instance(enchantedCreature), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class))));
			this.addEffectPart(part2);
		}
	}

	public TreetopBracers(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));
		this.addAbility(new Tarzan(state));
	}
}
