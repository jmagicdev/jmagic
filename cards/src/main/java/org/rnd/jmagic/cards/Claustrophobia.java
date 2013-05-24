package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Claustrophobia")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Claustrophobia extends Card
{
	public static final class ClaustrophobiaAbility1 extends EventTriggeredAbility
	{
		public ClaustrophobiaAbility1(GameState state)
		{
			super(state, "When Claustrophobia enters the battlefield, tap enchanted creature.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator enchantedCreature = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);
			this.addEffect(tap(enchantedCreature, "Tap enchanted creature."));
		}
	}

	public static final class ClaustrophobiaAbility2 extends StaticAbility
	{
		public ClaustrophobiaAbility2(GameState state)
		{
			super(state, "Enchanted creature doesn't untap during its controller's untap step.");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());
			EventPattern untapping = new UntapDuringControllersUntapStep(enchantedCreature);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(untapping));
			this.addEffectPart(part);
		}
	}

	public Claustrophobia(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// When Claustrophobia enters the battlefield, tap enchanted creature.
		this.addAbility(new ClaustrophobiaAbility1(state));

		// Enchanted creature doesn't untap during its controller's untap step.
		this.addAbility(new ClaustrophobiaAbility2(state));
	}
}
