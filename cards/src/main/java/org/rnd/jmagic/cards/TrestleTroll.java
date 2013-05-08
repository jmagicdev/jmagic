package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Trestle Troll")
@Types({Type.CREATURE})
@SubTypes({SubType.TROLL})
@ManaCost("1BG")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class TrestleTroll extends Card
{
	public static final class TrestleTrollAbility2 extends ActivatedAbility
	{
		public TrestleTrollAbility2(GameState state)
		{
			super(state, "(1)(B)(G): Regenerate Trestle Troll.");
			this.setManaCost(new ManaPool("(1)(B)(G)"));
		}
	}

	public TrestleTroll(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);

		// Defender
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// Reach (This creature can block creatures with flying.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));

		// (1)(B)(G): Regenerate Trestle Troll.
		this.addAbility(new org.rnd.jmagic.abilities.Regenerate.Final(state, "(1)(B)(G)", "Trestle Troll"));
	}
}
