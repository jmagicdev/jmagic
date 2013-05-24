package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Goblin Piledriver")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.GOBLIN})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class GoblinPiledriver extends Card
{
	public static final class GoblinPiledriverAbility1 extends EventTriggeredAbility
	{
		public GoblinPiledriverAbility1(GameState state)
		{
			super(state, "Whenever Goblin Piledriver attacks, it gets +2/+0 until end of turn for each other attacking Goblin.");
			this.addPattern(whenThisAttacks());
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, Multiply.instance(numberGenerator(2), Count.instance(RelativeComplement.instance(Intersect.instance(Attacking.instance(), HasSubType.instance(SubType.GOBLIN)), ABILITY_SOURCE_OF_THIS))), numberGenerator(0), "It gets +2/+0 until end of turn for each other attacking Goblin."));
		}
	}

	public GoblinPiledriver(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Protection from blue
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.FromBlue(state));

		// Whenever Goblin Piledriver attacks, it gets +2/+0 until end of turn
		// for each other attacking Goblin.
		this.addAbility(new GoblinPiledriverAbility1(state));
	}
}
