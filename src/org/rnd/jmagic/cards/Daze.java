package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Daze")
@Types({Type.INSTANT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.NEMESIS, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Daze extends Card
{
	public Daze(GameState state)
	{
		super(state);

		// You may return an Island you control to its owner's hand rather than
		// pay Daze's mana cost.
		EventFactory bounceFactory = new EventFactory(EventType.PUT_INTO_HAND_CHOICE, "Return an Island you control to its owner's hand");
		bounceFactory.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
		bounceFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		bounceFactory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		bounceFactory.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.ISLAND)));

		CostCollection altCost = new CostCollection(CostCollection.TYPE_ALTERNATE, bounceFactory);

		this.addAbility(new org.rnd.jmagic.abilities.AlternateCost(state, "You may return an Island you control to its owner's hand rather than pay Daze's mana cost.", altCost));

		// Counter target spell unless its controller pays (1).
		Target target = this.addTarget(Spells.instance(), "target spell");
		this.addEffect(counterTargetUnlessControllerPays("(1)", target));
	}
}
