package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Invisibility")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("UU")
@ColorIdentity({Color.BLUE})
public final class Invisibility extends Card
{
	public static final class InvisibilityAbility1 extends StaticAbility
	{
		public InvisibilityAbility1(GameState state)
		{
			super(state, "Enchanted creature can't be blocked except by Walls.");
			SetGenerator blockingThis = Blocking.instance(EnchantedBy.instance(This.instance()));
			SetGenerator nonWallBlocking = RelativeComplement.instance(blockingThis, HasSubType.instance(SubType.WALL));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(nonWallBlocking));
			this.addEffectPart(part);
		}
	}

	public Invisibility(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature can't be blocked except by Walls.
		this.addAbility(new InvisibilityAbility1(state));
	}
}
