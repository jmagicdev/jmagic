package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cryptic Command")
@Types({Type.INSTANT})
@ManaCost("1UUU")
@Printings({@Printings.Printed(ex = Expansion.LORWYN, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class CrypticCommand extends Card
{
	public CrypticCommand(GameState state)
	{
		super(state);

		// Choose two -
		this.setNumModes(new Set(new org.rnd.util.NumberRange(2, 2)));

		// Counter target spell;
		Target target1 = this.addTarget(1, org.rnd.jmagic.engine.generators.Spells.instance(), "target spell");
		this.addEffect(1, counter(targetedBy(target1), "Counter target spell"));

		// or return target permanent to its owner's hand;
		Target target2 = this.addTarget(2, Permanents.instance(), "target permanent");
		this.addEffect(2, bounce(targetedBy(target2), "return target permanent to its owner's hand"));

		// or tap all creatures your opponents control;
		SetGenerator opponentsControl = ControlledBy.instance(OpponentsOf.instance(You.instance()));
		this.addEffect(3, tap(Intersect.instance(CreaturePermanents.instance(), opponentsControl), "tap all creatures your opponents control"));

		// or draw a card.
		this.addEffect(4, drawACard());
	}
}
