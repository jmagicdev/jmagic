package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Remember the Fallen")
@Types({Type.SORCERY})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class RemembertheFallen extends Card
{
	public RemembertheFallen(GameState state)
	{
		super(state);

		SetGenerator yourGraveyard = GraveyardOf.instance(You.instance());
		SetGenerator yourHand = HandOf.instance(You.instance());
		SetGenerator inYourGraveyard = InZone.instance(yourGraveyard);

		// Choose one or both \u2014 Return target creature card from your
		// graveyard to your hand; and/or return target artifact card from your
		// graveyard to your hand.
		this.setNumModes(new Set(new org.rnd.util.NumberRange(1, 2)));
		{
			SetGenerator targeting = Intersect.instance(HasType.instance(Type.CREATURE), Cards.instance(), inYourGraveyard);
			SetGenerator target = targetedBy(this.addTarget(1, targeting, "target creature card from your graveyard"));
			EventFactory returnToHand = new EventFactory(EventType.MOVE_OBJECTS, "Return target creature card from your graveyard to your hand");
			returnToHand.parameters.put(EventType.Parameter.CAUSE, This.instance());
			returnToHand.parameters.put(EventType.Parameter.TO, yourHand);
			returnToHand.parameters.put(EventType.Parameter.OBJECT, target);
			this.addEffect(1, returnToHand);
		}
		{
			SetGenerator targeting = Intersect.instance(HasType.instance(Type.ARTIFACT), Cards.instance(), inYourGraveyard);
			SetGenerator target = targetedBy(this.addTarget(2, targeting, "target artifact card from your graveyard"));
			EventFactory returnToHand = new EventFactory(EventType.MOVE_OBJECTS, "and/or return target artifact card from your graveyard to your hand.");
			returnToHand.parameters.put(EventType.Parameter.CAUSE, This.instance());
			returnToHand.parameters.put(EventType.Parameter.TO, yourHand);
			returnToHand.parameters.put(EventType.Parameter.OBJECT, target);
			this.addEffect(2, returnToHand);
		}
	}
}
