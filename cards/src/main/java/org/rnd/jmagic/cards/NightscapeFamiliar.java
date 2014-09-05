package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Nightscape Familiar")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Planeshift.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class NightscapeFamiliar extends Card
{
	public NightscapeFamiliar(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Blue spells and red spells you cast cost (1) less to cast.
		this.addAbility(new org.rnd.jmagic.abilities.CostsYouLessToCast(state, HasColor.instance(Color.BLUE, Color.RED), "(1)", "Blue spells and red spells you cast cost (1) less to cast."));

		// (1)(B): Regenerate Nightscape Familiar.
		this.addAbility(new org.rnd.jmagic.abilities.Regenerate.Final(state, "(1)(B)", this.getName()));
	}
}
