package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Wolfbitten Captive")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WEREWOLF})
@ManaCost("G")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
@BackFace(KrallenhordeKiller.class)
public final class WolfbittenCaptive extends Card
{
	public static final class WolfbittenCaptiveAbility0 extends ActivatedAbility
	{
		public WolfbittenCaptiveAbility0(GameState state)
		{
			super(state, "(1)(G): Wolfbitten Captive gets +2/+2 until end of turn. Activate this ability only once each turn.");
			this.setManaCost(new ManaPool("(1)(G)"));

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +2, +2, "Wolfbitten Captive gets +2/+2 until end of turn."));
			this.perTurnLimit(1);
		}
	}

	public WolfbittenCaptive(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (1)(G): Wolfbitten Captive gets +2/+2 until end of turn. Activate
		// this ability only once each turn.
		this.addAbility(new WolfbittenCaptiveAbility0(state));

		// At the beginning of each upkeep, if no spells were cast last turn,
		// transform Wolfbitten Captive.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeFuzzy(state, this.getName()));
	}
}
