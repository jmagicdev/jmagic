package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Dance of the Dead")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.ICE_AGE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class DanceoftheDead extends Card
{
	public static final class EnchantDeadCreature extends org.rnd.jmagic.abilities.keywords.Enchant
	{
		public EnchantDeadCreature(GameState state)
		{
			super(state, "creature card in a graveyard", Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(Players.instance()))));
		}
	}

	public static final class EnchantAnimatedCreature extends org.rnd.jmagic.abilities.EnchantAnimatedCreature
	{
		public EnchantAnimatedCreature(GameState state)
		{
			super(state, "Dance of the Dead");
		}
	}

	public static final class DanceoftheDeadAbility2 extends StaticAbility
	{
		public DanceoftheDeadAbility2(GameState state)
		{
			super(state, "Enchanted creature gets +1/+1 and doesn't untap during its controller's untap step.");

			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +1, +1));

			EventPattern prohibitPattern = new UntapDuringControllersUntapStep(EnchantedBy.instance(This.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));
			this.addEffectPart(part);
		}
	}

	public static final class DanceoftheDeadAbility3 extends EventTriggeredAbility
	{
		public DanceoftheDeadAbility3(GameState state)
		{
			super(state, "At the beginning of the upkeep of enchanted creature's controller, that player may pay (1)(B). If he or she does, untap that creature.");

			SetGenerator controller = ControllerOf.instance(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS));

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, UpkeepStepOf.instance(controller));
			this.addPattern(pattern);

			EventFactory pay = new EventFactory(EventType.PAY_MANA, "Pay (1)(B)");
			pay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			pay.parameters.put(EventType.Parameter.COST, Identity.instance(new ManaPool("1B")));
			pay.parameters.put(EventType.Parameter.PLAYER, controller);

			EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "That player may pay (1)(B). If he or she does, untap that creature.");
			effect.parameters.put(EventType.Parameter.IF, Identity.instance(playerMay(controller, pay, "That player may pay (1)(B)")));
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(untap(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS), "Untap that creature")));
			this.addEffect(effect);
		}
	}

	public DanceoftheDead(GameState state)
	{
		super(state);

		// Enchant creature card in a graveyard
		this.addAbility(new EnchantDeadCreature(state));

		// When Dance of the Dead enters the battlefield, if it's on the
		// battlefield, it loses "enchant creature card in a graveyard" and
		// gains
		// "enchant creature put onto the battlefield with Dance of the Dead."
		// Return enchanted creature card to the battlefield tapped under your
		// control and attach Dance of the Dead to it. When Dance of the Dead
		// leaves the battlefield, that creature's controller sacrifices it.
		this.addAbility(new org.rnd.jmagic.abilities.AnimateDeadCreature(state, this.getName(), EnchantDeadCreature.class, EnchantAnimatedCreature.class, true));

		// Enchanted creature gets +1/+1 and doesn't untap during its
		// controller's untap step.
		this.addAbility(new DanceoftheDeadAbility2(state));

		// At the beginning of the upkeep of enchanted creature's controller,
		// that player may pay (1)(B). If he or she does, untap that creature.
		this.addAbility(new DanceoftheDeadAbility3(state));
	}
}
