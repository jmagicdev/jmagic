package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Shivan Dragon")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("4RR")
@Printings({@Printings.Printed(ex = Magic2010.class, r = Rarity.RARE), @Printings.Printed(ex = FromTheVaultDragons.class, r = Rarity.RARE), @Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = EighthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.RARE), @Printings.Printed(ex = FifthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = FourthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.RARE), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.RARE), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.RARE), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class ShivanDragon extends Card
{
	public ShivanDragon(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.Firebreathing(state, this.getName()));
	}
}
