package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bosk Banneret")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.TREEFOLK})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.MORNINGTIDE, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class BoskBanneret extends Card
{
	public BoskBanneret(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// Treefolk spells and Shaman spells you cast cost (1) less to cast.
		this.addAbility(new org.rnd.jmagic.abilities.CostsYouLessToCast(state, HasSubType.instance(SubType.TREEFOLK, SubType.SHAMAN), "(1)", "Treefolk spells and Shaman spells you cast cost (1) less to cast."));
	}
}
