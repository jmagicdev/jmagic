package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Life's Finale")
@Types({Type.SORCERY})
@ManaCost("4BB")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class LifesFinale extends Card
{
	public LifesFinale(GameState state)
	{
		super(state);

		// Destroy all creatures, then search target opponent's library for up
		// to three creature cards and put them into his or her graveyard. Then
		// that player shuffles his or her library.
		this.addEffect(destroy(CreaturePermanents.instance(), "Destroy all creatures,"));

		SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
		EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "then search target opponent's library for up to three creature cards and put them into his or her graveyard. Then that player shuffles his or her library.");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.INDEX, numberGenerator(1));
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.TARGET, target);
		search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(3));
		search.parameters.put(EventType.Parameter.TO, GraveyardOf.instance(target));
		search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasType.instance(Type.CREATURE)));
		this.addEffect(search);
	}
}
