package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Channel the Suns")
@Types({Type.SORCERY})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = FifthDawn.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.BLACK, Color.GREEN, Color.RED})
public final class ChanneltheSuns extends Card
{
	public ChanneltheSuns(GameState state)
	{
		super(state);
		this.addEffect(addManaToYourManaPoolFromSpell("(W)(U)(B)(R)(G)"));
	}
}
