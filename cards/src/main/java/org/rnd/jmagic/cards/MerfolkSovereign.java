package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Merfolk Sovereign")
@Types({Type.CREATURE})
@SubTypes({SubType.MERFOLK})
@ManaCost("1UU")
@ColorIdentity({Color.BLUE})
public final class MerfolkSovereign extends Card
{
	public static final class MerfolkLord extends StaticAbility
	{
		public MerfolkLord(GameState state)
		{
			super(state, "Other Merfolk creatures you control get +1/+1.");

			SetGenerator otherMerfolkCreaturesYouControl = Intersect.instance(RelativeComplement.instance(Intersect.instance(HasSubType.instance(SubType.MERFOLK), CreaturePermanents.instance()), This.instance()), ControlledBy.instance(You.instance()));

			this.addEffectPart(modifyPowerAndToughness(otherMerfolkCreaturesYouControl, 1, 1));
		}
	}

	public static final class Cloak extends ActivatedAbility
	{
		public Cloak(GameState state)
		{
			super(state, "(T): Target Merfolk creature can't be blocked this turn.");

			this.costsTap = true;

			Target target = this.addTarget(Intersect.instance(CreaturePermanents.instance(), HasSubType.instance(SubType.MERFOLK)), "target Merfolk creature");

			this.addEffect(createFloatingEffect("Target Merfolk creature can't be blocked this turn.", unblockable(targetedBy(target))));
		}
	}

	public MerfolkSovereign(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new MerfolkLord(state));
		this.addAbility(new Cloak(state));
	}
}
