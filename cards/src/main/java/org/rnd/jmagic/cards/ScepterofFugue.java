package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scepter of Fugue")
@Types({Type.ARTIFACT})
@ManaCost("BB")
@Printings({@Printings.Printed(ex = Expansion.CONFLUX, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class ScepterofFugue extends Card
{
	public static final class Discard extends ActivatedAbility
	{
		public Discard(GameState state)
		{
			super(state, "(1)(B), (T): Target player discards a card. Activate this ability only during your turn.");
			this.setManaCost(new ManaPool("(1)(B)"));
			this.costsTap = true;

			Target target = this.addTarget(Players.instance(), "target player");
			this.addEffect(discardCards(targetedBy(target), 1, "Target player discards a card."));

			SetGenerator itsYourTurn = Intersect.instance(CurrentTurn.instance(), TurnOf.instance(You.instance()));
			this.addActivateRestriction(Not.instance(itsYourTurn));
		}
	}

	public ScepterofFugue(GameState state)
	{
		super(state);

		// (1)(B), (T): Target player discards a card. Activate this ability
		// only during your turn.
		this.addAbility(new Discard(state));
	}
}
