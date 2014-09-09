package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Shrine of Piercing Vision")
@Types({Type.ARTIFACT})
@ManaCost("2")
@ColorIdentity({})
public final class ShrineofPiercingVision extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("Shrine of Piercing Vision", "Choose a card to put into your hand", false);

	public static final class ShrineofPiercingVisionAbility0 extends EventTriggeredAbility
	{
		public ShrineofPiercingVisionAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep or whenever you cast a blue spell, put a charge counter on Shrine of Piercing Vision.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SimpleEventPattern castABlueSpell = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			castABlueSpell.put(EventType.Parameter.PLAYER, You.instance());
			castABlueSpell.put(EventType.Parameter.OBJECT, Intersect.instance(Spells.instance(), HasColor.instance(Color.BLUE)));
			this.addPattern(castABlueSpell);

			this.addEffect(putCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Put a charge counter on Shrine of Piercing Vision."));
		}
	}

	public static final class ShrineofPiercingVisionAbility1 extends ActivatedAbility
	{
		public ShrineofPiercingVisionAbility1(GameState state)
		{
			super(state, "(T), Sacrifice Shrine of Piercing Vision: Look at the top X cards of your library, where X is the number of charge counters on Shrine of Piercing Vision. Put one of those cards into your hand and the rest on the bottom of your library in any order.");
			this.costsTap = true;
			this.addCost(sacrificeThis("Shrine of Piercing Vision"));

			SetGenerator counters = Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.CHARGE));

			// Look at the top X cards of your library, where X is the number of
			// charge counters on Shrine of Piercing Vision.
			SetGenerator yourLibrary = LibraryOf.instance(You.instance());
			SetGenerator top = TopCards.instance(counters, yourLibrary);
			EventFactory look = new EventFactory(EventType.LOOK, "Look at the top X cards of your library, where X is the number of charge counters on Shrine of Piercing Vision.");
			look.parameters.put(EventType.Parameter.CAUSE, This.instance());
			look.parameters.put(EventType.Parameter.PLAYER, You.instance());
			look.parameters.put(EventType.Parameter.OBJECT, top);
			this.addEffect(look);

			// Put one of those cards into your hand and the rest on the bottom
			// of your library in any order.

			EventFactory chooseCard = new EventFactory(EventType.PLAYER_CHOOSE, "");
			chooseCard.parameters.put(EventType.Parameter.PLAYER, You.instance());
			chooseCard.parameters.put(EventType.Parameter.CHOICE, top);
			chooseCard.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.OBJECTS, REASON));
			this.addEffect(chooseCard);

			SetGenerator chosenCard = EffectResult.instance(chooseCard);

			EventFactory putIntoHand = new EventFactory(EventType.MOVE_OBJECTS, "Put one of those cards into your hand");
			putIntoHand.parameters.put(EventType.Parameter.CAUSE, This.instance());
			putIntoHand.parameters.put(EventType.Parameter.OBJECT, chosenCard);
			putIntoHand.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));

			EventFactory putOnBottom = new EventFactory(EventType.PUT_INTO_LIBRARY, "and the rest on the bottom of your library in any order.");
			putOnBottom.parameters.put(EventType.Parameter.CAUSE, This.instance());
			putOnBottom.parameters.put(EventType.Parameter.INDEX, numberGenerator(-1));
			putOnBottom.parameters.put(EventType.Parameter.OBJECT, RelativeComplement.instance(top, chosenCard));

			this.addEffect(simultaneous(putIntoHand, putOnBottom));
		}
	}

	public ShrineofPiercingVision(GameState state)
	{
		super(state);

		// At the beginning of your upkeep or whenever you cast a blue spell,
		// put a charge counter on Shrine of Piercing Vision.
		this.addAbility(new ShrineofPiercingVisionAbility0(state));

		// (T), Sacrifice Shrine of Piercing Vision: Look at the top X cards of
		// your library, where X is the number of charge counters on Shrine of
		// Piercing Vision. Put one of those cards into your hand and the rest
		// on the bottom of your library in any order.
		this.addAbility(new ShrineofPiercingVisionAbility1(state));
	}
}
