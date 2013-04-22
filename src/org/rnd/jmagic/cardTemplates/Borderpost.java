package org.rnd.jmagic.cardTemplates;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public abstract class Borderpost extends Card
{
	public Borderpost(GameState state, Color a, Color b)
	{
		super(state);

		EventFactory bounceFactory = new EventFactory(EventType.PUT_INTO_HAND_CHOICE, "Return a basic land you control to its owner's hand");
		bounceFactory.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
		bounceFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		bounceFactory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		bounceFactory.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(ControlledBy.instance(You.instance()), Intersect.instance(HasSuperType.instance(SuperType.BASIC), LandPermanents.instance())));
		CostCollection altCost = new CostCollection(CostCollection.TYPE_ALTERNATE, "1", bounceFactory);
		this.addAbility(new org.rnd.jmagic.abilities.AlternateCost(state, "You may pay (1) and return a basic land you control to its owner's hand rather than pay " + this.getName() + "'s mana cost.", altCost));

		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(" + a.getLetter() + b.getLetter() + ")"));
	}

}
