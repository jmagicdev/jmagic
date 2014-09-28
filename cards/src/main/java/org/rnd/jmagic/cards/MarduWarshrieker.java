package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mardu Warshrieker")
@Types({Type.CREATURE})
@SubTypes({SubType.ORC, SubType.SHAMAN})
@ManaCost("3R")
@ColorIdentity({Color.RED, Color.WHITE, Color.BLACK})
public final class MarduWarshrieker extends Card
{
	public static final class MarduWarshriekerAbility0 extends EventTriggeredAbility
	{
		public MarduWarshriekerAbility0(GameState state)
		{
			super(state, "When Mardu Warshrieker enters the battlefield, if you attacked with a creature this turn, add (R)(W)(B) to your mana pool.");
			this.addPattern(whenThisEntersTheBattlefield());

			state.ensureTracker(new org.rnd.jmagic.engine.trackers.AttackTracker());
			this.interveningIf = Raid.instance();

			this.addEffect(addManaToYourManaPoolFromAbility("(R)(W)(B)"));
		}
	}

	public MarduWarshrieker(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Raid \u2014 When Mardu Warshrieker enters the battlefield, if you
		// attacked with a creature this turn, add (R)(W)(B) to your mana pool.
		this.addAbility(new MarduWarshriekerAbility0(state));
	}
}
