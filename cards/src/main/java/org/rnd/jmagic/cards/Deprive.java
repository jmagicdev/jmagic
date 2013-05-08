package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Deprive")
@Types({Type.INSTANT})
@ManaCost("UU")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Deprive extends Card
{
	public Deprive(GameState state)
	{
		super(state);

		// As an additional cost to cast Deprive, return a land you control to
		// its owner's hand.
		EventFactory bounce = new EventFactory(EventType.PUT_INTO_HAND_CHOICE, "return a land you control to its owner's hand");
		bounce.parameters.put(EventType.Parameter.CAUSE, This.instance());
		bounce.parameters.put(EventType.Parameter.PLAYER, You.instance());
		bounce.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(LandPermanents.instance(), ControlledBy.instance(You.instance())));
		bounce.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		this.addCost(bounce);

		// Counter target spell.
		Target target = this.addTarget(Spells.instance(), "target spell");
		this.addEffect(counter(targetedBy(target), "Counter target spell."));
	}
}
