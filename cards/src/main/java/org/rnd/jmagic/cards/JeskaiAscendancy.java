package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Jeskai Ascendancy")
@Types({Type.ENCHANTMENT})
@ManaCost("URW")
@ColorIdentity({Color.RED, Color.WHITE, Color.BLUE})
public final class JeskaiAscendancy extends Card
{
	public static final class JeskaiAscendancyAbility0 extends EventTriggeredAbility
	{
		public JeskaiAscendancyAbility0(GameState state)
		{
			super(state, "Whenever you cast a noncreature spell, creatures you control get +1/+1 until end of turn. Untap those creatures.");
			this.addPattern(whenYouCastANoncreatureSpell());
			this.addEffect(ptChangeUntilEndOfTurn(CREATURES_YOU_CONTROL, +1, +1, "Creatures you control get +1/+1 until end of turn."));
			this.addEffect(untap(CREATURES_YOU_CONTROL, "Untap those creatures."));
		}
	}

	public static final class JeskaiAscendancyAbility1 extends EventTriggeredAbility
	{
		public JeskaiAscendancyAbility1(GameState state)
		{
			super(state, "Whenever you cast a noncreature spell, you may draw a card. If you do, discard a card.");
			this.addPattern(whenYouCastANoncreatureSpell());
			this.addEffect(youMayDrawACardIfYouDoDiscardACard());
		}
	}

	public JeskaiAscendancy(GameState state)
	{
		super(state);

		// Whenever you cast a noncreature spell, creatures you control get
		// +1/+1 until end of turn. Untap those creatures.
		this.addAbility(new JeskaiAscendancyAbility0(state));

		// Whenever you cast a noncreature spell, you may draw a card. If you
		// do, discard a card.
		this.addAbility(new JeskaiAscendancyAbility1(state));
	}
}
