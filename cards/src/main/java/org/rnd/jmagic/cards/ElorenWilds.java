package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Eloren Wilds")
@Types({Type.PLANE})
@SubTypes({SubType.SHANDALAR})
@Printings({@Printings.Printed(ex = Expansion.PLANECHASE, r = Rarity.COMMON)})
@ColorIdentity({})
public final class ElorenWilds extends Card
{
	public static final class ElorenMana extends EventTriggeredAbility
	{
		public ElorenMana(GameState state)
		{
			super(state, "Whenever a player taps a permanent for mana, that player adds one mana to his or her mana pool of any type that permanent produced.");

			this.addPattern(tappedForMana(Players.instance(), permanents()));

			SetGenerator triggerEvent = TriggerEvent.instance(This.instance());
			SetGenerator thatPlayer = EventParameter.instance(triggerEvent, EventType.Parameter.PLAYER);
			SetGenerator theAbility = EventResult.instance(triggerEvent);

			EventFactory factory = new EventFactory(EventType.ADD_MANA, "That player adds one mana to his or her mana pool of any type that permanent produced.");
			factory.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			factory.parameters.put(EventType.Parameter.MANA, ManaTypeOf.instance(ManaAddedBy.instance(theAbility)));
			factory.parameters.put(EventType.Parameter.PLAYER, thatPlayer);
			this.addEffect(factory);

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public static final class SilentChaos extends EventTriggeredAbility
	{
		public SilentChaos(GameState state)
		{
			super(state, "Whenever you roll (C), target player can't cast spells until a player planeswalks.");

			state.ensureTracker(new Planechase.UntilAPlayerPlaneswalks());

			this.addPattern(Planechase.wheneverYouRollChaos());

			Target target = this.addTarget(Players.instance(), "target player");

			SimpleEventPattern prohibitPattern = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			prohibitPattern.put(EventType.Parameter.OBJECT, SetPattern.CASTABLE);
			prohibitPattern.put(EventType.Parameter.PLAYER, new SimpleSetPattern(targetedBy(target)));
			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));

			EventType.ParameterMap effectParameters = new EventType.ParameterMap();
			effectParameters.put(EventType.Parameter.CAUSE, This.instance());
			effectParameters.put(EventType.Parameter.EFFECT, Identity.instance(part));
			this.addEffect(new EventFactory(Planechase.CREATE_FCE_UNTIL_A_PLAYER_PLANESWALKS, effectParameters, "Target player can't cast spells until a player planeswalks."));

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public ElorenWilds(GameState state)
	{
		super(state);

		this.addAbility(new ElorenMana(state));

		this.addAbility(new SilentChaos(state));
	}
}
