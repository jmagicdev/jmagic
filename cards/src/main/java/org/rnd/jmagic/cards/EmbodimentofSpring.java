package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Embodiment of Spring")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("U")
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class EmbodimentofSpring extends Card
{
	public static final class EmbodimentofSpringAbility0 extends ActivatedAbility
	{
		public EmbodimentofSpringAbility0(GameState state)
		{
			super(state, "(1)(G), (T), Sacrifice Embodiment of Spring: Search your library for a basic land card, put it onto the battlefield tapped, then shuffle your library.");
			this.setManaCost(new ManaPool("(1)(G)"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Embodiment of Spring"));
			this.addEffect(searchYourLibraryForABasicLandCardAndPutItOntoTheBattlefield(true));
		}
	}

	public EmbodimentofSpring(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(3);

		// (1)(G), (T), Sacrifice Embodiment of Spring: Search your library for
		// a basic land card, put it onto the battlefield tapped, then shuffle
		// your library.
		this.addAbility(new EmbodimentofSpringAbility0(state));
	}
}
