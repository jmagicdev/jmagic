package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Oran-Rief Recluse")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIDER})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class OranRiefRecluse extends Card
{
	public static final class ETBKill extends EventTriggeredAbility
	{
		private CostCollection kickerCost;

		public ETBKill(GameState state, CostCollection kickerCost)
		{
			super(state, "When Oran-Rief Recluse enters the battlefield, if it was kicked, destroy target creature with flying.");
			this.kickerCost = kickerCost;

			this.addPattern(whenThisEntersTheBattlefield());
			this.interveningIf = ThisPermanentWasKicked.instance(kickerCost);
			Target target = this.addTarget(Intersect.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class)), "target creature with flying");
			this.addEffect(destroy(targetedBy(target), "Destroy target creature with flying."));
		}

		@Override
		public ETBKill create(Game game)
		{
			return new ETBKill(game.physicalState, this.kickerCost);
		}
	}

	public OranRiefRecluse(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);
		org.rnd.jmagic.abilities.keywords.Kicker ability = new org.rnd.jmagic.abilities.keywords.Kicker(state, "(2)(G)");
		this.addAbility(ability);

		// Kicker (2)(G) (You may pay an additional (2)(G) as you cast this
		// spell.)
		CostCollection kickerCost = ability.costCollections[0];

		// Reach (This creature can block creatures with flying.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));

		// When Oran-Rief Recluse enters the battlefield, if it was kicked,
		// destroy target creature with flying.
		this.addAbility(new ETBKill(state, kickerCost));
	}
}
