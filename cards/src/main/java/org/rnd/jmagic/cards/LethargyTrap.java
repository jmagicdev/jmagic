package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Lethargy Trap")
@Types({Type.INSTANT})
@SubTypes({SubType.TRAP})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class LethargyTrap extends Card
{
	public LethargyTrap(GameState state)
	{
		super(state);

		// If three or more creatures are attacking, you may pay (U) rather than
		// pay Lethargy Trap's mana cost.
		SetGenerator threeOrMore = Between.instance(3, null);
		SetGenerator attackingCreatures = Count.instance(Attacking.instance());
		SetGenerator condition = Intersect.instance(threeOrMore, attackingCreatures);
		this.addAbility(new org.rnd.jmagic.abilities.Trap(state, this.getName(), condition, "If three or more creatures are attacking", "(U)"));

		// Attacking creatures get -3/-0 until end of turn.
		this.addEffect(ptChangeUntilEndOfTurn(Attacking.instance(), (-3), (-0), "Attacking creatures get -3/-0 until end of turn."));
	}
}
