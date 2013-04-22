package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Etherium Sculptor")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.ARTIFICER, SubType.VEDALKEN})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class EtheriumSculptor extends Card
{
	public EtheriumSculptor(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.CostsYouLessToCast(state, HasType.instance(Type.ARTIFACT), "1", "Artifact spells you cast cost (1) less to cast."));
	}
}
