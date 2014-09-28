package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mardu Skullhunter")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class MarduSkullhunter extends Card
{
	public static final class MarduSkullhunterAbility1 extends EventTriggeredAbility
	{
		public MarduSkullhunterAbility1(GameState state)
		{
			super(state, "When Mardu Skullhunter enters the battlefield, if you attacked with a creature this turn, target opponent discards a card.");
			this.addPattern(whenThisEntersTheBattlefield());

			state.ensureTracker(new org.rnd.jmagic.engine.trackers.AttackTracker());
			this.interveningIf = Raid.instance();

			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
			this.addEffect(discardCards(target, 1, "Target opponent discards a card."));
		}
	}

	public MarduSkullhunter(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Mardu Skullhunter enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// Raid \u2014 When Mardu Skullhunter enters the battlefield, if you
		// attacked with a creature this turn, target opponent discards a card.
		this.addAbility(new MarduSkullhunterAbility1(state));
	}
}
