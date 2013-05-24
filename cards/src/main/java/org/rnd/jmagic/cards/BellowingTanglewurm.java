package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bellowing Tanglewurm")
@Types({Type.CREATURE})
@SubTypes({SubType.WURM})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class BellowingTanglewurm extends Card
{
	public static final class BellowingTanglewurmAbility1 extends StaticAbility
	{
		public BellowingTanglewurmAbility1(GameState state)
		{
			super(state, "Other green creatures you control have intimidate.");

			SetGenerator otherGreenCreaturesYouControl = RelativeComplement.instance(Intersect.instance(HasColor.instance(Color.GREEN), CREATURES_YOU_CONTROL), This.instance());

			this.addEffectPart(addAbilityToObject(otherGreenCreaturesYouControl, org.rnd.jmagic.abilities.keywords.Intimidate.class));
		}
	}

	public BellowingTanglewurm(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Intimidate (This creature can't be blocked except by artifact
		// creatures and/or creatures that share a color with it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Intimidate(state));

		// Other green creatures you control have intimidate.
		this.addAbility(new BellowingTanglewurmAbility1(state));
	}
}
