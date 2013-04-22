package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.Convenience;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Muddle the Mixture")
@Types({Type.INSTANT})
@ManaCost("UU")
@Printings({@Printings.Printed(ex = Expansion.RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class MuddletheMixture extends Card
{
	public MuddletheMixture(GameState state)
	{
		super(state);

		// Counter target instant or sorcery spell.
		SetGenerator instantsAndSorceries = HasType.instance(Type.INSTANT, Type.SORCERY);
		Target target = this.addTarget(Intersect.instance(Spells.instance(), instantsAndSorceries), "target instant or sorcery spell");

		this.addEffect(Convenience.counter(targetedBy(target), "Counter target instant or sorcery spell."));

		// Transmute {1}{U}{U} ({1}{U}{U}, Discard this card: Search your
		// library for a card with the same converted mana cost as this card,
		// reveal it, and put it into your hand. Then shuffle your library.
		// Transmute only as a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Transmute(state, "(1)(U)(U)"));
	}
}
