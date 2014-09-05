package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Serra Angel")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2012.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2011.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = EighthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.RARE), @Printings.Printed(ex = FourthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class SerraAngel extends Card
{
	public SerraAngel(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));
	}
}
