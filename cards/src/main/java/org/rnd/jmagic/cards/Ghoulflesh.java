package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ghoulflesh")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class Ghoulflesh extends Card
{
	public static final class GhoulfleshAbility1 extends StaticAbility
	{
		public GhoulfleshAbility1(GameState state)
		{
			super(state, "Enchanted creature gets -1/-1 and is a black Zombie in addition to its other colors and types.");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(enchantedCreature, -1, -1));

			ContinuousEffect.Part color = new ContinuousEffect.Part(ContinuousEffectType.ADD_COLOR);
			color.parameters.put(ContinuousEffectType.Parameter.COLOR, Identity.instance(Color.BLACK));
			color.parameters.put(ContinuousEffectType.Parameter.OBJECT, enchantedCreature);
			this.addEffectPart(color);

			ContinuousEffect.Part type = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
			type.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(SubType.ZOMBIE));
			type.parameters.put(ContinuousEffectType.Parameter.OBJECT, enchantedCreature);
			this.addEffectPart(type);
		}
	}

	public Ghoulflesh(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets -1/-1 and is a black Zombie in addition to
		// its other colors and types.
		this.addAbility(new GhoulfleshAbility1(state));
	}
}
