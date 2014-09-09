package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stromkirk Captain")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.VAMPIRE})
@ManaCost("1BR")
@ColorIdentity({Color.BLACK, Color.RED})
public final class StromkirkCaptain extends Card
{
	public static final class StromkirkCaptainAbility1 extends StaticAbility
	{
		public StromkirkCaptainAbility1(GameState state)
		{
			super(state, "Other Vampire creatures you control get +1/+1 and have first strike.");

			SetGenerator otherVampires = RelativeComplement.instance(Intersect.instance(HasSubType.instance(SubType.VAMPIRE), CREATURES_YOU_CONTROL), This.instance());
			this.addEffectPart(modifyPowerAndToughness(otherVampires, +1, +1));
			this.addEffectPart(addAbilityToObject(otherVampires, org.rnd.jmagic.abilities.keywords.FirstStrike.class));
		}
	}

	public StromkirkCaptain(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// First strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		// Other Vampire creatures you control get +1/+1 and have first strike.
		this.addAbility(new StromkirkCaptainAbility1(state));
	}
}
