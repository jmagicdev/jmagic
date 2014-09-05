package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("\u00C6ther Spellbomb")
@Types({Type.ARTIFACT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Mirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class AEtherSpellbomb extends Card
{
	public static final class ShockBomb extends ActivatedAbility
	{
		public ShockBomb(GameState state)
		{
			super(state, "(U), Sacrifice \u00C6ther Spellbomb: Return target creature to its owner's hand.");
			this.setManaCost(new ManaPool("(U)"));
			this.addCost(sacrificeThis("\u00C6ther Spellbomb"));

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			this.addEffect(bounce(targetedBy(target), "Return target creature to its owner's hand"));
		}
	}

	public static final class CardBomb extends ActivatedAbility
	{
		public CardBomb(GameState state)
		{
			super(state, "(1), Sacrifice \u00C6ther Spellbomb: Draw a card.");
			this.setManaCost(new ManaPool("(1)"));
			this.addCost(sacrificeThis("\u00C6ther Spellbomb"));

			this.addEffect(drawCards(You.instance(), 1, "Draw a card"));
		}
	}

	public AEtherSpellbomb(GameState state)
	{
		super(state);

		this.addAbility(new ShockBomb(state));
		this.addAbility(new CardBomb(state));
	}
}
