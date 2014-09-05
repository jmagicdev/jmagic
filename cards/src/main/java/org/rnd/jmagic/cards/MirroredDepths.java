package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.gameTypes.*;

@Name("Mirrored Depths")
@Types({Type.PLANE})
@SubTypes({SubType.KARSUS})
@Printings({@Printings.Printed(ex = org.rnd.jmagic.expansions.Planechase.class, r = Rarity.SPECIAL)})
@ColorIdentity({})
public final class MirroredDepths extends Card
{
	public static final class MirrorsHateYou extends EventTriggeredAbility
	{
		public MirrorsHateYou(GameState state)
		{
			super(state, "Whenever a player casts a spell, that player flips a coin.  If he or she loses the flip, counter that spell.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.withResult(Spells.instance());
			this.addPattern(pattern);

			SetGenerator thatSpell = EventResult.instance(TriggerEvent.instance(This.instance()));
			SetGenerator thatPlayer = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.PLAYER);

			EventFactory flip = new EventFactory(EventType.FLIP_COIN, "That player flips a coin.");
			flip.parameters.put(EventType.Parameter.PLAYER, thatPlayer);

			EventFactory factory = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "That player flips a coin. If he or she loses the flip, counter that spell.");
			factory.parameters.put(EventType.Parameter.IF, Identity.instance(flip));
			factory.parameters.put(EventType.Parameter.THEN, Identity.instance(counter(thatSpell, "Counter that spell.")));
			this.addEffect(factory);

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public static final class MirroredChaos extends EventTriggeredAbility
	{
		public MirroredChaos(GameState state)
		{
			super(state, "Whenever you roll (C), target player reveals the top card of his or her library. If it's a nonland card, you may cast it without paying its mana cost.");

			this.addPattern(PlanechaseGameRules.wheneverYouRollChaos());

			Target target = this.addTarget(Players.instance(), "target player");

			SetGenerator topCard = TopCards.instance(1, LibraryOf.instance(targetedBy(target)));

			EventFactory reveal = new EventFactory(EventType.REVEAL, "Target player reveals the top card of his or her library.");
			reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
			reveal.parameters.put(EventType.Parameter.OBJECT, topCard);
			this.addEffect(reveal);

			EventFactory castFactory = new EventFactory(PLAY_WITHOUT_PAYING_MANA_COSTS, "You may cast it without paying its mana cost.");
			castFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			castFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			castFactory.parameters.put(EventType.Parameter.OBJECT, topCard);

			EventFactory ifNonland = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If it's a nonland card, you may cast it without paying its mana cost.");
			ifNonland.parameters.put(EventType.Parameter.IF, RelativeComplement.instance(topCard, HasType.instance(Type.LAND)));
			ifNonland.parameters.put(EventType.Parameter.THEN, Identity.instance(castFactory));
			this.addEffect(ifNonland);

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public MirroredDepths(GameState state)
	{
		super(state);

		this.addAbility(new MirrorsHateYou(state));

		this.addAbility(new MirroredChaos(state));
	}
}
