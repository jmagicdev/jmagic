package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Channel the Suns")
@Types({Type.SORCERY})
@ManaCost("3G")
@ColorIdentity({Color.WHITE, Color.BLUE, Color.BLACK, Color.RED, Color.GREEN})
public final class ChanneltheSuns extends Card
{
	public ChanneltheSuns(GameState state)
	{
		super(state);
		this.addEffect(addManaToYourManaPoolFromSpell("(W)(U)(B)(R)(G)"));
	}
}
