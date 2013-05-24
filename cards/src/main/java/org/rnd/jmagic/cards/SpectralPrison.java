package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Spectral Prison")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class SpectralPrison extends Card
{
	public static final class SpectralPrisonAbility1 extends StaticAbility
	{
		public SpectralPrisonAbility1(GameState state)
		{
			super(state, "Enchanted creature doesn't untap during its controller's untap step.");

			EventPattern prohibitPattern = new UntapDuringControllersUntapStep(EnchantedBy.instance(This.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));
			this.addEffectPart(part);
		}
	}

	public static final class SpectralPrisonAbility2 extends EventTriggeredAbility
	{
		public SpectralPrisonAbility2(GameState state)
		{
			super(state, "When enchanted creature becomes the target of a spell, sacrifice Spectral Prison.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_TARGET);
			pattern.put(EventType.Parameter.OBJECT, Spells.instance());
			pattern.put(EventType.Parameter.TARGET, EnchantedBy.instance(ABILITY_SOURCE_OF_THIS));
			this.addPattern(pattern);

			this.addEffect(sacrificeThis("Spectral Prison"));
		}
	}

	public SpectralPrison(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature doesn't untap during its controller's untap step.
		this.addAbility(new SpectralPrisonAbility1(state));

		// When enchanted creature becomes the target of a spell, sacrifice
		// Spectral Prison.
		this.addAbility(new SpectralPrisonAbility2(state));
	}
}
