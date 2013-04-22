package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bala Ged Scorpion")
@Types({Type.CREATURE})
@SubTypes({SubType.SCORPION})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class BalaGedScorpion extends Card
{
	public static final class ScorpionSting extends EventTriggeredAbility
	{
		public ScorpionSting(GameState state)
		{
			super(state, "When Bala Ged Scorpion enters the battlefield, you may destroy target creature with power 1 or less.");

			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(Intersect.instance(CreaturePermanents.instance(), HasPower.instance(Between.instance(null, 1))), "target creature with power 1 or less");

			this.addEffect(youMay(destroy(targetedBy(target), "Destroy target creature with power 1 or less."), "You may destroy target creature with power 1 or less."));
		}
	}

	public BalaGedScorpion(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// When Bala Ged Scorpion enters the battlefield, you may destroy target
		// creature with power 1 or less.
		this.addAbility(new ScorpionSting(state));
	}
}
