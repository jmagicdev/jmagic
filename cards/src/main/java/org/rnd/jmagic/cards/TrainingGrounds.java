package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Training Grounds")
@Types({Type.ENCHANTMENT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class TrainingGrounds extends Card
{
	public static final class TrainingGroundsAbility0 extends StaticAbility
	{
		public TrainingGroundsAbility0(GameState state)
		{
			super(state, "Activated abilities of creatures you control cost up to (2) less to activate. This effect can't reduce the amount of mana an ability costs to activate to less than one mana.");

			ContinuousEffect.Part reduce = new ContinuousEffect.Part(ContinuousEffectType.MANA_COST_REDUCTION);
			reduce.parameters.put(ContinuousEffectType.Parameter.OBJECT, AbilitiesOf.instance(CREATURES_YOU_CONTROL));
			reduce.parameters.put(ContinuousEffectType.Parameter.COST, Identity.instance(new ManaPool("2")));
			reduce.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Empty.instance());
			this.addEffectPart(reduce);
		}
	}

	public TrainingGrounds(GameState state)
	{
		super(state);

		// Activated abilities of creatures you control cost up to (2) less to
		// activate. This effect can't reduce the amount of mana an ability
		// costs to activate to less than one mana.
		this.addAbility(new TrainingGroundsAbility0(state));
	}
}
