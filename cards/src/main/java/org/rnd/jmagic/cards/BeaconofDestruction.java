package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Beacon of Destruction")
@Types({Type.INSTANT})
@ManaCost("3RR")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = FifthDawn.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class BeaconofDestruction extends Card
{
	public BeaconofDestruction(GameState state)
	{
		super(state);

		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

		this.addEffect(spellDealDamage(5, targetedBy(target), "Beacon of Destruction deals 5 damage to target creature or player."));

		EventType.ParameterMap shuffleParameters = new EventType.ParameterMap();
		shuffleParameters.put(EventType.Parameter.CAUSE, This.instance());
		shuffleParameters.put(EventType.Parameter.OBJECT, Union.instance(This.instance(), OwnerOf.instance(This.instance())));
		this.addEffect(new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, shuffleParameters, "Shuffle Beacon of Destruction into its owner's library"));
	}
}
