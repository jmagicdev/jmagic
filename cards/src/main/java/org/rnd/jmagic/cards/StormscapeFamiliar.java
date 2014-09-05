package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Stormscape Familiar")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = TimeSpiral.class, r = Rarity.SPECIAL), @Printings.Printed(ex = Planeshift.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class StormscapeFamiliar extends Card
{
	public StormscapeFamiliar(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new org.rnd.jmagic.abilities.CostsYouLessToCast(state, HasColor.instance(Color.WHITE, Color.BLACK), "(1)", "White spells and black spells you cast cost (1) less to cast."));
	}
}
