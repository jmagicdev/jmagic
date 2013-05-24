package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Submerge")
@Types({Type.INSTANT})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Expansion.NEMESIS, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Submerge extends Card
{
	public static final class ForFree extends StaticAbility
	{
		public ForFree(GameState state)
		{
			super(state, "If an opponent controls a Forest and you control an Island, you may cast Submerge without paying its mana cost.");

			CostCollection altCost = new CostCollection(CostCollection.TYPE_ALTERNATE);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ALTERNATE_COST);
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.instance(altCost));
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());

			this.addEffectPart(part);

			SetGenerator opponentForest = Intersect.instance(ControlledBy.instance(OpponentsOf.instance(You.instance())), HasSubType.instance(SubType.FOREST));
			SetGenerator youIsland = Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.ISLAND));
			this.canApply = Both.instance(opponentForest, youIsland);
		}
	}

	public Submerge(GameState state)
	{
		super(state);

		// If an opponent controls a Forest and you control an Island, you may
		// cast Submerge without paying its mana cost.
		this.addAbility(new ForFree(state));

		// Put target creature on top of its owner's library.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

		EventFactory effect = new EventFactory(EventType.PUT_INTO_LIBRARY, "Put target creature on top of its owner's library.");
		effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
		effect.parameters.put(EventType.Parameter.OBJECT, target);
		effect.parameters.put(EventType.Parameter.INDEX, numberGenerator(1));
		this.addEffect(effect);
	}
}
