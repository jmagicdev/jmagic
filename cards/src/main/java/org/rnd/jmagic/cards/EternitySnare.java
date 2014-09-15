package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.UntapDuringControllersUntapStep;

@Name("Eternity Snare")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("5U")
@ColorIdentity({Color.BLUE})
public final class EternitySnare extends Card
{
	public static final class EternitySnareAbility1 extends EventTriggeredAbility
	{
		public EternitySnareAbility1(GameState state)
		{
			super(state, "When Eternity Snare enters the battlefield, draw a card.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(drawACard());
		}
	}

	public static final class EternitySnareAbility2 extends StaticAbility
	{
		public EternitySnareAbility2(GameState state)
		{
			super(state, "Enchanted creature doesn't untap during its controller's untap step.");

			EventPattern prohibitPattern = new UntapDuringControllersUntapStep(EnchantedBy.instance(This.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));
			this.addEffectPart(part);
		}
	}

	public EternitySnare(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// When Eternity Snare enters the battlefield, draw a card.
		this.addAbility(new EternitySnareAbility1(state));

		// Enchanted creature doesn't untap during its controller's untap step.
		this.addAbility(new EternitySnareAbility2(state));
	}
}
