package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spirit Link")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class SpiritLink extends Card
{
	public static final class LifeGain extends EventTriggeredAbility
	{
		public LifeGain(GameState state)
		{
			super(state, "Whenever enchanted creature deals damage, you gain that much life.");

			this.addPattern(whenDealsDamage(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS)));

			SetGenerator totalDamageDealt = Count.instance(TriggerDamage.instance(This.instance()));
			this.addEffect(gainLife(You.instance(), totalDamageDealt, "You gain that much life."));
		}
	}

	public SpiritLink(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));
		this.addAbility(new LifeGain(state));
	}
}
