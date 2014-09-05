package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ascendant Evincar")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("4BB")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = Nemesis.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class AscendantEvincar extends Card
{
	public AscendantEvincar(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		SetGenerator blackCreatures = Intersect.instance(CreaturePermanents.instance(), HasColor.instance(Color.BLACK));
		SetGenerator otherBlackCreatures = RelativeComplement.instance(blackCreatures, This.instance());
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, otherBlackCreatures, "Other black creatures", +1, +1, true));

		SetGenerator nonBlackCreatures = RelativeComplement.instance(CreaturePermanents.instance(), HasColor.instance(Color.BLACK));
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, nonBlackCreatures, "Nonblack creatures", -1, -1, true));
	}
}
