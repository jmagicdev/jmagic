package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Talus Paladin")
@Types({Type.CREATURE})
@SubTypes({SubType.ALLY, SubType.HUMAN, SubType.KNIGHT})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class TalusPaladin extends Card
{
	public static final class AllyLifelink extends EventTriggeredAbility
	{
		public AllyLifelink(GameState state)
		{
			super(state, "Whenever Talus Paladin or another Ally enters the battlefield under your control, you may have Allies you control gain lifelink until end of turn, and you may put a +1/+1 counter on Talus Paladin.");
			this.addPattern(allyTrigger());

			EventFactory lifelink = addAbilityUntilEndOfTurn(ALLIES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Lifelink.class, "Allies you control gain lifelink until end of turn");
			this.addEffect(youMay(lifelink, "You may have Allies you control gain lifelink until end of turn,"));

			EventFactory counter = putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Talus Paladin");
			this.addEffect(youMay(counter, "and you may put a +1/+1 counter on Talus Paladin."));
		}
	}

	public TalusPaladin(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Whenever Talus Paladin or another Ally enters the battlefield under
		// your control, you may have Allies you control gain lifelink until end
		// of turn, and you may put a +1/+1 counter on Talus Paladin.
		this.addAbility(new AllyLifelink(state));
	}
}
