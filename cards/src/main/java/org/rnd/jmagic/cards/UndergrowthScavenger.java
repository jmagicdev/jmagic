package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Undergrowth Scavenger")
@Types({Type.CREATURE})
@SubTypes({SubType.FUNGUS, SubType.HORROR})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class UndergrowthScavenger extends Card
{
	public UndergrowthScavenger(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Undergrowth Scavenger enters the battlefield with a number of +1/+1
		// counters on it equal to the number of creature cards in all
		// graveyards.
		SetGenerator deadThings = Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(Players.instance())));
		SetGenerator count = Count.instance(deadThings);
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, this.getName(), count,//
		"Undergrowth Scavenger enters the battlefield with a number of +1/+1 counters on it equal to the number of creature cards in all graveyards.",//
		Counter.CounterType.PLUS_ONE_PLUS_ONE));
	}
}
