package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Selfless Cathar")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.HUMAN})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class SelflessCathar extends Card
{
	public static final class SelflessCatharAbility0 extends ActivatedAbility
	{
		public SelflessCatharAbility0(GameState state)
		{
			super(state, "(1)(W), Sacrifice Selfless Cathar: Creatures you control get +1/+1 until end of turn.");
			this.setManaCost(new ManaPool("(1)(W)"));
			this.addCost(sacrificeThis("Selfless Cathar"));
			this.addEffect(ptChangeUntilEndOfTurn(CREATURES_YOU_CONTROL, +1, +1, "Creatures you control get +1/+1 until end of turn."));
		}
	}

	public SelflessCathar(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (1)(W), Sacrifice Selfless Cathar: Creatures you control get +1/+1
		// until end of turn.
		this.addAbility(new SelflessCatharAbility0(state));
	}
}
