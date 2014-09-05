package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Path to Exile")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Conflux.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class PathtoExile extends Card
{
	public PathtoExile(GameState state)
	{
		super(state);

		// Exile target creature.
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffect(exile(targetedBy(target), "Exile target creature."));
		SetGenerator controller = ControllerOf.instance(targetedBy(target));

		// Its controller may search his or her library for a basic land card,
		// put that card onto the battlefield tapped, then shuffle his or her
		// library.
		EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Its controller searches his or her library for a basic land card, puts that card onto the battlefield tapped, then shuffles his or her library.");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.CONTROLLER, controller);
		search.parameters.put(EventType.Parameter.PLAYER, controller);
		search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		search.parameters.put(EventType.Parameter.TO, Battlefield.instance());
		search.parameters.put(EventType.Parameter.TAPPED, Empty.instance());
		search.parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasSuperType.instance(SuperType.BASIC), HasType.instance(Type.LAND))));

		this.addEffect(playerMay(controller, search, "Its controller may search his or her library for a basic land card, put that card onto the battlefield tapped, then shuffle his or her library."));
	}
}
