package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cone of Flame")
@Types({Type.SORCERY})
@ManaCost("3RR")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.WEATHERLIGHT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class ConeofFlame extends Card
{
	public ConeofFlame(GameState state)
	{
		super(state);

		SetGenerator creatures = CreaturePermanents.instance();

		// First target
		Target targetOne = this.addTarget(creatures, "target creature to deal 1 damage to");
		targetOne.restrictFromLaterTargets = true;
		targetOne.division = 1;

		// Second target
		Target targetTwo = this.addTarget(creatures, "target creature to deal 2 damage to");
		targetTwo.restrictFromLaterTargets = true;
		targetTwo.division = 2;

		// Third target
		Target targetThree = this.addTarget(creatures, "target creature to deal 3 damage to");
		targetThree.division = 3;

		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.SOURCE, This.instance());
		parameters.put(EventType.Parameter.TAKER, ChosenTargetsFor.instance(Identity.instance(targetOne, targetTwo, targetThree), This.instance()));
		this.addEffect(new EventFactory(EventType.DISTRIBUTE_DAMAGE, parameters, "Cone of Flame deals 1 damage to target creature, 2 damage to another target creature, and 3 damage to a third target creature."));
	}
}
