package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Stonefare Crocodile")
@Types({Type.CREATURE})
@SubTypes({SubType.CROCODILE})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK, Color.GREEN})
public final class StonefareCrocodile extends Card
{
	public static final class StonefareCrocodileAbility0 extends ActivatedAbility
	{
		public StonefareCrocodileAbility0(GameState state)
		{
			super(state, "(2)(B): Stonefare Crocodile gains lifelink until end of turn.");
			this.setManaCost(new ManaPool("(2)(B)"));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Lifelink.class, "Stonefare Crocodile gains lifelink until end of turn."));
		}
	}

	public StonefareCrocodile(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// (2)(B): Stonefare Crocodile gains lifelink until end of turn. (Damage
		// dealt by this creature also causes you to gain that much life.)
		this.addAbility(new StonefareCrocodileAbility0(state));
	}
}
