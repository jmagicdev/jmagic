package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.UntapDuringControllersUntapStep;

@Name("Singing Bell Strike")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class SingingBellStrike extends Card
{
	public static final class SingingBellStrikeAbility1 extends EventTriggeredAbility
	{
		public SingingBellStrikeAbility1(GameState state)
		{
			super(state, "When Singing Bell Strike enters the battlefield, tap enchanted creature.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(tap(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS), "Tap enchanted creature."));
		}
	}

	public static final class SingingBellStrikeAbility2 extends StaticAbility
	{
		public SingingBellStrikeAbility2(GameState state)
		{
			super(state, "Enchanted creature doesn't untap during its controller's untap step.");

			EventPattern prohibitPattern = new UntapDuringControllersUntapStep(EnchantedBy.instance(This.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));
			this.addEffectPart(part);
		}
	}

	public static final class UntapSelf extends ActivatedAbility
	{
		public UntapSelf(GameState state)
		{
			super(state, "(6): Untap this creature.");
			this.setManaCost(new ManaPool("(6)"));
			this.addEffect(untap(ABILITY_SOURCE_OF_THIS, "Untap this creature."));
		}
	}

	public static final class SingingBellStrikeAbility3 extends StaticAbility
	{
		public SingingBellStrikeAbility3(GameState state)
		{
			super(state, "Enchanted creature has \"(6): Untap this creature.\"");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), UntapSelf.class));
		}
	}

	public SingingBellStrike(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// When Singing Bell Strike enters the battlefield, tap enchanted
		// creature.
		this.addAbility(new SingingBellStrikeAbility1(state));

		// Enchanted creature doesn't untap during its controller's untap step.
		this.addAbility(new SingingBellStrikeAbility2(state));

		// Enchanted creature has "(6): Untap this creature."
		this.addAbility(new SingingBellStrikeAbility3(state));
	}
}
