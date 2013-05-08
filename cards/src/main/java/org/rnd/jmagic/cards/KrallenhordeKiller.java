package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Krallenhorde Killer")
@Types({Type.CREATURE})
@SubTypes({SubType.WEREWOLF})
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class KrallenhordeKiller extends AlternateCard
{
	public static final class KrallenhordeKillerAbility0 extends ActivatedAbility
	{
		public KrallenhordeKillerAbility0(GameState state)
		{
			super(state, "(3)(G): Krallenhorde Killer gets +4/+4 until end of turn. Activate this ability only once each turn.");
			this.setManaCost(new ManaPool("(3)(G)"));

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +4, +4, "Krallenhorde Killer gets +4/+4 until end of turn."));
			this.perTurnLimit(1);
		}
	}

	public KrallenhordeKiller(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.setColorIndicator(Color.GREEN);

		// (3)(G): Krallenhorde Killer gets +4/+4 until end of turn. Activate
		// this ability only once each turn.
		this.addAbility(new KrallenhordeKillerAbility0(state));

		// At the beginning of each upkeep, if a player cast two or more spells
		// last turn, transform Krallenhorde Killer.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeHuman(state, this.getName()));
	}
}
