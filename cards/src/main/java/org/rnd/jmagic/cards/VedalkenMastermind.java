package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vedalken Mastermind")
@Types({Type.CREATURE})
@SubTypes({SubType.VEDALKEN, SubType.WIZARD})
@ManaCost("UU")
@ColorIdentity({Color.BLUE})
public final class VedalkenMastermind extends Card
{
	public static final class BounceYourStuff extends ActivatedAbility
	{
		public BounceYourStuff(GameState state)
		{
			super(state, "(U), (T): Return target permanent you control to its owner's hand.");

			this.setManaCost(new ManaPool("U"));
			this.costsTap = true;

			SetGenerator targetable = ControlledBy.instance(You.instance());
			Target target = this.addTarget(targetable, "target permanent you control");

			this.addEffect(bounce(targetedBy(target), "Return target permanent you control to its owner's hand."));
		}
	}

	public VedalkenMastermind(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// U, T Return target permanent you control to its owner's hand.
		this.addAbility(new BounceYourStuff(state));
	}
}
