package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sunbeam Spellbomb")
@Types({Type.ARTIFACT})
@ManaCost("1")
@ColorIdentity({Color.WHITE})
public final class SunbeamSpellbomb extends Card
{
	public static final class SunbeamSpellbombAbility0 extends ActivatedAbility
	{
		public SunbeamSpellbombAbility0(GameState state)
		{
			super(state, "(W), Sacrifice Sunbeam Spellbomb: You gain 5 life.");
			this.setManaCost(new ManaPool("W"));
			this.addCost(sacrificeThis("Sunbeam Spellbomb"));
			this.addEffect(gainLife(You.instance(), 5, "You gain 5 life."));
		}
	}

	public static final class SunbeamSpellbombAbility1 extends ActivatedAbility
	{
		public SunbeamSpellbombAbility1(GameState state)
		{
			super(state, "(1), Sacrifice Sunbeam Spellbomb: Draw a card.");
			this.setManaCost(new ManaPool("1"));
			this.addCost(sacrificeThis("Sunbeam Spellbomb"));
			this.addEffect(drawACard());
		}
	}

	public SunbeamSpellbomb(GameState state)
	{
		super(state);

		// {W}, Sacrifice Sunbeam Spellbomb: You gain 5 life.
		this.addAbility(new SunbeamSpellbombAbility0(state));

		// {1}, Sacrifice Sunbeam Spellbomb: Draw a card.
		this.addAbility(new SunbeamSpellbombAbility1(state));
	}
}
