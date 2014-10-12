package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sightless Brawler")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class SightlessBrawler extends Card
{
	public static final class SightlessBrawlerAbility1 extends StaticAbility
	{
		public SightlessBrawlerAbility1(GameState state)
		{
			super(state, "Sightless Brawler can't attack alone.");

			SetGenerator thisIsAttacking = Intersect.instance(This.instance(), Attacking.instance());
			SetGenerator oneAttacker = Intersect.instance(numberGenerator(1), Count.instance(Attacking.instance()));
			SetGenerator attackingAlone = Both.instance(thisIsAttacking, oneAttacker);
			ContinuousEffect.Part attacking = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			attacking.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(attackingAlone));
			this.addEffectPart(attacking);
		}
	}

	public static final class SightlessBrawlerAbility2 extends StaticAbility
	{
		public SightlessBrawlerAbility2(GameState state)
		{
			super(state, "Enchanted creature gets +3/+2 and can't attack alone.");

			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +3, +2));

			SetGenerator thisIsAttacking = Intersect.instance(EnchantedBy.instance(This.instance()), Attacking.instance());
			SetGenerator oneAttacker = Intersect.instance(numberGenerator(1), Count.instance(Attacking.instance()));
			SetGenerator attackingAlone = Both.instance(thisIsAttacking, oneAttacker);
			ContinuousEffect.Part attacking = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			attacking.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(attackingAlone));
			this.addEffectPart(attacking);
		}
	}

	public SightlessBrawler(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Bestow (4)(W) (If you cast this card for its bestow cost, it's an
		// Aura spell with enchant creature. It becomes a creature again if it's
		// not attached to a creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bestow(state, "(4)(W)"));

		// Sightless Brawler can't attack alone.
		this.addAbility(new SightlessBrawlerAbility1(state));

		// Enchanted creature gets +3/+2 and can't attack alone.
		this.addAbility(new SightlessBrawlerAbility2(state));
	}
}
