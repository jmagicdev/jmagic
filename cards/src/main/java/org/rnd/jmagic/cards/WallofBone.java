package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Wall of Bone")
@Types({Type.CREATURE})
@SubTypes({SubType.WALL, SubType.SKELETON})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Magic2010.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = FifthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = FourthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class WallofBone extends Card
{
	public WallofBone(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));
		this.addAbility(new org.rnd.jmagic.abilities.Regenerate.Final(state, "(B)", this.getName()));
	}
}
