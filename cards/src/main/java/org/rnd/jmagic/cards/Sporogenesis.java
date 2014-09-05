package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Sporogenesis")
@Types({Type.ENCHANTMENT})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = UrzasSaga.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class Sporogenesis extends Card
{
	public static final class SporogenesisAbility0 extends EventTriggeredAbility
	{
		public SporogenesisAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, you may put a fungus counter on target nontoken creature.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator targetable = Intersect.instance(NonToken.instance(), CreaturePermanents.instance());
			SetGenerator target = targetedBy(this.addTarget(targetable, "target nontoken creature"));

			EventFactory putCounter = putCounters(1, Counter.CounterType.FUNGUS, target, "Put a fungus counter on target nontoken creature");
			this.addEffect(youMay(putCounter, "You may put a fungus counter on target nontoken creature."));
		}
	}

	public static final class SporogenesisAbility1 extends EventTriggeredAbility
	{
		public SporogenesisAbility1(GameState state)
		{
			super(state, "Whenever a creature with a fungus counter on it dies, put a 1/1 green Saproling creature token onto the battlefield for each fungus counter on that creature.");

			SetGenerator moldyCreatures = Intersect.instance(CreaturePermanents.instance(), HasCounterOfType.instance(Counter.CounterType.FUNGUS));
			SimpleZoneChangePattern pattern = new SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), moldyCreatures, true);
			this.addPattern(pattern);

			SetGenerator thatCreature = OldObjectOf.instance(TriggerZoneChange.instance(This.instance()));
			SetGenerator number = Count.instance(CountersOn.instance(thatCreature, Counter.CounterType.FUNGUS));

			CreateTokensFactory f = new CreateTokensFactory(number, numberGenerator(1), numberGenerator(1), "Put a 1/1 green Saproling creature token onto the battlefield for each fungus counter on that creature.");
			f.setColors(Color.GREEN);
			f.setSubTypes(SubType.SAPROLING);
			this.addEffect(f.getEventFactory());
		}
	}

	public static final class SporogenesisAbility2 extends EventTriggeredAbility
	{
		public SporogenesisAbility2(GameState state)
		{
			super(state, "When Sporogenesis leaves the battlefield, remove all fungus counters from all creatures.");
			this.addPattern(whenThisLeavesTheBattlefield());

			EventFactory remove = new EventFactory(EventType.REMOVE_ALL_COUNTERS, "Remove all fungus counters from all creatures.");
			remove.parameters.put(EventType.Parameter.CAUSE, This.instance());
			remove.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.FUNGUS));
			remove.parameters.put(EventType.Parameter.OBJECT, CreaturePermanents.instance());
			this.addEffect(remove);
		}
	}

	public Sporogenesis(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, you may put a fungus counter on
		// target nontoken creature.
		this.addAbility(new SporogenesisAbility0(state));

		// Whenever a creature with a fungus counter on it is put into a
		// graveyard from the battlefield, put a 1/1 green Saproling creature
		// token onto the battlefield for each fungus counter on that creature.
		this.addAbility(new SporogenesisAbility1(state));

		// When Sporogenesis leaves the battlefield, remove all fungus counters
		// from all creatures.
		this.addAbility(new SporogenesisAbility2(state));
	}
}
