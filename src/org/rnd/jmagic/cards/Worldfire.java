package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Worldfire")
@Types({Type.SORCERY})
@ManaCost("6RRR")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.MYTHIC)})
@ColorIdentity({Color.RED})
public final class Worldfire extends Card
{
	public Worldfire(GameState state)
	{
		super(state);

		// Exile all permanents. Exile all cards from all hands and graveyards.
		// Each player's life total becomes 1.
		this.addEffect(exile(Permanents.instance(), "Exile all permanents."));

		this.addEffect(exile(InZone.instance(Union.instance(HandOf.instance(Players.instance()), GraveyardOf.instance(Players.instance()))), "Exile all cards from all hands and graveyards."));

		EventFactory life = new EventFactory(EventType.SET_LIFE, "Each player's life total becomes 1.");
		life.parameters.put(EventType.Parameter.CAUSE, This.instance());
		life.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		life.parameters.put(EventType.Parameter.PLAYER, Players.instance());
		this.addEffect(life);
	}
}
