package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Cadaver Imp")
@Types({Type.CREATURE})
@SubTypes({SubType.IMP})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class CadaverImp extends Card
{

	public CadaverImp(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Cadaver Imp enters the battlefield, you may return target
		// creature card from your graveyard to your hand.
		this.addAbility(new org.rnd.jmagic.abilities.Gravedigging(state, "Cadaver Imp"));
	}
}
