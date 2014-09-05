package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Pacifism")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Onslaught.class, r = Rarity.COMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = UrzasSaga.class, r = Rarity.COMMON), @Printings.Printed(ex = Tempest.class, r = Rarity.COMMON), @Printings.Printed(ex = Mirage.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class Pacifism extends Card
{
	public static final class Pacifier extends StaticAbility
	{
		public Pacifier(GameState state)
		{
			super(state, "Enchanted creature can't attack or block.");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());

			ContinuousEffect.Part part1 = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			part1.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Attacking.instance(), enchantedCreature)));
			this.addEffectPart(part1);

			ContinuousEffect.Part part2 = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part2.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Blocking.instance(), enchantedCreature)));
			this.addEffectPart(part2);
		}
	}

	public Pacifism(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));
		this.addAbility(new Pacifier(state));
	}
}
