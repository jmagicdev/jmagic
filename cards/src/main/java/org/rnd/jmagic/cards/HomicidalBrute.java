package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Homicidal Brute")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.MUTANT})
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class HomicidalBrute extends AlternateCard
{
	public static final class HomicidalBruteAbility0 extends EventTriggeredAbility
	{
		public HomicidalBruteAbility0(GameState state)
		{
			super(state, "At the beginning of your end step, if Homicidal Brute didn't attack this turn, tap Homicidal Brute, then transform it.");
			this.addPattern(atTheBeginningOfYourEndStep());
			this.interveningIf = Not.instance(Intersect.instance(AttackedThisTurn.instance(), ABILITY_SOURCE_OF_THIS));
			this.addEffect(tap(ABILITY_SOURCE_OF_THIS, "Tap Homicidal Brute."));
			this.addEffect(transformThis("Homicidal Brute"));

			state.ensureTracker(new SuccessfullyAttacked());
		}
	}

	public HomicidalBrute(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(1);

		this.setColorIndicator(Color.RED);

		// At the beginning of your end step, if Homicidal Brute didn't attack
		// this turn, tap Homicidal Brute, then transform it.
		this.addAbility(new HomicidalBruteAbility0(state));
	}
}
