package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Tower Drake")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAKE})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.COMMON), @Printings.Printed(ex = Invasion.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class TowerDrake extends Card
{
	public static final class TowerDrakeAbility1 extends ActivatedAbility
	{
		public TowerDrakeAbility1(GameState state)
		{
			super(state, "(W): Tower Drake gets +0/+1 until end of turn.");
			this.setManaCost(new ManaPool("(W)"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +0, +1, "Tower Drake gets +0/+1 until end of turn."));
		}
	}

	public TowerDrake(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (W): Tower Drake gets +0/+1 until end of turn.
		this.addAbility(new TowerDrakeAbility1(state));
	}
}
