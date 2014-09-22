package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lyev Skyknight")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.KNIGHT})
@ManaCost("1WU")
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class LyevSkyknight extends Card
{
	public static final class LyevSkyknightAbility1 extends EventTriggeredAbility
	{
		public LyevSkyknightAbility1(GameState state)
		{
			super(state, "When Lyev Skyknight enters the battlefield, detain target nonland permanent an opponent controls.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.LAND)), ControlledBy.instance(OpponentsOf.instance(You.instance()))), "target nonland permanent an opponent controls"));

			this.addEffect(detain(target, "Detain target nonland permanent an opponent controls."));

			state.ensureTracker(new DetainGenerator.Tracker());
		}
	}

	public LyevSkyknight(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Lyev Skyknight enters the battlefield, detain target nonland
		// permanent an opponent controls. (Until your next turn, that permanent
		// can't attack or block and its activated abilities can't be
		// activated.)
		this.addAbility(new LyevSkyknightAbility1(state));
	}
}
