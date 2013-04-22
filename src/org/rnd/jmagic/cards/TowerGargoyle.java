package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Tower Gargoyle")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.GARGOYLE})
@ManaCost("1WUB")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.BLACK})
public final class TowerGargoyle extends Card
{
	public TowerGargoyle(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
