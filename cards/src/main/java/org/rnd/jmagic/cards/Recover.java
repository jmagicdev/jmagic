package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Recover")
@Types({Type.SORCERY})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.INVASION, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class Recover extends Card
{
	public Recover(GameState state)
	{
		super(state);

		SetGenerator graveyard = GraveyardOf.instance(You.instance());
		Target target = this.addTarget(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(graveyard)), "target creature card from your graveyard");

		EventType.ParameterMap moveParameters = new EventType.ParameterMap();
		moveParameters.put(EventType.Parameter.CAUSE, This.instance());
		moveParameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		moveParameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		this.addEffect(new EventFactory(EventType.MOVE_OBJECTS, moveParameters, "Return target creature card from your graveyard to your hand."));

		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}
