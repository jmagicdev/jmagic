package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fear")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("BB")
@ColorIdentity({Color.BLACK})
public final class Fear extends Card
{
	public static final class FearAbility0 extends StaticAbility
	{
		public FearAbility0(GameState state)
		{
			super(state, "Enchanted creature has fear.");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Fear.class));
		}
	}

	public Fear(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));
		this.addAbility(new FearAbility0(state));
	}
}
