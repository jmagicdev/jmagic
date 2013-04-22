package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Avatar of Might")
@Types({Type.CREATURE})
@SubTypes({SubType.AVATAR})
@ManaCost("6GG")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.PROPHECY, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class AvatarofMight extends Card
{
	// If an opponent controls at least four more creatures than you, Avatar of
	// Might costs 6 less to cast.
	public static final class CostReduction extends StaticAbility
	{
		public CostReduction(GameState state)
		{
			super(state, "If an opponent controls at least four more creatures than you, Avatar of Might costs (6) less to cast.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MANA_COST_REDUCTION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.instance(new ManaPool("6")));

			this.addEffectPart(part);
			SetGenerator youControl = ControlledBy.instance(You.instance());
			SetGenerator creaturesYouControl = Intersect.instance(CreaturePermanents.instance(), youControl);

			SetGenerator yourOpponentsControl = ControlledBy.instance(OpponentsOf.instance(You.instance()));
			SetGenerator creaturesYourOpponentsControl = Intersect.instance(CreaturePermanents.instance(), yourOpponentsControl);

			SetGenerator creaturesEachOpponentControls = SplitOnController.instance(creaturesYourOpponentsControl);
			SetGenerator mostCreatures = LargestSet.instance(creaturesEachOpponentControls);

			SetGenerator difference = Subtract.instance(Count.instance(mostCreatures), Count.instance(creaturesYouControl));

			// doesn't union with this.canApply since it completely changes when
			// it applies
			this.canApply = Intersect.instance(difference, Between.instance(4, null));
		}
	}

	public AvatarofMight(GameState state)
	{
		super(state);

		this.setPower(8);
		this.setToughness(8);

		this.addAbility(new CostReduction(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
	}
}
