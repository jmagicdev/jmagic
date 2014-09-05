package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Frogtosser Banneret")
@Types({Type.CREATURE})
@SubTypes({SubType.ROGUE, SubType.GOBLIN})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Morningtide.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class FrogtosserBanneret extends Card
{
	public FrogtosserBanneret(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		this.addAbility(new org.rnd.jmagic.abilities.CostsYouLessToCast(state, HasSubType.instance(SubType.GOBLIN, SubType.ROGUE), "(1)", "Goblin spells and Rogue spells you cast cost (1) less to cast."));
	}
}
