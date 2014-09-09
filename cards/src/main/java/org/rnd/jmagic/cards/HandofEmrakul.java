package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hand of Emrakul")
@Types({Type.CREATURE})
@SubTypes({SubType.ELDRAZI})
@ManaCost("9")
@ColorIdentity({})
public final class HandofEmrakul extends Card
{
	public HandofEmrakul(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);

		// You may sacrifice four Eldrazi Spawn rather than pay Hand of
		// Emrakul's mana cost.
		EventFactory exileFactory = new EventFactory(EventType.SACRIFICE_CHOICE, "Sacrifice four Eldrazi Spawn");
		exileFactory.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
		exileFactory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(4));
		exileFactory.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(HasSubType.instance(SubType.ELDRAZI), HasSubType.instance(SubType.SPAWN)));
		exileFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());

		CostCollection altCost = new CostCollection(CostCollection.TYPE_ALTERNATE, exileFactory);
		this.addAbility(new org.rnd.jmagic.abilities.AlternateCost(state, "You may sacrifice four Eldrazi Spawn rather than pay Hand of Emrakul's mana cost.", altCost));

		// Annihilator 1
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Annihilator.Final(state, 1));
	}
}
