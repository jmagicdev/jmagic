package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lord of Atlantis")
@Types({Type.CREATURE})
@SubTypes({SubType.MERFOLK})
@ManaCost("UU")
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.SPECIAL), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FOURTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.REVISED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.BETA, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.RARE)})
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
