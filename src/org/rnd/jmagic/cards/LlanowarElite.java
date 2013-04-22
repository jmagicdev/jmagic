package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Llanowar Elite")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.INVASION, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class LlanowarElite extends Card
{
	public static final class ElvishSteroids extends StaticAbility
	{
		private final CostCollection kickerCost;

		public ElvishSteroids(GameState state, CostCollection kickerCost)
		{
			super(state, "If Llanowar Elite was kicked, it enters the battlefield with five +1/+1 counters on it.");

			this.kickerCost = kickerCost;

			this.canApply = Both.instance(this.canApply, ThisPermanentWasKicked.instance(kickerCost));

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, this.getName());
			replacement.addPattern(asThisEntersTheBattlefield());
			replacement.addEffect(putCounters(5, Counter.CounterType.PLUS_ONE_PLUS_ONE, NewObjectOf.instance(replacement.replacedByThis()), "Llanowar Elite enters the battlefield with five +1/+1 counters on it."));

			this.addEffectPart(replacementEffectPart(replacement));
		}

		@Override
		public ElvishSteroids create(Game game)
		{
			return new ElvishSteroids(game.physicalState, this.kickerCost);
		}
	}

	public LlanowarElite(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);
		org.rnd.jmagic.abilities.keywords.Kicker ability = new org.rnd.jmagic.abilities.keywords.Kicker(state, "8");
		this.addAbility(ability);

		CostCollection kickerCost = ability.costCollections[0];

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		this.addAbility(new ElvishSteroids(state, kickerCost));
	}
}
