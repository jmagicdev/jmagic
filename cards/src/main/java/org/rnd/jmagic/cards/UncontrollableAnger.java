package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Uncontrollable Anger")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2RR")
@ColorIdentity({Color.RED})
public final class UncontrollableAnger extends Card
{
	public static final class Angry extends StaticAbility
	{
		public Angry(GameState state)
		{
			super(state, "Enchanted creature gets +2/+2 and attacks each turn if able.");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(enchantedCreature, 2, 2));

			ContinuousEffect.Part part2 = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_REQUIREMENT);
			part2.parameters.put(ContinuousEffectType.Parameter.ATTACKING, enchantedCreature);
			this.addEffectPart(part2);
		}
	}

	public UncontrollableAnger(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		this.addAbility(new Angry(state));
	}
}
