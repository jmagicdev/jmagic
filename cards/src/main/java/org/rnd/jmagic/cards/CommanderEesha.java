package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Commander Eesha")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.BIRD})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.JUDGMENT, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class CommanderEesha extends Card
{
	public CommanderEesha(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// Flying, protection from creatures
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.From(state, CreaturePermanents.instance(), "creatures"));
	}
}
