package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Izzet Keyrune")
@Types({Type.ARTIFACT})
@ManaCost("3")
@ColorIdentity({Color.BLUE, Color.RED})
public final class IzzetKeyrune extends Card
{
	public static final class IzzetKeyruneAbility1 extends ActivatedAbility
	{
		public IzzetKeyruneAbility1(GameState state)
		{
			super(state, "(U)(R): Until end of turn, Izzet Keyrune becomes a 2/1 blue and red Elemental artifact creature.");
			this.setManaCost(new ManaPool("(U)(R)"));

			Animator animate = new Animator(ABILITY_SOURCE_OF_THIS, 2, 1);
			animate.addColor(Color.BLUE);
			animate.addColor(Color.RED);
			animate.addSubType(SubType.ELEMENTAL);
			animate.addType(Type.ARTIFACT);
			this.addEffect(createFloatingEffect("Until end of turn, Izzet Keyrune becomes a 2/1 blue and red Elemental artifact creature.", animate.getParts()));
		}
	}

	public static final class IzzetKeyruneAbility2 extends EventTriggeredAbility
	{
		public IzzetKeyruneAbility2(GameState state)
		{
			super(state, "Whenever Izzet Keyrune deals combat damage to a player, you may draw a card. If you do, discard a card.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());
			this.addEffect(youMayDrawACardIfYouDoDiscardACard());
		}
	}

	public IzzetKeyrune(GameState state)
	{
		super(state);

		// (T): Add (U) or (R) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(UR)"));

		// (U)(R): Until end of turn, Izzet Keyrune becomes a 2/1 blue and red
		// Elemental artifact creature.
		this.addAbility(new IzzetKeyruneAbility1(state));

		// Whenever Izzet Keyrune deals combat damage to a player, you may draw
		// a card. If you do, discard a card.
		this.addAbility(new IzzetKeyruneAbility2(state));
	}
}
