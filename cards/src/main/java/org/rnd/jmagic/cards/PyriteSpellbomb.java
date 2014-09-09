package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pyrite Spellbomb")
@Types({Type.ARTIFACT})
@ManaCost("1")
@ColorIdentity({Color.RED})
public final class PyriteSpellbomb extends Card
{
	public static final class ShockBomb extends ActivatedAbility
	{
		public ShockBomb(GameState state)
		{
			super(state, "(R), Sacrifice Pyrite Spellbomb: Pyrite Spellbomb deals 2 damage to target creature or player.");
			this.setManaCost(new ManaPool("(R)"));
			this.addCost(sacrificeThis("Pyrite Spellbomb"));

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
			this.addEffect(permanentDealDamage(2, targetedBy(target), "Pyrite Spellbomb deals 2 damage to target creature or player"));
		}
	}

	public static final class CardBomb extends ActivatedAbility
	{
		public CardBomb(GameState state)
		{
			super(state, "(1), Sacrifice Pyrite Spellbomb: Draw a card.");
			this.setManaCost(new ManaPool("(1)"));
			this.addCost(sacrificeThis("Pyrite Spellbomb"));

			this.addEffect(drawCards(You.instance(), 1, "Draw a card"));
		}
	}

	public PyriteSpellbomb(GameState state)
	{
		super(state);

		// (R), Sacrifice Pyrite Spellbomb: Pyrite Spellbomb deals 2 damage to
		// target creature or player.
		this.addAbility(new ShockBomb(state));

		// (1), Sacrifice Pyrite Spellbomb: Draw a card.
		this.addAbility(new CardBomb(state));
	}
}
