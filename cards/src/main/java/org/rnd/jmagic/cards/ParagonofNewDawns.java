package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Paragon of New Dawns")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class ParagonofNewDawns extends Card
{
	public static final class ParagonofNewDawnsAbility0 extends StaticAbility
	{
		public ParagonofNewDawnsAbility0(GameState state)
		{
			super(state, "Other white creatures you control get +1/+1.");
			SetGenerator guys = Intersect.instance(HasColor.instance(Color.WHITE), CREATURES_YOU_CONTROL);
			SetGenerator otherGuys = RelativeComplement.instance(guys, This.instance());
			this.addEffectPart(modifyPowerAndToughness(otherGuys, +1, +1));
		}
	}

	public static final class ParagonofNewDawnsAbility1 extends ActivatedAbility
	{
		public ParagonofNewDawnsAbility1(GameState state)
		{
			super(state, "(W), (T): Another target white creature you control gains vigilance until end of turn.");
			this.setManaCost(new ManaPool("(W)"));
			this.costsTap = true;

			SetGenerator guys = Intersect.instance(HasColor.instance(Color.WHITE), CREATURES_YOU_CONTROL);
			SetGenerator otherGuys = RelativeComplement.instance(guys, This.instance());
			SetGenerator target = targetedBy(this.addTarget(otherGuys, "another target white creature you control"));
			this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Vigilance.class, "Another target white creature you control gains vigilance until end of turn."));
		}
	}

	public ParagonofNewDawns(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Other white creatures you control get +1/+1.
		this.addAbility(new ParagonofNewDawnsAbility0(state));

		// (W), (T): Another target white creature you control gains vigilance
		// until end of turn. (Attacking doesn't cause it to tap.)
		this.addAbility(new ParagonofNewDawnsAbility1(state));
	}
}
