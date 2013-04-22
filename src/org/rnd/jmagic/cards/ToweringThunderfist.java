package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Towering Thunderfist")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT, SubType.SOLDIER})
@ManaCost("4R")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class ToweringThunderfist extends Card
{
	public static final class ToweringThunderfistAbility0 extends ActivatedAbility
	{
		public ToweringThunderfistAbility0(GameState state)
		{
			super(state, "(W): Towering Thunderfist gains vigilance until end of turn.");
			this.setManaCost(new ManaPool("(W)"));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Vigilance.class, "Towering Thunderfist gains vigilance until end of turn."));
		}
	}

	public ToweringThunderfist(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// (W): Towering Thunderfist gains vigilance until end of turn.
		this.addAbility(new ToweringThunderfistAbility0(state));
	}
}
