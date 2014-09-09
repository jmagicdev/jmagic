package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nephalia Drownyard")
@Types({Type.LAND})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class NephaliaDrownyard extends Card
{
	public static final class NephaliaDrownyardAbility1 extends ActivatedAbility
	{
		public NephaliaDrownyardAbility1(GameState state)
		{
			super(state, "(1)(U)(B), (T): Target player puts the top three cards of his or her library into his or her graveyard.");
			this.setManaCost(new ManaPool("(1)(U)(B)"));
			this.costsTap = true;
			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(millCards(target, 3, "Target player puts the top three cards of his or her library into his or her graveyard."));
		}
	}

	public NephaliaDrownyard(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (1)(U)(B), (T): Target player puts the top three cards of his or her
		// library into his or her graveyard.
		this.addAbility(new NephaliaDrownyardAbility1(state));
	}
}
