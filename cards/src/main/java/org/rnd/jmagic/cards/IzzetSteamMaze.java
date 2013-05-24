package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Izzet Steam Maze")
@Types({Type.PLANE})
@SubTypes({SubType.RAVNICA})
@Printings({@Printings.Printed(ex = Expansion.PLANECHASE, r = Rarity.COMMON)})
@ColorIdentity({})
public final class IzzetSteamMaze extends Card
{
	public static final class SteamCast extends EventTriggeredAbility
	{
		public SteamCast(GameState state)
		{
			super(state, "Whenever a player casts an instant or sorcery spell, that player copies it. The player may choose new targets for the copy.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.withResult(Intersect.instance(Spells.instance(), HasType.instance(Type.INSTANT, Type.SORCERY)));
			this.addPattern(pattern);

			SetGenerator triggerEvent = TriggerEvent.instance(This.instance());
			SetGenerator it = EventResult.instance(triggerEvent);
			SetGenerator thatPlayer = EventParameter.instance(triggerEvent, EventType.Parameter.PLAYER);

			EventFactory factory = new EventFactory(EventType.COPY_SPELL_OR_ABILITY, "That player copies it. The player may choose new targets for the copy.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.OBJECT, it);
			factory.parameters.put(EventType.Parameter.PLAYER, thatPlayer);
			this.addEffect(factory);

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public static final class DiscountChaos extends EventTriggeredAbility
	{
		public DiscountChaos(GameState state)
		{
			super(state, "Whenever you roll (C), instant and sorcery spells you cast this turn cost (3) less to cast.");

			this.addPattern(Planechase.wheneverYouRollChaos());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MANA_COST_REDUCTION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Intersect.instance(HasType.instance(Type.INSTANT, Type.SORCERY), ControlledBy.instance(You.instance(), Stack.instance())));
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.instance(new ManaPool("3")));

			this.addEffect(createFloatingEffect("Instant and sorcery spells you cast this turn cost (3) less to cast.", part));

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public IzzetSteamMaze(GameState state)
	{
		super(state);

		this.addAbility(new SteamCast(state));

		this.addAbility(new DiscountChaos(state));
	}
}
