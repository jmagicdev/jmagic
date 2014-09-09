package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Lantern Spirit")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class LanternSpirit extends Card
{
	public static final class LanternSpiritAbility1 extends ActivatedAbility
	{
		public LanternSpiritAbility1(GameState state)
		{
			super(state, "(U): Return Lantern Spirit to its owner's hand.");
			this.setManaCost(new ManaPool("(U)"));
			this.addEffect(bounce(ABILITY_SOURCE_OF_THIS, "Return Lantern Spirit to its owner's hand."));
		}
	}

	public LanternSpirit(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (U): Return Lantern Spirit to its owner's hand.
		this.addAbility(new LanternSpiritAbility1(state));
	}
}
