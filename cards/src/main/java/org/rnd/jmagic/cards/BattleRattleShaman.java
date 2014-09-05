package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Battle-Rattle Shaman")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.SHAMAN})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class BattleRattleShaman extends Card
{
	public static final class Rattle extends EventTriggeredAbility
	{
		public Rattle(GameState state)
		{
			super(state, "At the beginning of combat on your turn, you may have target creature get +2/+0 until end of turn.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, StepOf.instance(You.instance(), Phase.PhaseType.COMBAT, Step.StepType.BEGINNING_OF_COMBAT));
			this.addPattern(pattern);

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			this.addEffect(youMay(ptChangeUntilEndOfTurn(targetedBy(target), +2, +0, "Target creature gets +2/+0 until end of turn."), "You may have target creature get +2/+0 until end of turn."));
		}
	}

	public BattleRattleShaman(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// At the beginning of combat on your turn, you may have target creature
		// get +2/+0 until end of turn.
		this.addAbility(new Rattle(state));
	}
}
