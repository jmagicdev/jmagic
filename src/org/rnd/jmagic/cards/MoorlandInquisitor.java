package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Moorland Inquisitor")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class MoorlandInquisitor extends Card
{
	public static final class MoorlandInquisitorAbility0 extends ActivatedAbility
	{
		public MoorlandInquisitorAbility0(GameState state)
		{
			super(state, "(2)(W): Moorland Inquisitor gains first strike until end of turn.");
			this.setManaCost(new ManaPool("(2)(W)"));

			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.FirstStrike.class, "Moorland Inquisitor gains first strike until end of turn."));
		}
	}

	public MoorlandInquisitor(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (2)(W): Moorland Inquisitor gains first strike until end of turn.
		this.addAbility(new MoorlandInquisitorAbility0(state));
	}
}
