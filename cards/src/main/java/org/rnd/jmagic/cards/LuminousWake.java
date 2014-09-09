package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Luminous Wake")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class LuminousWake extends Card
{
	public static final class LuminousLife extends EventTriggeredAbility
	{
		public LuminousLife(GameState state)
		{
			super(state, "Whenever enchanted creature attacks or blocks, you gain 4 life.");

			SetGenerator enchantedCreature = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);

			SimpleEventPattern attack = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			attack.put(EventType.Parameter.OBJECT, enchantedCreature);
			this.addPattern(attack);

			SimpleEventPattern block = new SimpleEventPattern(EventType.DECLARE_ONE_BLOCKER);
			block.put(EventType.Parameter.OBJECT, enchantedCreature);
			this.addPattern(block);

			this.addEffect(gainLife(You.instance(), 4, "You gain 4 life."));
		}
	}

	public LuminousWake(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Whenever enchanted creature attacks or blocks, you gain 4 life.
		this.addAbility(new LuminousLife(state));
	}
}
