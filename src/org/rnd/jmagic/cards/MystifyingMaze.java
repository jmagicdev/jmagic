package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mystifying Maze")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.RARE)})
@ColorIdentity({})
public final class MystifyingMaze extends Card
{
	public static final class MystifyingMazeAbility1 extends ActivatedAbility
	{
		public MystifyingMazeAbility1(GameState state)
		{
			super(state, "(4), (T): Exile target attacking creature an opponent controls. At the beginning of the next end step, return it to the battlefield tapped under its owner's control.");
			this.setManaCost(new ManaPool("(4)"));
			this.costsTap = true;
			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(Attacking.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance()))), "target attacking creature an opponent controls"));

			EventType.ParameterMap rfgParameters = new EventType.ParameterMap();
			rfgParameters.put(EventType.Parameter.CAUSE, This.instance());
			rfgParameters.put(EventType.Parameter.TAPPED, NonEmpty.instance());
			rfgParameters.put(EventType.Parameter.TARGET, target);
			this.addEffect(new EventFactory(SLIDE, rfgParameters, "Exile target creature. Return that card to the battlefield under its owner's control at the beginning of the next end step."));
		}
	}

	public MystifyingMaze(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (4), (T): Exile target attacking creature an opponent controls. At
		// the beginning of the next end step, return it to the battlefield
		// tapped under its owner's control.
		this.addAbility(new MystifyingMazeAbility1(state));
	}
}
