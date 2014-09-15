package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mischief and Mayhem")
@Types({Type.SORCERY})
@ManaCost("4G")
@ColorIdentity({Color.GREEN})
public final class MischiefandMayhem extends Card
{
	public MischiefandMayhem(GameState state)
	{
		super(state);

		// Up to two target creatures each get +4/+4 until end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "up to two target creatures").setNumber(0, 2));
		this.addEffect(ptChangeUntilEndOfTurn(target, +4, +4, "Up to two target creatures each get +4/+4 until end of turn."));
	}
}
