package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Goblin Ruinblaster")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.GOBLIN})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class GoblinRuinblaster extends Card
{
	public static final class Blast extends EventTriggeredAbility
	{
		private final CostCollection kickerCost;

		public Blast(GameState state, CostCollection kickerCost)
		{
			super(state, "When Goblin Ruinblaster enters the battlefield, if it was kicked, destroy target nonbasic land.");
			this.kickerCost = kickerCost;
			this.addPattern(whenThisEntersTheBattlefield());
			this.interveningIf = ThisPermanentWasKicked.instance(kickerCost);

			Target target = this.addTarget(RelativeComplement.instance(LandPermanents.instance(), HasSuperType.instance(SuperType.BASIC)), "target nonbasic land");

			this.addEffect(destroy(targetedBy(target), "Destroy target nonbasic land."));
		}

		@Override
		public Blast create(Game game)
		{
			return new Blast(game.physicalState, this.kickerCost);
		}
	}

	public GoblinRuinblaster(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);
		org.rnd.jmagic.abilities.keywords.Kicker ability = new org.rnd.jmagic.abilities.keywords.Kicker(state, "(R)");
		this.addAbility(ability);

		// Kicker (R) (You may pay an additional (R) as you cast this spell.)
		CostCollection kickerCost = ability.costCollections[0];

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// When Goblin Ruinblaster enters the battlefield, if it was kicked,
		// destroy target nonbasic land.
		this.addAbility(new Blast(state, kickerCost));
	}
}
