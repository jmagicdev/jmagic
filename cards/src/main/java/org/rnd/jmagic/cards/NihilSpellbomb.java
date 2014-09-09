package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nihil Spellbomb")
@Types({Type.ARTIFACT})
@ManaCost("1")
@ColorIdentity({Color.BLACK})
public final class NihilSpellbomb extends Card
{
	public static final class NihilSpellbombAbility0 extends ActivatedAbility
	{
		public NihilSpellbombAbility0(GameState state)
		{
			super(state, "(T), Sacrifice Nihil Spellbomb: Exile all cards from target player's graveyard.");
			this.costsTap = true;
			this.addCost(sacrificeThis("Nihil Spellbomb"));
			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(exile(InZone.instance(GraveyardOf.instance(target)), "Exile all cards from target player's graveyard."));
		}
	}

	public NihilSpellbomb(GameState state)
	{
		super(state);

		// (T), Sacrifice Nihil Spellbomb: Exile all cards from target player's
		// graveyard.
		this.addAbility(new NihilSpellbombAbility0(state));

		// When Nihil Spellbomb is put into a graveyard from the battlefield,
		// you may pay (B). If you do, draw a card.
		this.addAbility(new org.rnd.jmagic.abilities.ScarsSpellbomb(state, this.getName(), "(B)"));
	}
}
