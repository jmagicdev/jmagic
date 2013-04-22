package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bonds of Faith")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class BondsofFaith extends Card
{
	public static final class BondsofFaithAbility1 extends StaticAbility
	{
		public BondsofFaithAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +2/+2 as long as it's a Human. Otherwise, it can't attack or block.");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());
			SetGenerator humans = HasSubType.instance(SubType.HUMAN);

			SetGenerator thisHuman = Intersect.instance(enchantedCreature, humans);
			this.addEffectPart(modifyPowerAndToughness(thisHuman, +2, +2));

			SetGenerator thisNonHuman = RelativeComplement.instance(enchantedCreature, humans);

			// can't attack
			ContinuousEffect.Part restrictions = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			restrictions.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(thisNonHuman, Attacking.instance())));
			this.addEffectPart(restrictions);

			// or block,
			ContinuousEffect.Part block = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			block.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(thisNonHuman, Blocking.instance())));
			this.addEffectPart(block);
		}
	}

	public BondsofFaith(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +2/+2 as long as it's a Human. Otherwise, it
		// can't attack or block.
		this.addAbility(new BondsofFaithAbility1(state));
	}
}
