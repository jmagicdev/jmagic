package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Goblin King")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN})
@ManaCost("1RR")
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
