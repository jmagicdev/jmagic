package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Bladed Sentinel")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class BladedSentinel extends Card
{
	public static final class BladedSentinelAbility0 extends ActivatedAbility
	{
		public BladedSentinelAbility0(GameState state)
		{
			super(state, "(W): Bladed Sentinel gains vigilance until end of turn.");
			this.setManaCost(new ManaPool("(W)"));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Vigilance.class, "Bladed Sentinel gains vigilance until end of turn."));
		}
	}

	public BladedSentinel(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// (W): Bladed Sentinel gains vigilance until end of turn.
		this.addAbility(new BladedSentinelAbility0(state));
	}
}
