package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Madcap Skills")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class MadcapSkills extends Card
{
	public static final class MadcapSkillsAbility1 extends StaticAbility
	{
		public MadcapSkillsAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +3/+0 and can't be blocked except by two or more creatures.");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(enchantedCreature, +3, +0));

			SetGenerator restriction = Intersect.instance(Count.instance(Blocking.instance(enchantedCreature)), numberGenerator(1));
			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(restriction));
			this.addEffectPart(part);
		}
	}

	public MadcapSkills(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +3/+0 and can't be blocked except by two or
		// more creatures.
		this.addAbility(new MadcapSkillsAbility1(state));
	}
}
