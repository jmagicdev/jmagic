package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Elvish Champion")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF})
@ManaCost("1GG")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = EighthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.RARE), @Printings.Printed(ex = Invasion.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class ElvishChampion extends Card
{

	public ElvishChampion(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		SetGenerator otherElfCreatures = RelativeComplement.instance(Intersect.instance(CreaturePermanents.instance(), HasSubType.instance(SubType.ELF)), This.instance());
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, otherElfCreatures, "Other Elf creatures", +1, +1, org.rnd.jmagic.abilities.keywords.Landwalk.Forestwalk.class, true));
	}
}
