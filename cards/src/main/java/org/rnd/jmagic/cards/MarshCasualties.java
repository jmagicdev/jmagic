package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Marsh Casualties")
@Types({Type.SORCERY})
@ManaCost("BB")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class MarshCasualties extends Card
{
	public MarshCasualties(GameState state)
	{
		super(state);

		org.rnd.jmagic.abilities.keywords.Kicker ability = new org.rnd.jmagic.abilities.keywords.Kicker(state, "(3)");
		this.addAbility(ability);

		// Kicker (3) (You may pay an additional (3) as you cast this spell.)
		CostCollection kickerCost = ability.costCollections[0];

		// Creatures target player controls get -1/-1 until end of turn. If
		// Marsh Casualties was kicked, those creatures get -2/-2 until end of
		// turn instead.
		Target target = this.addTarget(Players.instance(), "target player");
		SetGenerator thatPlayersCreatures = Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(targetedBy(target)));

		SetGenerator change = IfThenElse.instance(ThisSpellWasKicked.instance(kickerCost), numberGenerator(-2), numberGenerator(-1));

		this.addEffect(ptChangeUntilEndOfTurn(thatPlayersCreatures, change, change, "Creatures target player controls get -1/-1 until end of turn. If Marsh Casualties was kicked, those creatures get -2/-2 until end of turn instead."));
	}
}
