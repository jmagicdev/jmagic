package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Godhunter Octopus")
@Types({Type.CREATURE})
@SubTypes({SubType.OCTOPUS})
@ManaCost("5U")
@ColorIdentity({Color.BLUE})
public final class GodhunterOctopus extends Card
{
	public static final class GodhunterOctopusAbility0 extends StaticAbility
	{
		public GodhunterOctopusAbility0(GameState state)
		{
			super(state, "Godhunter Octopus can't attack unless defending player controls an enchantment or an enchanted permanent.");

			ContinuousEffect.Part part1 = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			part1.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Attacking.instance(), This.instance())));
			this.addEffectPart(part1);

			SetGenerator enchanted = EnchantedBy.instance(HasSubType.instance(SubType.AURA));
			SetGenerator enchantmentOrEnchanted = Union.instance(EnchantmentPermanents.instance(), enchanted);
			SetGenerator enemyStuff = Intersect.instance(ControlledBy.instance(OpponentsOf.instance(You.instance())), enchantmentOrEnchanted);
			this.canApply = Both.instance(this.canApply, Not.instance(enemyStuff));
		}
	}

	public GodhunterOctopus(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Godhunter Octopus can't attack unless defending player controls an
		// enchantment or an enchanted permanent.
		this.addAbility(new GodhunterOctopusAbility0(state));
	}
}
