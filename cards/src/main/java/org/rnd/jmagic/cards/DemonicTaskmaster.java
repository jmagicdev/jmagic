package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Demonic Taskmaster")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class DemonicTaskmaster extends Card
{
	public static final class DemonicTaskmasterAbility1 extends EventTriggeredAbility
	{
		public DemonicTaskmasterAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, sacrifice a creature other than Demonic Taskmaster.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			this.addEffect(sacrifice(You.instance(), 1, RelativeComplement.instance(CREATURES_YOU_CONTROL, ABILITY_SOURCE_OF_THIS), "Sacrifice a creature other than Demonic Taskmaster."));
		}
	}

	public DemonicTaskmaster(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// At the beginning of your upkeep, sacrifice a creature other than
		// Demonic Taskmaster.
		this.addAbility(new DemonicTaskmasterAbility1(state));
	}
}
