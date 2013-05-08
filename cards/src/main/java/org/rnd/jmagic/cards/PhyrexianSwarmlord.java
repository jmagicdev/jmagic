package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Phyrexian Swarmlord")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR, SubType.INSECT})
@ManaCost("4GG")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class PhyrexianSwarmlord extends Card
{
	public static final class PhyrexianSwarmlordAbility1 extends EventTriggeredAbility
	{
		public PhyrexianSwarmlordAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, put a 1/1 green Insect creature token with infect onto the battlefield for each poison counter your opponents have.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			CreateTokensFactory factory = new CreateTokensFactory(Count.instance(CountersOn.instance(OpponentsOf.instance(You.instance()), Counter.CounterType.POISON)), "Put a 1/1 green Insect creature token with infect onto the battlefield for each poison counter your opponents have.");
			factory.addCreature(1, 1);
			factory.setColors(Color.GREEN);
			factory.setSubTypes(SubType.INSECT);
			factory.addAbility(org.rnd.jmagic.abilities.keywords.Infect.class);
			this.addEffect(factory.getEventFactory());
		}
	}

	public PhyrexianSwarmlord(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));

		// At the beginning of your upkeep, put a 1/1 green Insect creature
		// token with infect onto the battlefield for each poison counter your
		// opponents have.
		this.addAbility(new PhyrexianSwarmlordAbility1(state));
	}
}
