package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Remand")
@Types({Type.INSTANT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = RavnicaCityOfGuilds.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Remand extends Card
{
	public Remand(GameState state)
	{
		super(state);

		Target target = this.addTarget(Spells.instance(), "target spell");

		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		parameters.put(EventType.Parameter.TO, HandOf.instance(OwnerOf.instance(targetedBy(target))));
		this.addEffect(new EventFactory(EventType.COUNTER, parameters, "Counter target spell. If that spell is countered this way, put it into its owner's hand instead of into that player's graveyard."));

		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}
