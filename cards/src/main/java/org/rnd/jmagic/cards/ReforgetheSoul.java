package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Reforge the Soul")
@Types({Type.SORCERY})
@ManaCost("3RR")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class ReforgetheSoul extends Card
{
	public ReforgetheSoul(GameState state)
	{
		super(state);

		// Each player discards his or her hand and draws seven cards.
		this.addEffect(discardHand(Players.instance(), "Each player discards his or her hand,"));

		this.addEffect(drawCards(Players.instance(), 7, "then draws seven cards"));

		// Miracle (1)(R) (You may cast this card for its miracle cost when you
		// draw it if it's the first card you drew this turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Miracle(state, "(1)(R)"));
	}
}
