package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nessian Game Warden")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("3GG")
@ColorIdentity({Color.GREEN})
public final class NessianGameWarden extends Card
{
	public static final class NessianGameWardenAbility0 extends EventTriggeredAbility
	{
		public NessianGameWardenAbility0(GameState state)
		{
			super(state, "When Nessian Game Warden enters the battlefield, look at the top X cards of your library, where X is the number of Forests you control. You may reveal a creature card from among them and put it into your hand. Put the rest on the bottom of your library in any order.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator yourForests = Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.FOREST));
			SetGenerator X = Count.instance(yourForests);

			this.addEffect(Sifter.start().look(X).take(1, HasType.instance(Type.CREATURE)).dumpToBottom().getEventFactory("Look at the top X cards of your library, where X is the number of Forests you control. You may reveal a creature card from among them and put it into your hand. Put the rest on the bottom of your library in any order."));
		}
	}

	public NessianGameWarden(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);

		// When Nessian Game Warden enters the battlefield, look at the top X
		// cards of your library, where X is the number of Forests you control.
		// You may reveal a creature card from among them and put it into your
		// hand. Put the rest on the bottom of your library in any order.
		this.addAbility(new NessianGameWardenAbility0(state));
	}
}
