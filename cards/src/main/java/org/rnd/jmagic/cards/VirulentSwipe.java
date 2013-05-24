package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Virulent Swipe")
@Types({Type.INSTANT})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class VirulentSwipe extends Card
{
	public VirulentSwipe(GameState state)
	{
		super(state);

		// Target creature gets +2/+0 and gains deathtouch until end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, +2, +0, "Target creature gets +2/+0 and gains deathtouch until end of turn.", org.rnd.jmagic.abilities.keywords.Deathtouch.class));

		// Rebound (If you cast this spell from your hand, exile it as it
		// resolves. At the beginning of your next upkeep, you may cast this
		// card from exile without paying its mana cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Rebound(state));
	}
}
