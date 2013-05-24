package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Alabaster Mage")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class AlabasterMage extends Card
{
	public static final class AlabasterMageAbility0 extends ActivatedAbility
	{
		public AlabasterMageAbility0(GameState state)
		{
			super(state, "(1)(W): Target creature you control gains lifelink until end of turn.");
			this.setManaCost(new ManaPool("(1)(W)"));
			SetGenerator target = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));
			this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Lifelink.class, "Target creature you control gains lifelink until end of turn."));
		}
	}

	public AlabasterMage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// (1)(W): Target creature you control gains lifelink until end of turn.
		// (Damage dealt by the creature also causes its controller to gain that
		// much life.)
		this.addAbility(new AlabasterMageAbility0(state));
	}
}
