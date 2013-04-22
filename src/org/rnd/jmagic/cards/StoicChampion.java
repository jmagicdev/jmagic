package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Stoic Champion")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("WW")
@Printings({@Printings.Printed(ex = Expansion.LEGIONS, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class StoicChampion extends Card
{
	public static final class CyclePump extends EventTriggeredAbility
	{
		public CyclePump(GameState state)
		{
			super(state, "Whenever a player cycles a card, Stoic Champion gets +2/+2 until end of turn.");

			this.addPattern(whenAPlayerCyclesACard());

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +2, +2, "Stoic Champion gets +2/+2 until end of turn."));
		}
	}

	public StoicChampion(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new CyclePump(state));
	}

}
