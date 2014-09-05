package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Contagion")
@Types({Type.INSTANT})
@ManaCost("3BB")
@Printings({@Printings.Printed(ex = Alliances.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class Contagion extends Card
{
	public Contagion(GameState state)
	{
		super(state);

		// You may pay 1 life and exile a black card from your hand rather than
		// pay Contagion's mana cost.
		EventFactory lifeFactory = payLife(You.instance(), 1, "Pay 1 life");

		EventFactory exileFactory = new EventFactory(EventType.EXILE_CHOICE, "Exile a black card from your hand");
		exileFactory.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
		exileFactory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		exileFactory.parameters.put(EventType.Parameter.OBJECT, Intersect.instance(HasColor.instance(Color.BLACK), InZone.instance(HandOf.instance(You.instance()))));
		exileFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());

		CostCollection altCost = new CostCollection(CostCollection.TYPE_ALTERNATE, lifeFactory, exileFactory);
		this.addAbility(new org.rnd.jmagic.abilities.AlternateCost(state, "You may pay 1 life and exile a black card from your hand rather than pay Contagion's mana cost.", altCost));

		// Distribute two -2/-1 counters among one or two target creatures.
		Target target = this.addTarget(CreaturePermanents.instance(), "one or two target creatures");
		target.setNumber(1, 2);

		SetGenerator numberChosenTargets = CountChosenTargets.instance(This.instance(), Identity.instance(target));

		this.addEffect(putCounters(DivideBy.instance(numberGenerator(2), numberChosenTargets, true), Counter.CounterType.MINUS_TWO_MINUS_ONE, targetedBy(target), "Distribute two -2/-1 counters among one or two target creatures."));
	}
}
