package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sign in Blood")
@Types({Type.SORCERY})
@ManaCost("BB")
@ColorIdentity({Color.BLACK})
public final class SigninBlood extends Card
{
	public SigninBlood(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");

		SetGenerator targetPlayer = targetedBy(target);

		this.addEffect(drawCards(targetedBy(target), 2, "Target player draws two cards"));
		this.addEffect(loseLife(targetPlayer, 2, "and loses 2 life."));
	}
}
