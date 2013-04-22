package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Devil's Play")
@Types({Type.SORCERY})
@ManaCost("XR")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class DevilsPlay extends Card
{
	public DevilsPlay(GameState state)
	{
		super(state);

		// Devil's Play deals X damage to target creature or player.
		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
		this.addEffect(spellDealDamage(ValueOfX.instance(This.instance()), target, "Devil's Play deals X damage to target creature or player."));

		// Flashback (X)(R)(R)(R) (You may cast this card from your graveyard
		// for its flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(X)(R)(R)(R)"));
	}
}
