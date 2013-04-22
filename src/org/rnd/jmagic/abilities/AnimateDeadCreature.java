package org.rnd.jmagic.abilities;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;
import static org.rnd.jmagic.Convenience.*;

/** Goes with {@link EnchantAnimatedCreature} */
public final class AnimateDeadCreature extends EventTriggeredAbility
{
	private final String cardName;
	private final Class<? extends org.rnd.jmagic.abilities.keywords.Enchant> enchantDead;
	private final Class<? extends org.rnd.jmagic.abilities.keywords.Enchant> enchantAnimated;
	private final boolean tapped;

	public static final class Tracker extends org.rnd.jmagic.abilities.EnchantAnimatedCreature.ReanimationTracker
	{
		@Override
		protected Class<? extends TriggeredAbility> triggerClass()
		{
			return AnimateDeadCreature.class;
		}
	}

	/**
	 * @param state
	 * @param cardName The name of the card doing the animating
	 * @param enchantDead The enchant ability to lose
	 */
	public AnimateDeadCreature(GameState state, String cardName, Class<? extends org.rnd.jmagic.abilities.keywords.Enchant> enchantDead, Class<? extends org.rnd.jmagic.abilities.keywords.Enchant> enchantAnimated, boolean tapped)
	{
		super(state, "When " + cardName + " enters the battlefield, if it's on the battlefield, it loses \"enchant creature card in a graveyard\" and gains \"enchant creature put onto the battlefield with " + cardName + ".\" Return enchanted creature card to the battlefield under your control " + (tapped ? "tapped " : "") + "and attach " + cardName + " to it. When " + cardName + " leaves the battlefield, that creature's controller sacrifices it.");
		this.cardName = cardName;
		this.enchantDead = enchantDead;
		this.enchantAnimated = enchantAnimated;
		this.tapped = tapped;

		this.addPattern(whenThisEntersTheBattlefield());

		this.interveningIf = Intersect.instance(InZone.instance(Battlefield.instance()), ABILITY_SOURCE_OF_THIS);

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_ABILITY_FROM_OBJECT);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
		part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(enchantDead));

		this.addEffect(createFloatingEffect("It loses \"enchant creature card in a graveyard\" and gains \"enchant creature put onto the battlefield with " + cardName + ".\"", part, addAbilityToObject(ABILITY_SOURCE_OF_THIS, enchantAnimated)));

		EventFactory putOntoBattlefield = putOntoBattlefield(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS), "Return enchanted creature card to the battlefield under your control.", tapped);
		this.addEffect(putOntoBattlefield);

		SetGenerator thatCreature = NewObjectOf.instance(EffectResult.instance(putOntoBattlefield));
		this.addEffect(attach(ABILITY_SOURCE_OF_THIS, thatCreature, "Attach " + cardName + " to it."));

		{
			thatCreature = delayedTriggerContext(thatCreature);

			SimpleZoneChangePattern pattern = new SimpleZoneChangePattern(Battlefield.instance(), null, ABILITY_SOURCE_OF_THIS, true);

			EventFactory sacrifice = new EventFactory(EventType.SACRIFICE_PERMANENTS, "That creature's controller sacrifices it.");
			sacrifice.parameters.put(EventType.Parameter.CAUSE, This.instance());
			sacrifice.parameters.put(EventType.Parameter.PLAYER, ControllerOf.instance(thatCreature));
			sacrifice.parameters.put(EventType.Parameter.PERMANENT, thatCreature);

			EventFactory delayed = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "When " + cardName + " leaves the battlefield, that creature's controller sacrifices it.");
			delayed.parameters.put(EventType.Parameter.CAUSE, This.instance());
			delayed.parameters.put(EventType.Parameter.ZONE_CHANGE, Identity.instance(pattern));
			delayed.parameters.put(EventType.Parameter.EFFECT, Identity.instance(sacrifice));
			this.addEffect(delayed);
		}
	}

	@Override
	public AnimateDeadCreature create(Game game)
	{
		return new AnimateDeadCreature(game.physicalState, this.cardName, this.enchantDead, this.enchantAnimated, this.tapped);
	}
}