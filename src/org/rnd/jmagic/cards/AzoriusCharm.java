package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Azorius Charm")
@Types({Type.INSTANT})
@ManaCost("WU")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class AzoriusCharm extends Card
{
	public AzoriusCharm(GameState state)
	{
		super(state);

		// Choose one \u2014

		// Creatures you control gain lifelink until end of turn;
		this.addEffect(1, addAbilityUntilEndOfTurn(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Lifelink.class, "Creatures you control gain lifelink until end of turn"));

		// or draw a card;
		this.addEffect(2, drawCards(You.instance(), 1, "draw a card"));

		// or put target attacking or blocking creature on top of its owner's
		// library.
		SetGenerator target = targetedBy(this.addTarget(3, Union.instance(Attacking.instance(), Blocking.instance()), "target attacking or blocking creature"));
		this.addEffect(3, putOnTopOfLibrary(target, "put target attacking or blocking creature on top of its owner's library"));
	}
}
