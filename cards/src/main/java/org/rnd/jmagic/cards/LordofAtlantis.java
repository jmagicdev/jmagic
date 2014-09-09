package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lord of Atlantis")
@Types({Type.CREATURE})
@SubTypes({SubType.MERFOLK})
@ManaCost("UU")
@ColorIdentity({Color.BLUE})
public final class LordofAtlantis extends Card
{
	public static final class LordofAtlantisAbility0 extends StaticAbility
	{
		public LordofAtlantisAbility0(GameState state)
		{
			super(state, "Other Merfolk creatures get +1/+1 and have islandwalk.");

			SetGenerator fish = Intersect.instance(HasSubType.instance(SubType.MERFOLK), CreaturePermanents.instance());
			SetGenerator otherFish = RelativeComplement.instance(fish, This.instance());
			this.addEffectPart(modifyPowerAndToughness(otherFish, +1, +1));
			this.addEffectPart(addAbilityToObject(otherFish, org.rnd.jmagic.abilities.keywords.Landwalk.Islandwalk.class));
		}
	}

	public LordofAtlantis(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Other Merfolk creatures get +1/+1 and have islandwalk.
		this.addAbility(new LordofAtlantisAbility0(state));
	}
}
