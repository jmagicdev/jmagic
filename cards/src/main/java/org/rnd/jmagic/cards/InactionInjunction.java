package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Inaction Injunction")
@Types({Type.SORCERY})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class InactionInjunction extends Card
{
	public InactionInjunction(GameState state)
	{
		super(state);

		state.ensureTracker(new DetainGenerator.Tracker());

		// Detain target creature an opponent controls. (Until your next turn,
		// that creature can't attack or block and its activated abilities can't
		// be activated.)
		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance()))), "target creature an opponent controls"));
		this.addEffect(detain(target, "Detain target creature an opponent controls."));

		state.ensureTracker(new DetainGenerator.Tracker());

		// Draw a card.
		this.addEffect(drawACard());
	}
}
