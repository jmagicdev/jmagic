package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Defy Death")
@Types({Type.SORCERY})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class DefyDeath extends Card
{
	public DefyDeath(GameState state)
	{
		super(state);

		// Return target creature card from your graveyard to the battlefield.
		// If it's an Angel, put two +1/+1 counters on it.

		SetGenerator creatureCards = Intersect.instance(HasType.instance(Type.CREATURE), Cards.instance());
		SetGenerator yourGraveyard = GraveyardOf.instance(You.instance());

		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(creatureCards, InZone.instance(yourGraveyard)), "target creature card from your graveyard"));

		EventFactory factory = new EventFactory(EventType.MOVE_OBJECTS, "Return target creature card from your graveyard to the battlefield.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.TO, Battlefield.instance());
		factory.parameters.put(EventType.Parameter.OBJECT, target);
		factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		this.addEffect(factory);

		SetGenerator thatCreature = NewObjectOf.instance(EffectResult.instance(factory));
		EventFactory counters = putCounters(2, Counter.CounterType.PLUS_ONE_PLUS_ONE, thatCreature, "Put two +1/+1 counters on it.");
		this.addEffect(ifThen(Intersect.instance(HasSubType.instance(SubType.ANGEL), thatCreature), counters, "If it's an Angel, put two +1/+1 counters on it."));
	}
}
