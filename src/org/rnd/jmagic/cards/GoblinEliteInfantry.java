package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Goblin Elite Infantry")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.GOBLIN})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MIRAGE, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class GoblinEliteInfantry extends Card
{
	public static final class Coward extends EventTriggeredAbility
	{
		public Coward(GameState state)
		{
			super(state, "Whenever Goblin Elite Infantry blocks or becomes blocked, it gets -1/-1 until end of turn.");
			this.addPattern(whenThisBlocks());
			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_BLOCKED);
			pattern.put(EventType.Parameter.ATTACKER, thisCard);
			this.addPattern(pattern);

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, (-1), (-1), "Goblin Elite Infantry gets -1/-1 until end of turn."));
		}
	}

	public GoblinEliteInfantry(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new Coward(state));
	}
}
