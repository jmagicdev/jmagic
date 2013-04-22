package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sundial of the Infinite")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE)})
@ColorIdentity({})
public final class SundialoftheInfinite extends Card
{
	public static final class SundialoftheInfiniteAbility0 extends ActivatedAbility
	{
		public SundialoftheInfiniteAbility0(GameState state)
		{
			super(state, "(1), (T): End the turn. Activate this ability only during your turn.");
			this.setManaCost(new ManaPool("(1)"));
			this.costsTap = true;

			EventFactory endTheTurn = new EventFactory(EventType.END_THE_TURN, "End the turn.");
			endTheTurn.parameters.put(EventType.Parameter.CAUSE, This.instance());
			this.addEffect(endTheTurn);

			SetGenerator itsYourTurn = Intersect.instance(CurrentTurn.instance(), TurnOf.instance(You.instance()));
			this.addActivateRestriction(Not.instance(itsYourTurn));
		}
	}

	public SundialoftheInfinite(GameState state)
	{
		super(state);

		// (1), (T): End the turn. Activate this ability only during your turn.
		// (Exile all spells and abilities on the stack. Discard down to your
		// maximum hand size. Damage wears off, and "this turn" and
		// "until end of turn" effects end.)
		this.addAbility(new SundialoftheInfiniteAbility0(state));
	}
}
