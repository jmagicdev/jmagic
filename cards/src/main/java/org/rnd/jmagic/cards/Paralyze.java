package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Paralyze")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("B")
@Printings({@Printings.Printed(ex = FifthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = FourthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.COMMON), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class Paralyze extends Card
{
	public static final class TapIt extends EventTriggeredAbility
	{
		public TapIt(GameState state)
		{
			super(state, "When Paralyze enters the battlefield, tap enchanted creature.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(tap(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS), "Tap enchanted creature."));
		}
	}

	public static final class KeepItTapped extends StaticAbility
	{
		public KeepItTapped(GameState state)
		{
			super(state, "Enchanted creature doesn't untap during its controller's untap step.");

			EventPattern prohibitPattern = new UntapDuringControllersUntapStep(EnchantedBy.instance(This.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));
			this.addEffectPart(part);
		}
	}

	public static final class OkayMaybeItCanUntapOnceInAWhile extends EventTriggeredAbility
	{
		public OkayMaybeItCanUntapOnceInAWhile(GameState state)
		{
			super(state, "At the beginning of the upkeep of enchanted creature's controller, that player may pay (4). If he or she does, untap the creature.");

			SetGenerator controller = ControllerOf.instance(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS));

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, UpkeepStepOf.instance(controller));
			this.addPattern(pattern);

			EventFactory pay = new EventFactory(EventType.PAY_MANA, "Pay (4)");
			pay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			pay.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("4")));
			pay.parameters.put(EventType.Parameter.PLAYER, controller);

			EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "That player may pay (4). If he or she does, untap the creature.");
			effect.parameters.put(EventType.Parameter.IF, Identity.instance(playerMay(controller, pay, "That player may pay (4)")));
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(untap(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS), "Untap the creature")));
			this.addEffect(effect);
		}
	}

	public Paralyze(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// When Paralyze enters the battlefield, tap enchanted creature.
		this.addAbility(new TapIt(state));

		// Enchanted creature doesn't untap during its controller's untap step.
		this.addAbility(new KeepItTapped(state));

		// At the beginning of the upkeep of enchanted creature's controller,
		// that player may pay (4). If he or she does, untap the creature.
		this.addAbility(new OkayMaybeItCanUntapOnceInAWhile(state));
	}
}
