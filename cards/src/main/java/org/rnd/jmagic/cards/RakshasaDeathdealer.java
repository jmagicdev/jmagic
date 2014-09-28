package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Rakshasa Deathdealer")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON, SubType.CAT})
@ManaCost("BG")
@ColorIdentity({Color.BLACK, Color.GREEN})
public final class RakshasaDeathdealer extends Card
{
	public static final class RakshasaDeathdealerAbility0 extends ActivatedAbility
	{
		public RakshasaDeathdealerAbility0(GameState state)
		{
			super(state, "(B)(G): Rakshasa Deathdealer gets +2/+2 until end of turn.");
			this.setManaCost(new ManaPool("(B)(G)"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +2, +2, "Rakshasa Deathdealer gets +2/+2 until end of turn."));
		}
	}

	public RakshasaDeathdealer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (B)(G): Rakshasa Deathdealer gets +2/+2 until end of turn.
		this.addAbility(new RakshasaDeathdealerAbility0(state));

		// (B)(G): Regenerate Rakshasa Deathdealer.
		this.addAbility(new org.rnd.jmagic.abilities.Regenerate.Final(state, "(B)(G)", this.getName()));
	}
}
