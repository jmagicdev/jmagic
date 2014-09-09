package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tendrils of Agony")
@Types({Type.SORCERY})
@ManaCost("2BB")
@ColorIdentity({Color.BLACK})
public final class TendrilsofAgony extends Card
{
	public TendrilsofAgony(GameState state)
	{
		super(state);

		// Target player loses 2 life and you gain 2 life.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		this.addEffect(loseLife(target, 2, "Target player loses 2 life"));
		this.addEffect(gainLife(You.instance(), 2, "and you gain 2 life."));

		// Storm
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Storm(state));
	}
}
