package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Troll Ascetic")
@Types({Type.CREATURE})
@SubTypes({SubType.TROLL, SubType.SHAMAN})
@ManaCost("1GG")
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.RARE), @Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = Mirrodin.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class TrollAscetic extends Card
{
	public TrollAscetic(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Hexproof(state));
		this.addAbility(new org.rnd.jmagic.abilities.Regenerate.Final(state, "(1)(G)", this.getName()));
	}
}
