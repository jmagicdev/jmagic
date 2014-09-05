package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Chalice of the Void")
@Types({Type.ARTIFACT})
@ManaCost("XX")
@Printings({@Printings.Printed(ex = Mirrodin.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class ChaliceoftheVoid extends Card
{
	public static final class TheOtherMeaningOfCounter extends EventTriggeredAbility
	{
		public TheOtherMeaningOfCounter(GameState state)
		{
			super(state, "Whenever a player casts a spell with converted mana cost equal to the number of charge counters on Chalice of the Void, counter that spell.");

			SetGenerator counters = Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.CHARGE));

			SimpleEventPattern castSpell = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			castSpell.put(EventType.Parameter.OBJECT, Intersect.instance(Spells.instance(), HasConvertedManaCost.instance(counters)));
			this.addPattern(castSpell);

			SetGenerator thatSpell = EventResult.instance(TriggerEvent.instance(This.instance()));

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.CAUSE, This.instance());
			parameters.put(EventType.Parameter.OBJECT, thatSpell);
			this.addEffect(new EventFactory(EventType.COUNTER, parameters, "Counter that spell."));
		}
	}

	public ChaliceoftheVoid(GameState state)
	{
		super(state);

		// Chalice of the Void enters the battlefield with X charge counters on
		// it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, this.getName(), ValueOfX.instance(This.instance()), "X charge counters on it", Counter.CounterType.CHARGE));

		// Whenever a player casts a spell with converted mana cost equal to the
		// number of charge counters on Chalice of the Void, counter that spell.
		this.addAbility(new TheOtherMeaningOfCounter(state));
	}
}
