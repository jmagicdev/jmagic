package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Markov Warlord")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.VAMPIRE})
@ManaCost("5R")
@ColorIdentity({Color.RED})
public final class MarkovWarlord extends Card
{
	public static final class MarkovWarlordAbility1 extends EventTriggeredAbility
	{
		public MarkovWarlordAbility1(GameState state)
		{
			super(state, "When Markov Warlord enters the battlefield, up to two target creatures can't block this turn.");
			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(CreaturePermanents.instance(), "up to two target creatures");
			target.setNumber(0, 2);

			this.addEffect(cantBlockThisTurn(targetedBy(target), "Up to two target creatures can't block this turn."));
		}
	}

	public MarkovWarlord(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// When Markov Warlord enters the battlefield, up to two target
		// creatures can't block this turn.
		this.addAbility(new MarkovWarlordAbility1(state));
	}
}
