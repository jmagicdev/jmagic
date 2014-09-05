package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Geralf's Messenger")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("BBB")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class GeralfsMessenger extends Card
{
	public static final class GeralfsMessengerAbility1 extends EventTriggeredAbility
	{
		public GeralfsMessengerAbility1(GameState state)
		{
			super(state, "When Geralf's Messenger enters the battlefield, target opponent loses 2 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
			this.addEffect(loseLife(target, 2, "Target opponent loses 2 life."));
		}
	}

	public GeralfsMessenger(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Geralf's Messenger enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Geralf's Messenger enters the battlefield, target opponent loses
		// 2 life.
		this.addAbility(new GeralfsMessengerAbility1(state));

		// Undying (When this creature dies, if it had no +1/+1 counters on it,
		// return it to the battlefield under its owner's control with a +1/+1
		// counter on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Undying(state));
	}
}
