package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Armament Master")
@Types({Type.CREATURE})
@SubTypes({SubType.KOR, SubType.SOLDIER})
@ManaCost("WW")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class ArmamentMaster extends Card
{
	public static final class WhenImHoldingHammersMyFamilyGetsReallyAngry extends StaticAbility
	{
		public WhenImHoldingHammersMyFamilyGetsReallyAngry(GameState state)
		{
			super(state, "Other Kor creatures you control get +2/+2 for each Equipment attached to Armament Master.");

			SetGenerator otherKor = Intersect.instance(ControlledBy.instance(You.instance()), RelativeComplement.instance(Intersect.instance(CreaturePermanents.instance(), HasSubType.instance(SubType.KOR)), This.instance()));
			SetGenerator pump = Multiply.instance(numberGenerator(2), Count.instance(Intersect.instance(HasSubType.instance(SubType.EQUIPMENT), AttachedTo.instance(This.instance()))));

			this.addEffectPart(modifyPowerAndToughness(otherKor, pump, pump));
		}
	}

	public ArmamentMaster(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new WhenImHoldingHammersMyFamilyGetsReallyAngry(state));
	}
}
