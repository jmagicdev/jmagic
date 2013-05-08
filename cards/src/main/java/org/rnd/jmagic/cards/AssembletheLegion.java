package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Assemble the Legion")
@Types({Type.ENCHANTMENT})
@ManaCost("3RW")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class AssembletheLegion extends Card
{
	public static final class AssembletheLegionAbility0 extends EventTriggeredAbility
	{
		public AssembletheLegionAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, put a muster counter on Assemble the Legion. Then put a 1/1 red and white Soldier creature token with haste onto the battlefield for each muster counter on Assemble the Legion.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			this.addEffect(putCounters(1, Counter.CounterType.MUSTER, ABILITY_SOURCE_OF_THIS, "Put a muster counter on Assemble the Legion."));

			CreateTokensFactory factory = new CreateTokensFactory(Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.MUSTER)), numberGenerator(1), numberGenerator(1), "Then put a 1/1 red and white Soldier creature token with haste onto the battlefield for each muster counter on Assemble the Legion.");
			factory.setColors(Color.RED, Color.WHITE);
			factory.setSubTypes(SubType.SOLDIER);
			factory.addAbility(org.rnd.jmagic.abilities.keywords.Haste.class);
			this.addEffect(factory.getEventFactory());
		}
	}

	public AssembletheLegion(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, put a muster counter on Assemble the
		// Legion. Then put a 1/1 red and white Soldier creature token with
		// haste onto the battlefield for each muster counter on Assemble the
		// Legion.
		this.addAbility(new AssembletheLegionAbility0(state));
	}
}
