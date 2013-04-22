package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Reaper from the Abyss")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("3BBB")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLACK})
public final class ReaperfromtheAbyss extends Card
{
	public static final class ReaperfromtheAbyssAbility1 extends EventTriggeredAbility
	{
		public ReaperfromtheAbyssAbility1(GameState state)
		{
			super(state, "At the beginning of each end step, if a creature died this turn, destroy target non-Demon creature.");
			this.addPattern(atTheBeginningOfEachEndStep());
			this.interveningIf = Morbid.instance();
			state.ensureTracker(new Morbid.Tracker());
			SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(CreaturePermanents.instance(), HasSubType.instance(SubType.DEMON)), "target non-Demon creature"));
			this.addEffect(destroy(target, "Destroy target non-Demon creature."));
		}
	}

	public ReaperfromtheAbyss(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Morbid \u2014 At the beginning of each end step, if a creature died
		// this turn, destroy target non-Demon creature.
		this.addAbility(new ReaperfromtheAbyssAbility1(state));
	}
}
