package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mirran Crusader")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.KNIGHT})
@ManaCost("1WW")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class MirranCrusader extends Card
{
	public MirranCrusader(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Double strike, protection from black and from green
		this.addAbility(new org.rnd.jmagic.abilities.keywords.DoubleStrike(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.From(state, HasColor.instance(Color.BLACK, Color.GREEN), "black and from green"));
	}
}
