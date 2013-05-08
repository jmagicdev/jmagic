package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("The Maelstrom")
@Types({Type.PLANE})
@SubTypes({SubType.ALARA})
@Printings({@Printings.Printed(ex = Expansion.PLANECHASE, r = Rarity.COMMON)})
@ColorIdentity({})
public final class TheMaelstrom extends Card
{
	public static final class TheMaelstromsFirstAbility extends EventTriggeredAbility
	{
		public TheMaelstromsFirstAbility(GameState state)
		{
			super(state, "When you planeswalk to The Maelstrom or at the beginning of your upkeep, you may reveal the top card of your library. If it's a permanent card, you may put it onto the battlefield. If you revealed a card but didn't put it onto the battlefield, put it on the bottom of your library.");

			SimpleEventPattern pattern = new SimpleEventPattern(Planechase.PLANESWALK);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.put(EventType.Parameter.TO, ABILITY_SOURCE_OF_THIS);
			this.addPattern(pattern);
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator topCard = TopCards.instance(1, LibraryOf.instance(You.instance()));

			EventFactory reveal = new EventFactory(EventType.REVEAL, "Reveal the top card of your library.");
			reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
			reveal.parameters.put(EventType.Parameter.OBJECT, topCard);

			EventFactory putOnBattlefield = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Put it onto the battlefield.");
			putOnBattlefield.parameters.put(EventType.Parameter.CAUSE, This.instance());
			putOnBattlefield.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			putOnBattlefield.parameters.put(EventType.Parameter.OBJECT, topCard);

			EventFactory putOnBottom = new EventFactory(EventType.PUT_INTO_LIBRARY, "Put it on the bottom of your library.");
			putOnBottom.parameters.put(EventType.Parameter.CAUSE, This.instance());
			putOnBottom.parameters.put(EventType.Parameter.INDEX, numberGenerator(-1));
			putOnBottom.parameters.put(EventType.Parameter.OBJECT, topCard);

			EventFactory ifPutOnBattlefield = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may put it onto the battlefield.  Otherwise, put it on the bottom of your library.");
			ifPutOnBattlefield.parameters.put(EventType.Parameter.IF, Identity.instance(youMay(putOnBattlefield, "You may put it onto the battlefield.")));
			ifPutOnBattlefield.parameters.put(EventType.Parameter.ELSE, Identity.instance(putOnBottom));

			EventFactory ifCondition = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If it's a permanent card, you may put it onto the battlefield. Otherwise, put it on the bottom of your library.");
			ifCondition.parameters.put(EventType.Parameter.IF, Intersect.instance(topCard, HasType.instance(Type.permanentTypes())));
			ifCondition.parameters.put(EventType.Parameter.THEN, Identity.instance(ifPutOnBattlefield));
			ifCondition.parameters.put(EventType.Parameter.ELSE, Identity.instance(putOnBottom));

			EventFactory ifReveal = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may reveal the top card of your library. If it's a permanent card, you may put it onto the battlefield. If you revealed a card but didn't put it onto the battlefield, put it on the bottom of your library.");
			ifReveal.parameters.put(EventType.Parameter.IF, Identity.instance(youMay(reveal, "You may reveal the top card of your library.")));
			ifReveal.parameters.put(EventType.Parameter.THEN, Identity.instance(ifCondition));

			this.addEffect(ifReveal);

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public static final class ChaosRebirth extends EventTriggeredAbility
	{
		public ChaosRebirth(GameState state)
		{
			super(state, "Whenever you roll (C), return target permanent card from your graveyard to the battlefield.");

			this.addPattern(Planechase.wheneverYouRollChaos());

			Target target = this.addTarget(Intersect.instance(InZone.instance(GraveyardOf.instance(You.instance())), HasType.instance(Type.permanentTypes())), "target permanent card from your graveyard");

			EventFactory putOnBattlefield = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Put it onto the battlefield.");
			putOnBattlefield.parameters.put(EventType.Parameter.CAUSE, This.instance());
			putOnBattlefield.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			putOnBattlefield.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			this.addEffect(putOnBattlefield);

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public TheMaelstrom(GameState state)
	{
		super(state);

		this.addAbility(new TheMaelstromsFirstAbility(state));

		this.addAbility(new ChaosRebirth(state));
	}
}
