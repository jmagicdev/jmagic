package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vedalken Infuser")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.VEDALKEN})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class VedalkenInfuser extends Card
{
	public static final class VedalkenInfuserAbility0 extends EventTriggeredAbility
	{
		public VedalkenInfuserAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, you may put a charge counter on target artifact.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			SetGenerator target = targetedBy(this.addTarget(ArtifactPermanents.instance(), "target artifact"));
			this.addEffect(youMay(putCounters(1, Counter.CounterType.CHARGE, target, "Put a charge counter on target artifact."), "You may put a charge counter on target artifact."));
		}
	}

	public VedalkenInfuser(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);

		// At the beginning of your upkeep, you may put a charge counter on
		// target artifact.
		this.addAbility(new VedalkenInfuserAbility0(state));
	}
}
