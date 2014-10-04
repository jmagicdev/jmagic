package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Temur Charm")
@Types({Type.INSTANT})
@ManaCost("GUR")
@ColorIdentity({Color.RED, Color.BLUE, Color.GREEN})
public final class TemurCharm extends Card
{
	public TemurCharm(GameState state)
	{
		super(state);

		// Choose one \u2014

		// • Target creature you control gets +1/+1 until end of turn. It
		// fights target creature you don't control.
		{
			SetGenerator yours = targetedBy(this.addTarget(1, CREATURES_YOU_CONTROL, "target creature you control"));

			SetGenerator enemyCreatures = Intersect.instance(ControlledBy.instance(OpponentsOf.instance(You.instance())), HasType.instance(Type.CREATURE));
			SetGenerator theirs = targetedBy(this.addTarget(1, enemyCreatures, "target creature you don't control"));

			this.addEffect(1, ptChangeUntilEndOfTurn(yours, +1, +1, "Target creature you control gets +1/+1 until end of turn."));
			this.addEffect(1, fight(Union.instance(yours, theirs), "It fights target creature you don't control."));
		}

		// • Counter target spell unless its controller pays (3).
		{
			Target target = this.addTarget(2, Spells.instance(), "target spell");
			this.addEffect(2, counterTargetUnlessControllerPays("(3)", target));
		}

		// • Creatures with power 3 or less can't block this turn.
		{
			SetGenerator small = HasPower.instance(Between.instance(null, 3));
			this.addEffect(cantBlockThisTurn(small, "Creatures with power 3 or less can't block this turn."));
		}
	}
}
