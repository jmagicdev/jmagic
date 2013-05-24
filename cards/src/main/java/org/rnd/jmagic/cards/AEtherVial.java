package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("\u00C6ther Vial")
@Types({Type.ARTIFACT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.RELICS, r = Rarity.MYTHIC), @Printings.Printed(ex = Expansion.DARKSTEEL, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class AEtherVial extends Card
{
	public static final class AEtherVialAbility0 extends EventTriggeredAbility
	{
		public AEtherVialAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, you may put a charge counter on \u00C6ther Vial.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			EventFactory putCounter = putCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Put a charge counter on \u00C6ther Vial");
			this.addEffect(youMay(putCounter, "You may put a charge counter on \u00C6ther Vial."));
		}
	}

	public static final class AEtherVialAbility1 extends ActivatedAbility
	{
		public AEtherVialAbility1(GameState state)
		{
			super(state, "(T): You may put a creature card with converted mana cost equal to the number of charge counters on \u00C6ther Vial from your hand onto the battlefield.");
			this.costsTap = true;

			SetGenerator inYourHand = InZone.instance(HandOf.instance(You.instance()));
			SetGenerator choices = Intersect.instance(inYourHand, HasType.instance(Type.CREATURE), HasConvertedManaCost.instance(Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.CHARGE))));

			EventFactory putOntoBattlefield = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_CHOICE, "Put a creature card from your hand onto the battlefield");
			putOntoBattlefield.parameters.put(EventType.Parameter.CAUSE, This.instance());
			putOntoBattlefield.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			putOntoBattlefield.parameters.put(EventType.Parameter.OBJECT, choices);

			this.addEffect(youMay(putOntoBattlefield, "You may put a creature card from your hand onto the battlefield."));
		}
	}

	public AEtherVial(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, you may put a charge counter on
		// \u00C6ther Vial.
		this.addAbility(new AEtherVialAbility0(state));

		// (T): You may put a creature card with converted mana cost equal to
		// the number of charge counters on \u00C6ther Vial from your hand onto
		// the battlefield.
		this.addAbility(new AEtherVialAbility1(state));
	}
}
