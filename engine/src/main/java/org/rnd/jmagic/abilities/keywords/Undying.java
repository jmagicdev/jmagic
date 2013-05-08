package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Undying")
// This is pretty much an exact copy of Persist, so fix any errors there that
// are found here.
public final class Undying extends Keyword
{
	public Undying(GameState state)
	{
		super(state, "Undying");
	}

	@Override
	protected java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		java.util.List<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();
		ret.add(new UndyingAbility(this.state));
		return ret;
	}

	public static final class UndyingAbility extends EventTriggeredAbility
	{
		public UndyingAbility(GameState state)
		{
			super(state, "When this permanent is put into a graveyard from the battlefield, if it had no +1/+1 counters on it, return it to play under its owner's control with a +1/+1 counter on it.");

			this.addPattern(whenThisIsPutIntoAGraveyardFromTheBattlefield());

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;
			this.interveningIf = Intersect.instance(numberGenerator(0), Count.instance(CountersOn.instance(thisCard, Counter.CounterType.PLUS_ONE_PLUS_ONE)));

			SetGenerator owner = OwnerOf.instance(thisCard);
			SetGenerator triggerEvent = TriggerZoneChange.instance(This.instance());

			EventFactory factory = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_WITH_COUNTERS, "Return it to play under its owner's control with a +1/+1 counter on it.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.CONTROLLER, owner);
			// Do this intersect to make sure the creature is in the graveyard
			factory.parameters.put(EventType.Parameter.OBJECT, Intersect.instance(InZone.instance(GraveyardOf.instance(owner)), NewObjectOf.instance(triggerEvent)));
			factory.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));
			this.addEffect(factory);
		}
	}
}
