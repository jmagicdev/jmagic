package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Stab Wound")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class StabWound extends Card
{
	public static final class StabWoundAbility1 extends StaticAbility
	{
		public StabWoundAbility1(GameState state)
		{
			super(state, "Enchanted creature gets -2/-2.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), -2, -2));
		}
	}

	public static final class StabWoundAbility2 extends EventTriggeredAbility
	{
		public StabWoundAbility2(GameState state)
		{
			super(state, "At the beginning of the upkeep of enchanted creature's controller, that player loses 2 life.");
			SetGenerator controllerOfEnchantedCreature = ControllerOf.instance(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS));

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, UpkeepStepOf.instance(controllerOfEnchantedCreature));
			this.addPattern(pattern);

			this.addEffect(loseLife(controllerOfEnchantedCreature, 2, "That player loses 2 life."));
		}
	}

	public StabWound(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets -2/-2.
		this.addAbility(new StabWoundAbility1(state));

		// At the beginning of the upkeep of enchanted creature's controller,
		// that player loses 2 life.
		this.addAbility(new StabWoundAbility2(state));
	}
}
