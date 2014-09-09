package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Heretic's Punishment")
@Types({Type.ENCHANTMENT})
@ManaCost("4R")
@ColorIdentity({Color.RED})
public final class HereticsPunishment extends Card
{
	public static final class HereticsPunishmentAbility0 extends ActivatedAbility
	{
		public HereticsPunishmentAbility0(GameState state)
		{
			super(state, "(3)(R): Choose target creature or player, then put the top three cards of your library into your graveyard.  Heretic's Punishment deals damage to that creature or player equal to the highest converted mana cost among those cards.");
			this.setManaCost(new ManaPool("(3)(R)"));

			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			EventFactory mill = millCards(You.instance(), 3, "Put the top three cards of your library into your graveyard.");
			this.addEffect(mill);

			this.addEffect(permanentDealDamage(Maximum.instance(ConvertedManaCostOf.instance(NewObjectOf.instance(EffectResult.instance(mill)))), target, "Heretic's Punishment deals damage to that creature or player equal to the highest converted mana cost among those cards."));
		}
	}

	public HereticsPunishment(GameState state)
	{
		super(state);

		// (3)(R): Choose target creature or player, then put the top three
		// cards of your library into your graveyard. Heretic's Punishment deals
		// damage to that creature or player equal to the highest converted mana
		// cost among those cards.
		this.addAbility(new HereticsPunishmentAbility0(state));
	}
}
