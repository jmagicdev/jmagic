package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Isperia's Skywatch")
@Types({Type.CREATURE})
@SubTypes({SubType.KNIGHT, SubType.VEDALKEN})
@ManaCost("5U")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class IsperiasSkywatch extends Card
{
	public static final class IsperiasSkywatchAbility1 extends EventTriggeredAbility
	{
		public IsperiasSkywatchAbility1(GameState state)
		{
			super(state, "When Isperia's Skywatch enters the battlefield, detain target creature an opponent controls.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance()))), "target creature an opponent controls"));

			this.addEffect(detain(target, "Detain target creature an opponent controls."));

			state.ensureTracker(new DetainGenerator.Tracker());
		}
	}

	public IsperiasSkywatch(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Isperia's Skywatch enters the battlefield, detain target
		// creature an opponent controls. (Until your next turn, that creature
		// can't attack or block and its activated abilities can't be
		// activated.)
		this.addAbility(new IsperiasSkywatchAbility1(state));
	}
}
