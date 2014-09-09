package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Snapback")
@Types({Type.INSTANT})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class Snapback extends Card
{
	public Snapback(GameState state)
	{
		super(state);

		// You may exile a blue card from your hand rather than pay Snapback's
		// mana cost.
		EventFactory exileFactory = new EventFactory(EventType.EXILE_CHOICE, "Exile a blue card from your hand");
		exileFactory.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
		exileFactory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		exileFactory.parameters.put(EventType.Parameter.OBJECT, Intersect.instance(HasColor.instance(Color.BLUE), InZone.instance(HandOf.instance(You.instance()))));
		exileFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());

		CostCollection altCost = new CostCollection(CostCollection.TYPE_ALTERNATE, exileFactory);
		this.addAbility(new org.rnd.jmagic.abilities.AlternateCost(state, "You may exile a blue card from your hand rather than pay Snapback's mana cost.", altCost));

		// Return target creature to its owner's hand.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(bounce(target, "Return target creature to its owner's hand."));
	}
}
