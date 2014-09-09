package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Myr Galvanizer")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.MYR})
@ManaCost("3")
@ColorIdentity({})
public final class MyrGalvanizer extends Card
{
	public static final class MyrGalvanizerAbility0 extends StaticAbility
	{
		public MyrGalvanizerAbility0(GameState state)
		{
			super(state, "Other Myr creatures you control get +1/+1.");
			SetGenerator eachOtherMyrCreature = RelativeComplement.instance(Intersect.instance(CREATURES_YOU_CONTROL, HasSubType.instance(SubType.MYR)), This.instance());
			this.addEffectPart(modifyPowerAndToughness(eachOtherMyrCreature, +1, +1));
		}
	}

	public static final class MyrGalvanizerAbility1 extends ActivatedAbility
	{
		public MyrGalvanizerAbility1(GameState state)
		{
			super(state, "(1), (T): Untap each other Myr you control.");
			this.setManaCost(new ManaPool("(1)"));
			this.costsTap = true;
			SetGenerator eachOtherMyr = RelativeComplement.instance(Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.MYR)), ABILITY_SOURCE_OF_THIS);
			this.addEffect(untap(eachOtherMyr, "Untap each other Myr you control."));
		}
	}

	public MyrGalvanizer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Other Myr creatures you control get +1/+1.
		this.addAbility(new MyrGalvanizerAbility0(state));

		// (1), (T): Untap each other Myr you control.
		this.addAbility(new MyrGalvanizerAbility1(state));
	}
}
