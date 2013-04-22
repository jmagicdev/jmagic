package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stingscourger")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.WARRIOR})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.PLANAR_CHAOS, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class Stingscourger extends Card
{
	public static final class StingscourgerAbility1 extends EventTriggeredAbility
	{
		public StingscourgerAbility1(GameState state)
		{
			super(state, "When Stingscourger enters the battlefield, return target creature an opponent controls to its owner's hand.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator controlledByOpponent = ControlledBy.instance(OpponentsOf.instance(You.instance()));
			SetGenerator restriction = Intersect.instance(CreaturePermanents.instance(), controlledByOpponent);
			SetGenerator target = targetedBy(this.addTarget(restriction, "target creature an opponent controls"));
			this.addEffect(bounce(target, "Return target creature an opponent controls to its owner's hand."));
		}
	}

	public Stingscourger(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Echo (3)(R) (At the beginning of your upkeep, if this came under your
		// control since the beginning of your last upkeep, sacrifice it unless
		// you pay its echo cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Echo(state, "(3)(R)"));

		// When Stingscourger enters the battlefield, return target creature an
		// opponent controls to its owner's hand.
		this.addAbility(new StingscourgerAbility1(state));
	}
}
