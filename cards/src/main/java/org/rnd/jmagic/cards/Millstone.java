package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Millstone")
@Types({Type.ARTIFACT})
@ManaCost("2")
@ColorIdentity({})
public final class Millstone extends Card
{
	public static final class Mill extends ActivatedAbility
	{
		public Mill(GameState state)
		{
			super(state, "(2), (T): Target player puts the top two cards of his or her library into his or her graveyard.");

			this.setManaCost(new ManaPool("2"));

			this.costsTap = true;

			Target target = this.addTarget(Players.instance(), "target player");

			this.addEffect(millCards(targetedBy(target), 2, "Target player puts the top two cards of his or her library into his or her graveyard."));
		}
	}

	public Millstone(GameState state)
	{
		super(state);

		this.addAbility(new Mill(state));
	}
}
