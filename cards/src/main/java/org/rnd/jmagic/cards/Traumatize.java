package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Traumatize")
@Types({Type.SORCERY})
@ManaCost("3UU")
@ColorIdentity({Color.BLUE})
public final class Traumatize extends Card
{
	public Traumatize(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");

		SetGenerator targetPlayer = targetedBy(target);
		SetGenerator numberToMill = DivideBy.instance(Count.instance(InZone.instance(LibraryOf.instance(targetPlayer))), numberGenerator(2), false);

		EventType.ParameterMap millParameters = new EventType.ParameterMap();
		millParameters.put(EventType.Parameter.CAUSE, This.instance());
		millParameters.put(EventType.Parameter.NUMBER, numberToMill);
		millParameters.put(EventType.Parameter.PLAYER, targetPlayer);
		this.addEffect(new EventFactory(EventType.MILL_CARDS, millParameters, "Target player puts the top half of his or her library, rounded down, into his or her graveyard."));
	}
}
