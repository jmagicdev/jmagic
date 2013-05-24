package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Composite Golem")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.GOLEM})
@ManaCost("6")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.FIFTH_DAWN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.GREEN, Color.BLACK, Color.RED})
public final class CompositeGolem extends Card
{
	public static final class SacForRainbow extends ActivatedAbility
	{
		public SacForRainbow(GameState state)
		{
			super(state, "Sacrifice Composite Golem: Add (W)(U)(B)(R)(G) to your mana pool.");
			this.addCost(sacrificeThis("Composite Golem"));
			this.addEffect(addManaToYourManaPoolFromAbility("(W)(U)(B)(R)(G)"));
		}
	}

	public CompositeGolem(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		this.addAbility(new SacForRainbow(state));
	}
}
