package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Sands of Delirium")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class SandsofDelirium extends Card
{
	public static final class SandsofDeliriumAbility0 extends ActivatedAbility
	{
		public SandsofDeliriumAbility0(GameState state)
		{
			super(state, "(X), (T): Target player puts the top X cards of his or her library into his or her graveyard.");
			this.setManaCost(new ManaPool("(X)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(millCards(target, ValueOfX.instance(This.instance()), "Target player puts the top X cards of his or her library into his or her graveyard."));
		}
	}

	public SandsofDelirium(GameState state)
	{
		super(state);

		// (X), (T): Target player puts the top X cards of his or her library
		// into his or her graveyard.
		this.addAbility(new SandsofDeliriumAbility0(state));
	}
}
