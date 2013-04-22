package org.rnd.jmagic.cardTemplates;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public abstract class AlaraRebornBlade extends Card
{
	public static final class GoldBladeBonus extends StaticAbility
	{
		private String thisName;
		private Class<? extends Keyword> ability;

		public GoldBladeBonus(GameState state, String thisName, Class<? extends Keyword> ability)
		{
			super(state, "As long as you control another multicolored permanent, " + thisName + " gets +1/+1 and has " + ability.getAnnotation(Name.class).value() + ".");

			this.thisName = thisName;
			this.ability = ability;

			SetGenerator yourGoldPermanents = Intersect.instance(ControlledBy.instance(You.instance()), Multicolored.instance());
			this.canApply = Both.instance(this.canApply, RelativeComplement.instance(yourGoldPermanents, This.instance()));

			this.addEffectPart(modifyPowerAndToughness(This.instance(), 1, 1));

			this.addEffectPart(addAbilityToObject(This.instance(), ability));
		}

		@Override
		public GoldBladeBonus create(Game game)
		{
			return new GoldBladeBonus(game.physicalState, this.thisName, this.ability);
		}
	}

	public AlaraRebornBlade(GameState state, Class<? extends Keyword> ability)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new GoldBladeBonus(state, this.getName(), ability));
	}

}
