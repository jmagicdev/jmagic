package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Whispering Madness")
@Types({Type.SORCERY})
@ManaCost("2UB")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class WhisperingMadness extends Card
{
	public WhisperingMadness(GameState state)
	{
		super(state);

		// Each player discards his or her hand,
		EventFactory discard = discardHand(Players.instance(), "Each player discards his or her hand,");
		this.addEffect(discard);

		// then draws cards equal to the greatest number of cards a player
		// discarded this way.
		SetGenerator discarded = OldObjectOf.instance(EffectResult.instance(discard));
		SetGenerator number = Count.instance(LargestSet.instance(SplitOnOwner.instance(discarded)));
		this.addEffect(drawCards(Players.instance(), number, "then draws cards equal to the greatest number of cards a player discarded this way."));

		// Cipher (Then you may exile this spell card encoded on a creature you
		// control. Whenever that creature deals combat damage to a player, its
		// controller may cast a copy of the encoded card without paying its
		// mana cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cipher(state));
	}
}
