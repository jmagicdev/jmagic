package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Paragon of Gathering Mists")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class ParagonofGatheringMists extends Card
{
	public static final class ParagonofGatheringMistsAbility0 extends StaticAbility
	{
		public ParagonofGatheringMistsAbility0(GameState state)
		{
			super(state, "Other blue creatures you control get +1/+1.");
			SetGenerator guys = Intersect.instance(HasColor.instance(Color.BLUE), CREATURES_YOU_CONTROL);
			SetGenerator otherGuys = RelativeComplement.instance(guys, This.instance());
			this.addEffectPart(modifyPowerAndToughness(otherGuys, +1, +1));
		}
	}

	public static final class ParagonofGatheringMistsAbility1 extends ActivatedAbility
	{
		public ParagonofGatheringMistsAbility1(GameState state)
		{
			super(state, "(U), (T): Another target blue creature you control gains flying until end of turn.");
			this.setManaCost(new ManaPool("(U)"));
			this.costsTap = true;

			SetGenerator guys = Intersect.instance(HasColor.instance(Color.BLUE), CREATURES_YOU_CONTROL);
			SetGenerator otherGuys = RelativeComplement.instance(guys, This.instance());
			SetGenerator target = targetedBy(this.addTarget(otherGuys, "another target blue creature you control"));
			this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Flying.class, "Another target blue creature you control gains flying until end of turn."));
		}
	}

	public ParagonofGatheringMists(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Other blue creatures you control get +1/+1.
		this.addAbility(new ParagonofGatheringMistsAbility0(state));

		// (U), (T): Another target blue creature you control gains flying until
		// end of turn.
		this.addAbility(new ParagonofGatheringMistsAbility1(state));
	}
}
