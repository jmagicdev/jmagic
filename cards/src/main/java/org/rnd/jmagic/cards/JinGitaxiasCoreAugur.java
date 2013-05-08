package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Jin-Gitaxias, Core Augur")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.PRAETOR})
@ManaCost("8UU")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE})
public final class JinGitaxiasCoreAugur extends Card
{
	public static final class JinGitaxiasCoreAugurAbility1 extends EventTriggeredAbility
	{
		public JinGitaxiasCoreAugurAbility1(GameState state)
		{
			super(state, "At the beginning of your end step, draw seven cards.");
			this.addPattern(atTheBeginningOfYourEndStep());
			this.addEffect(drawCards(You.instance(), 7, "Draw seven cards."));
		}
	}

	public static final class JinGitaxiasCoreAugurAbility2 extends StaticAbility
	{
		public JinGitaxiasCoreAugurAbility2(GameState state)
		{
			super(state, "Each opponent's maximum hand size is reduced by seven.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MODIFY_MAX_HAND_SIZE);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, numberGenerator(-7));
			this.addEffectPart(part);
		}
	}

	public JinGitaxiasCoreAugur(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		// Flash
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// At the beginning of your end step, draw seven cards.
		this.addAbility(new JinGitaxiasCoreAugurAbility1(state));

		// Each opponent's maximum hand size is reduced by seven.
		this.addAbility(new JinGitaxiasCoreAugurAbility2(state));
	}
}
