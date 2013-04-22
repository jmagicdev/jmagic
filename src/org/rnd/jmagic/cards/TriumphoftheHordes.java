package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Triumph of the Hordes")
@Types({Type.SORCERY})
@ManaCost("2GG")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class TriumphoftheHordes extends Card
{
	public TriumphoftheHordes(GameState state)
	{
		super(state);

		// Until end of turn, creatures you control get +1/+1 and gain trample
		// and infect. (Creatures with infect deal damage to creatures in the
		// form of -1/-1 counters and to players in the form of poison
		// counters.)
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(CREATURES_YOU_CONTROL, +1, +1, "Until end of turn, creatures you control get +1/+1 and gain trample and infect.", org.rnd.jmagic.abilities.keywords.Trample.class, org.rnd.jmagic.abilities.keywords.Infect.class));
	}
}
