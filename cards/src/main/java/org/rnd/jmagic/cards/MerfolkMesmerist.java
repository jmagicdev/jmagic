package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Merfolk Mesmerist")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.MERFOLK})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class MerfolkMesmerist extends Card
{
	public static final class MerfolkMesmeristAbility0 extends ActivatedAbility
	{
		public MerfolkMesmeristAbility0(GameState state)
		{
			super(state, "(U), (T): Target player puts the top two cards of his or her library into his or her graveyard.");
			this.setManaCost(new ManaPool("(U)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(millCards(target, 2, "Target player puts the top two cards of his or her library into his or her graveyard."));
		}
	}

	public MerfolkMesmerist(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// (U), (T): Target player puts the top two cards of his or her library
		// into his or her graveyard.
		this.addAbility(new MerfolkMesmeristAbility0(state));
	}
}
