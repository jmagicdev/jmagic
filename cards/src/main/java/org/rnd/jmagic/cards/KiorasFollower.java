package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kiora's Follower")
@Types({Type.CREATURE})
@SubTypes({SubType.MERFOLK})
@ManaCost("GU")
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class KiorasFollower extends Card
{
	public static final class KiorasFollowerAbility0 extends ActivatedAbility
	{
		public KiorasFollowerAbility0(GameState state)
		{
			super(state, "(T): Untap another target permanent.");
			this.costsTap = true;

			SetGenerator otherPermanents = RelativeComplement.instance(Permanents.instance(), ABILITY_SOURCE_OF_THIS);
			SetGenerator target = targetedBy(this.addTarget(otherPermanents, "another target permanent"));
			this.addEffect(untap(target, "Untap another target permanent."));
		}
	}

	public KiorasFollower(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (T): Untap another target permanent.
		this.addAbility(new KiorasFollowerAbility0(state));
	}
}
