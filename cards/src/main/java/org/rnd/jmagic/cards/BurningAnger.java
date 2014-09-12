package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Burning Anger")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("4R")
@ColorIdentity({Color.RED})
public final class BurningAnger extends Card
{
	public static final class GunToAKnifeFight extends ActivatedAbility
	{
		public GunToAKnifeFight(GameState state)
		{
			super(state, "(T): This creature deals damage equal to its power to target creature or player.");
			this.costsTap = true;
			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(PowerOf.instance(ABILITY_SOURCE_OF_THIS), target, "This creature deals damage equal to its power to target creature or player."));
		}
	}

	public static final class BurningAngerAbility1 extends StaticAbility
	{
		public BurningAngerAbility1(GameState state)
		{
			super(state, "Enchanted creature has \"(T): This creature deals damage equal to its power to target creature or player.\"");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), GunToAKnifeFight.class));
		}
	}

	public BurningAnger(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature has
		// "(T): This creature deals damage equal to its power to target creature or player."
		this.addAbility(new BurningAngerAbility1(state));
	}
}
