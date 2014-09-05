package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Bojuka Bog")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = Worldwake.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class BojukaBog extends Card
{
	public static final class UncounterableTormodsCrypt extends EventTriggeredAbility
	{
		public UncounterableTormodsCrypt(GameState state)
		{
			super(state, "When Bojuka Bog enters the battlefield, exile all cards from target player's graveyard.");
			this.addPattern(whenThisEntersTheBattlefield());
			Target target = this.addTarget(Players.instance(), "target player");
			this.addEffect(exile(InZone.instance(GraveyardOf.instance(targetedBy(target))), "Exile all cards from target player's graveyard."));
		}
	}

	public BojukaBog(GameState state)
	{
		super(state);

		// Bojuka Bog enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Bojuka Bog enters the battlefield, exile all cards from target
		// player's graveyard.
		this.addAbility(new UncounterableTormodsCrypt(state));

		// (T): Add (B) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForB(state));
	}
}
