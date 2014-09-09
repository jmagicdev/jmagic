package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Demon of Death's Gate")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("6BBB")
@ColorIdentity({Color.BLACK})
public final class DemonofDeathsGate extends Card
{
	public DemonofDeathsGate(GameState state)
	{
		super(state);

		this.setPower(9);
		this.setToughness(9);

		// You may pay 6 life and sacrifice three black creatures rather than
		// pay Demon of Death's Gate's mana cost.
		EventFactory lifeFactory = payLife(You.instance(), 6, "Pay 6 life");

		EventFactory sacFactory = new EventFactory(EventType.SACRIFICE_CHOICE, "Sacrifice three black creatures");
		sacFactory.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
		sacFactory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(3));
		sacFactory.parameters.put(EventType.Parameter.OBJECT, Intersect.instance(CREATURES_YOU_CONTROL, HasColor.instance(Color.BLACK)));
		sacFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());

		CostCollection altCost = new CostCollection(CostCollection.TYPE_ALTERNATE, lifeFactory, sacFactory);
		this.addAbility(new org.rnd.jmagic.abilities.AlternateCost(state, "You may pay 6 life and sacrifice three black creatures rather than pay Demon of Death's Gate's mana cost.", altCost));

		// Flying, trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
	}
}
