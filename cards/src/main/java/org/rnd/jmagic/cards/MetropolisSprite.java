package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Metropolis Sprite")
@Types({Type.CREATURE})
@SubTypes({SubType.FAERIE, SubType.ROGUE})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class MetropolisSprite extends Card
{
	public static final class MetropolisSpriteAbility1 extends ActivatedAbility
	{
		public MetropolisSpriteAbility1(GameState state)
		{
			super(state, "(U): Metropolis Sprite gets +1/-1 until end of turn.");
			this.setManaCost(new ManaPool("(U)"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, -1, "Metropolis Sprite gets +1/-1 until end of turn."));
		}
	}

	public MetropolisSprite(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (U): Metropolis Sprite gets +1/-1 until end of turn.
		this.addAbility(new MetropolisSpriteAbility1(state));
	}
}
