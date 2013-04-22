package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("S\u00e9ance")
@Types({Type.ENCHANTMENT})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class Seance extends Card
{
	public static final class SeanceAbility0 extends EventTriggeredAbility
	{
		public SeanceAbility0(GameState state)
		{
			super(state, "At the beginning of each upkeep, you may exile target creature card from your graveyard. If you do, put a token onto the battlefield that's a copy of that card except it's a Spirit in addition to its other types. Exile it at the beginning of the next end step.");
			this.addPattern(atTheBeginningOfEachUpkeep());

			SetGenerator legal = Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance())));
			SetGenerator target = targetedBy(this.addTarget(legal, "target creature card from your graveyard"));

			EventFactory exileDeadDude = exile(target, "Exile target creature card from a graveyard");
			EventFactory mayExile = youMay(exileDeadDude, "You may exile target creature card from your graveyard.");

			SetGenerator thatCard = NewObjectOf.instance(EffectResult.instance(exileDeadDude));

			EventFactory makeSpirit = new EventFactory(EventType.CREATE_TOKEN_COPY, "Put a token onto the battlefield that's a copy of that card except it's a Spirit in addition to its other types.");
			makeSpirit.parameters.put(EventType.Parameter.CAUSE, This.instance());
			makeSpirit.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			makeSpirit.parameters.put(EventType.Parameter.OBJECT, thatCard);
			makeSpirit.parameters.put(EventType.Parameter.TYPE, Identity.instance(SubType.SPIRIT));

			SetGenerator thatToken = NewObjectOf.instance(EffectResult.instance(makeSpirit));

			EventFactory exile = exile(delayedTriggerContext(thatToken), "Exile it");
			EventFactory exileLater = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Exile it at the beginning of the next ends step.");
			exileLater.parameters.put(EventType.Parameter.CAUSE, This.instance());
			exileLater.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfTheEndStep()));
			exileLater.parameters.put(EventType.Parameter.EFFECT, Identity.instance(exile));

			EventFactory ifYouExile = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "If you do, put a token onto the battlefield that's a copy of that card except it's a Spirit in addition to its other types. Exile it at the beginning of the next end step.");
			ifYouExile.parameters.put(EventType.Parameter.IF, Identity.instance(mayExile));
			ifYouExile.parameters.put(EventType.Parameter.THEN, Identity.instance(sequence(makeSpirit, exileLater)));
			this.addEffect(ifYouExile);
		}
	}

	public Seance(GameState state)
	{
		super(state);

		// At the beginning of each upkeep, you may exile target creature card
		// from your graveyard. If you do, put a token onto the battlefield
		// that's a copy of that card except it's a Spirit in addition to its
		// other types. Exile it at the beginning of the next end step.
		this.addAbility(new SeanceAbility0(state));
	}
}
