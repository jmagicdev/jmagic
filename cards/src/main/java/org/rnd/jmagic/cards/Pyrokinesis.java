package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pyrokinesis")
@Types({Type.INSTANT})
@ManaCost("4RR")
@Printings({@Printings.Printed(ex = Expansion.ALLIANCES, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class Pyrokinesis extends Card
{
	public Pyrokinesis(GameState state)
	{
		super(state);

		// You may exile a red card from your hand rather than pay Pyrokinesis's
		// mana cost.
		EventFactory exileFactory = new EventFactory(EventType.EXILE_CHOICE, "Exile a red card from your hand");
		exileFactory.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
		exileFactory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		exileFactory.parameters.put(EventType.Parameter.OBJECT, Intersect.instance(HasColor.instance(Color.RED), InZone.instance(HandOf.instance(You.instance()))));
		exileFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());

		CostCollection altCost = new CostCollection(CostCollection.TYPE_ALTERNATE, exileFactory);
		this.addAbility(new org.rnd.jmagic.abilities.AlternateCost(state, "You may exile a red card from your hand rather than pay Pyrokinesis's mana cost.", altCost));

		// Pyrokinesis deals 4 damage divided as you choose among any number of
		// target creatures.
		Target target = this.addTarget(CreaturePermanents.instance(), "up to four target creatures");
		target.setNumber(1, 4);

		this.setDivision(Union.instance(numberGenerator(4), Identity.instance("damage")));
		EventFactory factory = new EventFactory(EventType.DISTRIBUTE_DAMAGE, "Pyrokinesis deals 4 damage divided as you choose among any number of target creatures.");
		factory.parameters.put(EventType.Parameter.SOURCE, This.instance());
		factory.parameters.put(EventType.Parameter.TAKER, ChosenTargetsFor.instance(Identity.instance(target), This.instance()));
		this.addEffect(factory);
	}
}
