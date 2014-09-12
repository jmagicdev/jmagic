package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Paragon of Fierce Defiance")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("3R")
@ColorIdentity({Color.RED})
public final class ParagonofFierceDefiance extends Card
{
	public static final class ParagonofFierceDefianceAbility0 extends StaticAbility
	{
		public ParagonofFierceDefianceAbility0(GameState state)
		{
			super(state, "Other red creatures you control get +1/+1.");
			SetGenerator guys = Intersect.instance(HasColor.instance(Color.RED), CREATURES_YOU_CONTROL);
			SetGenerator otherGuys = RelativeComplement.instance(guys, This.instance());
			this.addEffectPart(modifyPowerAndToughness(otherGuys, +1, +1));
		}
	}

	public static final class ParagonofFierceDefianceAbility1 extends ActivatedAbility
	{
		public ParagonofFierceDefianceAbility1(GameState state)
		{
			super(state, "(R), (T): Another target red creature you control gains haste until end of turn.");
			this.setManaCost(new ManaPool("(R)"));
			this.costsTap = true;

			SetGenerator guys = Intersect.instance(HasColor.instance(Color.RED), CREATURES_YOU_CONTROL);
			SetGenerator otherGuys = RelativeComplement.instance(guys, This.instance());
			SetGenerator target = targetedBy(this.addTarget(otherGuys, "another target red creature you control"));
			this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Haste.class, "Another target red creature you control gains haste until end of turn."));
		}
	}

	public ParagonofFierceDefiance(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Other red creatures you control get +1/+1.
		this.addAbility(new ParagonofFierceDefianceAbility0(state));

		// (R), (T): Another target red creature you control gains haste until
		// end of turn. (It can attack and (T) this turn.)
		this.addAbility(new ParagonofFierceDefianceAbility1(state));
	}
}
