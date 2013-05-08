package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Jenara, Asura of War")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("GWU")
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.GREEN})
public final class JenaraAsuraofWar extends Card
{
	public static final class Grow extends ActivatedAbility
	{
		public Grow(GameState state)
		{
			super(state, "(1)(W): Put a +1/+1 counter on Jenara, Asura of War.");

			this.setManaCost(new ManaPool("1W"));

			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a +1/+1 counter on Jenara, Asura of War."));
		}
	}

	public JenaraAsuraofWar(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new Grow(state));
	}
}
