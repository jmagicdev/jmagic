package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Misdirection")
@Types({Type.INSTANT})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = Expansion.MERCADIAN_MASQUES, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class Misdirection extends Card
{
	public Misdirection(GameState state)
	{
		super(state);

		// You may exile a blue card from your hand rather than pay
		// Misdirection's mana cost.
		EventFactory exileFactory = new EventFactory(EventType.EXILE_CHOICE, "Exile a blue card from your hand");
		exileFactory.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
		exileFactory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		exileFactory.parameters.put(EventType.Parameter.OBJECT, Intersect.instance(HasColor.instance(Color.BLUE), InZone.instance(HandOf.instance(You.instance()))));
		exileFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());

		CostCollection altCost = new CostCollection(CostCollection.TYPE_ALTERNATE, exileFactory);

		this.addAbility(new org.rnd.jmagic.abilities.AlternateCost(state, "You may exile a blue card from your hand rather than pay Misdirection's mana cost.", altCost));

		// Change the target of target spell with a single target.
		Target target = this.addTarget(Intersect.instance(Spells.instance(), HasOneTarget.instance()), "target spell with a single target");

		EventFactory swerve = new EventFactory(EventType.CHANGE_TARGETS, "Change the target of target spell with a single target.");
		swerve.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		swerve.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(swerve);
	}
}
