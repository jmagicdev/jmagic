package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Heroes' Podium")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ARTIFACT})
@ManaCost("5")
@ColorIdentity({})
public final class HeroesPodium extends Card
{
	public static final class HeroesPodiumAbility0 extends StaticAbility
	{
		public HeroesPodiumAbility0(GameState state)
		{
			super(state, "Each legendary creature you control gets +1/+1 for each other legendary creature you control.");

			SetGenerator yourLegends = Intersect.instance(CREATURES_YOU_CONTROL, HasSuperType.instance(SuperType.LEGENDARY));
			SetGenerator pumpAmount = Subtract.instance(Count.instance(yourLegends), numberGenerator(1));

			this.addEffectPart(modifyPowerAndToughness(yourLegends, pumpAmount, pumpAmount));
		}
	}

	public static final class HeroesPodiumAbility1 extends ActivatedAbility
	{
		public HeroesPodiumAbility1(GameState state)
		{
			super(state, "(X), (T): Look at the top X cards of your library. You may reveal a legendary creature card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.");
			this.setManaCost(new ManaPool("(X)"));
			this.costsTap = true;

			SetGenerator X = ValueOfX.instance(This.instance());
			SetGenerator legends = Intersect.instance(HasSuperType.instance(SuperType.LEGENDARY), HasType.instance(Type.CREATURE));
			this.addEffect(Sifter.start().look(X).take(1, legends).dumpToBottomRandomly().getEventFactory("Look at the top X cards of your library. You may reveal a legendary creature card from among them and put it into your hand. Put the rest on the bottom of your library in a random order."));
		}
	}

	public HeroesPodium(GameState state)
	{
		super(state);

		// Each legendary creature you control gets +1/+1 for each other
		// legendary creature you control.
		this.addAbility(new HeroesPodiumAbility0(state));

		// (X), (T): Look at the top X cards of your library. You may reveal a
		// legendary creature card from among them and put it into your hand.
		// Put the rest on the bottom of your library in a random order.
		this.addAbility(new HeroesPodiumAbility1(state));
	}
}
