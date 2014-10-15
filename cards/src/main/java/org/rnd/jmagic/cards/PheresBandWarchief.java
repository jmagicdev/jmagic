package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pheres-Band Warchief")
@Types({Type.CREATURE})
@SubTypes({SubType.CENTAUR, SubType.WARRIOR})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class PheresBandWarchief extends Card
{
	public static final class PheresBandWarchiefAbility1 extends StaticAbility
	{
		public PheresBandWarchiefAbility1(GameState state)
		{
			super(state, "Other Centaur creatures you control get +1/+1 and have vigilance and trample.");

			SetGenerator centaurs = Intersect.instance(CREATURES_YOU_CONTROL, HasSubType.instance(SubType.CENTAUR));
			SetGenerator otherCentaurs = RelativeComplement.instance(centaurs, This.instance());
			this.addEffectPart(modifyPowerAndToughness(otherCentaurs, +1, +1));
			this.addEffectPart(addAbilityToObject(otherCentaurs, org.rnd.jmagic.abilities.keywords.Vigilance.class, org.rnd.jmagic.abilities.keywords.Trample.class));
		}
	}

	public PheresBandWarchief(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Vigilance, trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Other Centaur creatures you control get +1/+1 and have vigilance and
		// trample.
		this.addAbility(new PheresBandWarchiefAbility1(state));
	}
}
