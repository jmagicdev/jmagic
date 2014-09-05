package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Goblin King")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN})
@ManaCost("1RR")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = EighthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.RARE), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = FifthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = FourthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.RARE), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.RARE), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.RARE), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class GoblinKing extends Card
{

	public GoblinKing(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		SetGenerator otherGoblinCreatures = RelativeComplement.instance(Intersect.instance(CreaturePermanents.instance(), HasSubType.instance(SubType.GOBLIN)), This.instance());
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, otherGoblinCreatures, "Other Goblin creatures", +1, +1, org.rnd.jmagic.abilities.keywords.Landwalk.Mountainwalk.class, true));
	}
}
