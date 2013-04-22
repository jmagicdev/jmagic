package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Needlebite Trap")
@Types({Type.INSTANT})
@SubTypes({SubType.TRAP})
@ManaCost("5BB")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class NeedlebiteTrap extends Card
{
	public NeedlebiteTrap(GameState state)
	{
		super(state);

		// If an opponent gained life this turn, you may pay (B) rather than pay
		// Needlebite Trap's mana cost.
		this.addAbility(new org.rnd.jmagic.abilities.Trap(state, "Needlebite Trap", LifeGainedThisTurn.instance(OpponentsOf.instance(You.instance())), "If an opponent gained life this turn", "(B)"));
		state.ensureTracker(new LifeGainedThisTurn.LifeTracker());

		// Target player loses 5 life and you gain 5 life.
		Target target = this.addTarget(Players.instance(), "target player");

		this.addEffect(loseLife(targetedBy(target), 5, "Target player loses 5 life"));
		this.addEffect(gainLife(You.instance(), 5, "and you gain 5 life."));
	}
}
