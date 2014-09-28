package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Mardu Hateblade")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("W")
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class MarduHateblade extends Card
{
	public static final class MarduHatebladeAbility0 extends ActivatedAbility
	{
		public MarduHatebladeAbility0(GameState state)
		{
			super(state, "(B): Mardu Hateblade gains deathtouch until end of turn.");
			this.setManaCost(new ManaPool("(B)"));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Deathtouch.class, "Mardu Hateblade gains deathtouch until end of turn."));
		}
	}

	public MarduHateblade(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (B): Mardu Hateblade gains deathtouch until end of turn. (Any amount
		// of damage it deals to a creature is enough to destroy it.)
		this.addAbility(new MarduHatebladeAbility0(state));
	}
}
