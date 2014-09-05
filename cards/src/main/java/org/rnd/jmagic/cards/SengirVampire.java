package org.rnd.jmagic.cards;

import org.rnd.jmagic.abilities.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Sengir Vampire")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("3BB")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = Torment.class, r = Rarity.RARE), @Printings.Printed(ex = FourthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class SengirVampire extends Card
{
	public SengirVampire(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new VampireKill(state, "Sengir Vampire"));
	}
}
