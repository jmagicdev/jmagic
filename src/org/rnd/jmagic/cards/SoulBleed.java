package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Soul Bleed")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class SoulBleed extends Card
{
	public static final class SoulBleedAbility extends EventTriggeredAbility
	{
		public SoulBleedAbility(GameState state)
		{
			super(state, "At the beginning of the upkeep of enchanted creature's controller, that player loses 1 life.");

			SetGenerator enchantedCreature = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);
			SetGenerator controller = ControllerOf.instance(enchantedCreature);

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, UpkeepStepOf.instance(controller));
			this.addPattern(pattern);

			this.addEffect(loseLife(controller, 1, "That player loses 1 life."));
		}
	}

	public SoulBleed(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));
		this.addAbility(new SoulBleedAbility(state));
	}
}
