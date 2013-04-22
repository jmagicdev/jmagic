package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kor Sanctifiers")
@Types({Type.CREATURE})
@SubTypes({SubType.KOR, SubType.CLERIC})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class KorSanctifiers extends Card
{
	public static final class ETBKill extends EventTriggeredAbility
	{
		private CostCollection kickerCost;

		public ETBKill(GameState state, CostCollection kickerCost)
		{
			super(state, "When Kor Sanctifiers enters the battlefield, if it was kicked, destroy target artifact or enchantment.");
			this.kickerCost = kickerCost;

			this.addPattern(whenThisEntersTheBattlefield());
			this.interveningIf = ThisPermanentWasKicked.instance(kickerCost);
			Target target = this.addTarget(Union.instance(ArtifactPermanents.instance(), EnchantmentPermanents.instance()), "target artifact or enchantment");
			this.addEffect(destroy(targetedBy(target), "Destroy target artifact or enchantment."));
		}

		@Override
		public ETBKill create(Game game)
		{
			return new ETBKill(game.physicalState, this.kickerCost);
		}
	}

	public KorSanctifiers(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);
		org.rnd.jmagic.abilities.keywords.Kicker ability = new org.rnd.jmagic.abilities.keywords.Kicker(state, "(W)");
		this.addAbility(ability);

		// Kicker (W) (You may pay an additional (W) as you cast this spell.)
		CostCollection kickerCost = ability.costCollections[0];

		// When Kor Sanctifiers enters the battlefield, if it was kicked,
		// destroy target artifact or enchantment.
		this.addAbility(new ETBKill(state, kickerCost));
	}
}
