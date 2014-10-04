package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Demonic Dread")
@Types({Type.SORCERY})
@ManaCost("1BR")
@ColorIdentity({Color.BLACK, Color.RED})
public final class DemonicDread extends Card
{
	public DemonicDread(GameState state)
	{
		super(state);

		// Cascade
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cascade(state));

		// Target creature can't block this turn.
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		this.addEffect(cantBlockThisTurn(targetedBy(target), "Target creature can't block this turn."));
	}
}
