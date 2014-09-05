package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Golden Urn")
@Types({Type.ARTIFACT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class GoldenUrn extends Card
{
	public static final class GoldenUrnAbility0 extends EventTriggeredAbility
	{
		public GoldenUrnAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, you may put a charge counter on Golden Urn.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			this.addEffect(youMay(putCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Put a charge counter on Golden Urn."), "You may put a charge counter on Golden Urn."));
		}
	}

	public static final class GoldenUrnAbility1 extends ActivatedAbility
	{
		public GoldenUrnAbility1(GameState state)
		{
			super(state, "(T), Sacrifice Golden Urn: You gain life equal to the number of charge counters on Golden Urn.");
			this.costsTap = true;
			this.addCost(sacrificeThis("Golden Urn"));
			this.addEffect(gainLife(You.instance(), Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.CHARGE)), "You gain life equal to the number of charge counters on Golden Urn."));
		}
	}

	public GoldenUrn(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, you may put a charge counter on
		// Golden Urn.
		this.addAbility(new GoldenUrnAbility0(state));

		// (T), Sacrifice Golden Urn: You gain life equal to the number of
		// charge counters on Golden Urn.
		this.addAbility(new GoldenUrnAbility1(state));
	}
}
