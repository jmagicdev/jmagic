package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Morbid Plunder")
@Types({Type.SORCERY})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class MorbidPlunder extends Card
{
	public MorbidPlunder(GameState state)
	{
		super(state);

		SetGenerator yourGraveyard = GraveyardOf.instance(You.instance());
		Target target = this.addTarget(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(yourGraveyard)), "up to two target creature cards in your graveyard");
		target.setNumber(0, 2);

		// Return up to two target creature cards from your graveyard to your
		// hand.
		EventFactory move = new EventFactory(EventType.MOVE_OBJECTS, "Return up to two target creature cards from your graveyard to your hand.");
		move.parameters.put(EventType.Parameter.CAUSE, This.instance());
		move.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		move.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		this.addEffect(move);
	}
}
