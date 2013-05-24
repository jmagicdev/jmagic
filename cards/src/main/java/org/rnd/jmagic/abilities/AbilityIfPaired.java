package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public abstract class AbilityIfPaired extends StaticAbility
{
	public AbilityIfPaired(GameState state, String abilityText, Class<?>... abilities)
	{
		super(state, abilityText);
		SetGenerator pairedWithThis = PairedWith.instance(This.instance());
		this.canApply = Both.instance(this.canApply, pairedWithThis);
		this.addEffectPart(addAbilityToObject(Union.instance(This.instance(), pairedWithThis), abilities));
	}

	public static final class Final extends AbilityIfPaired
	{
		private String abilityText;
		private Class<?>[] abilities;

		public Final(GameState state, String abilityText, Class<?>... abilities)
		{
			super(state, abilityText, abilities);
			this.abilities = abilities;
			this.abilityText = abilityText;
		}

		@Override
		public Final create(Game game)
		{
			return new Final(game.physicalState, this.abilityText, this.abilities);
		}
	}
}
