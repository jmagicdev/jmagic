package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Life from the Loam")
@Types({Type.SORCERY})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class LifefromtheLoam extends Card
{
	public LifefromtheLoam(GameState state)
	{
		super(state);

		// Return up to three target land cards from your graveyard to your
		// hand.
		SetGenerator landsInYourYard = Intersect.instance(HasType.instance(Type.LAND), InZone.instance(GraveyardOf.instance(You.instance())));
		Target target = this.addTarget(landsInYourYard, "up to three target land cards from your graveyard");
		target.setNumber(0, 3);

		EventFactory returnToHand = new EventFactory(EventType.MOVE_OBJECTS, "Return up to three target land cards from your graveyard to your hand.");
		returnToHand.parameters.put(EventType.Parameter.CAUSE, This.instance());
		returnToHand.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		returnToHand.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		this.addEffect(returnToHand);

		// Dredge 3 (If you would draw a card, instead you may put exactly three
		// cards from the top of your library into your graveyard. If you do,
		// return this card from your graveyard to your hand. Otherwise, draw a
		// card.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Dredge(state, 3));
	}
}
