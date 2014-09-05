package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.Convenience;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Force of Will")
@Types({Type.INSTANT})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = Alliances.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class ForceofWill extends Card
{
	public ForceofWill(GameState state)
	{
		super(state);

		EventFactory lifeFactory = payLife(You.instance(), 1, "Pay 1 life");

		EventFactory exileFactory = new EventFactory(EventType.EXILE_CHOICE, "Exile a blue card from your hand");
		exileFactory.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
		exileFactory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		exileFactory.parameters.put(EventType.Parameter.OBJECT, Intersect.instance(HasColor.instance(Color.BLUE), InZone.instance(HandOf.instance(You.instance()))));
		exileFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());

		CostCollection altCost = new CostCollection(CostCollection.TYPE_ALTERNATE, lifeFactory, exileFactory);
		this.addAbility(new org.rnd.jmagic.abilities.AlternateCost(state, "You may pay 1 life and exile a blue card from your hand rather than pay Force of Will's mana cost.", altCost));

		Target target = this.addTarget(Spells.instance(), "target spell");
		this.addEffect(Convenience.counter(targetedBy(target), "Counter target spell."));
	}
}
