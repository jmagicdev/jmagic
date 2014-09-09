package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Chromatic Star")
@Types({Type.ARTIFACT})
@ManaCost("1")
@ColorIdentity({})
public final class ChromaticStar extends Card
{
	public static final class OneTapSacMana extends org.rnd.jmagic.abilities.TapForMana
	{
		public OneTapSacMana(GameState state)
		{
			super(state, "(WUBRG)");
			this.setManaCost(new ManaPool("1"));

			this.addCost(sacrificeThis("Chromatic Star"));

			this.setName("(1), (T), Sacrifice Chromatic Star: Add one mana of any color to your mana pool.");
		}
	}

	public static final class DrawTrigger extends EventTriggeredAbility
	{
		public DrawTrigger(GameState state)
		{
			super(state, "When Chromatic Star is put into a graveyard from the battlefield, draw a card.");
			this.addPattern(whenThisIsPutIntoAGraveyardFromTheBattlefield());
			this.addEffect(drawACard());
		}
	}

	public ChromaticStar(GameState state)
	{
		super(state);

		this.addAbility(new OneTapSacMana(state));
		this.addAbility(new DrawTrigger(state));
	}
}
