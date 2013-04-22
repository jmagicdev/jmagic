package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lighthouse Chronologist")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE})
public final class LighthouseChronologist extends Card
{
	public static final class ExtraTurns extends EventTriggeredAbility
	{
		public ExtraTurns(GameState state)
		{
			super(state, "At the beginning of each end step, if it's not your turn, take an extra turn after this one.");
			this.addPattern(atTheBeginningOfTheEndStep());

			// i'm a dirty cheater. so what? :-P -rw
			SetGenerator itsNotYourTurn = RelativeComplement.instance(CurrentStep.instance(), EndStepOf.instance(You.instance()));
			this.interveningIf = itsNotYourTurn;

			this.addEffect(takeExtraTurns(You.instance(), 1, "Take an extra turn after this one."));
		}
	}

	public LighthouseChronologist(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// Level up (U)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.LevelUp(state, "(U)"));

		// LEVEL 4-6
		// 2/4
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 4, 6, 2, 4));

		// LEVEL 7+
		// 3/5
		// At the beginning of each end step, if it's not your turn, take an
		// extra turn after this one.
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 7, 3, 5, "At the beginning of each end step, if it's not your turn, take an extra turn after this one.", ExtraTurns.class));
	}
}
