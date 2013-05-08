package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Master of the Pearl Trident")
@Types({Type.CREATURE})
@SubTypes({SubType.MERFOLK})
@ManaCost("UU")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class MasterofthePearlTrident extends Card
{
	public static final class MasterofthePearlTridentAbility0 extends StaticAbility
	{
		public MasterofthePearlTridentAbility0(GameState state)
		{
			super(state, "Other Merfolk creatures you control get +1/+1 and have islandwalk.");

			SetGenerator fish = Intersect.instance(HasSubType.instance(SubType.MERFOLK), CREATURES_YOU_CONTROL);
			SetGenerator otherFish = RelativeComplement.instance(fish, This.instance());
			this.addEffectPart(modifyPowerAndToughness(otherFish, +1, +1));
			this.addEffectPart(addAbilityToObject(otherFish, org.rnd.jmagic.abilities.keywords.Landwalk.Islandwalk.class));
		}
	}

	public MasterofthePearlTrident(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Other Merfolk creatures you control get +1/+1 and have islandwalk.
		// (They are unblockable as long as defending player controls an
		// Island.)
		this.addAbility(new MasterofthePearlTridentAbility0(state));
	}
}
