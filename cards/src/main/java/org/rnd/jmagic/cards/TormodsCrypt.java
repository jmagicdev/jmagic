package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tormod's Crypt")
@Types({Type.ARTIFACT})
@ManaCost("0")
@ColorIdentity({})
public final class TormodsCrypt extends Card
{
	public static final class GraveyardWipe extends ActivatedAbility
	{
		public GraveyardWipe(GameState state)
		{
			super(state, "(T), Sacrifice Tormod's Crypt: Exile all cards from target player's graveyard.");
			this.costsTap = true;
			this.addCost(sacrificeThis("Tormod's Crypt"));

			Target target = this.addTarget(Players.instance(), "target player");
			this.addEffect(exile(InZone.instance(GraveyardOf.instance(targetedBy(target))), "Exile all cards from target player's graveyard."));
		}
	}

	public TormodsCrypt(GameState state)
	{
		super(state);

		// (T), Sacrifice Tormod's Crypt: Exile all cards from target player's
		// graveyard.
		this.addAbility(new GraveyardWipe(state));
	}
}
