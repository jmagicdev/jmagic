package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Doubling Cube")
@Types({Type.ARTIFACT})
@ManaCost("2")
@ColorIdentity({})
public final class DoublingCube extends Card
{
	public static final class DoubleMana extends ActivatedAbility
	{
		public DoubleMana(GameState state)
		{
			super(state, "(3), (T): Double the amount of each type of mana in your mana pool.");

			this.setManaCost(new ManaPool("3"));
			this.costsTap = true;

			EventType.ParameterMap manaParameters = new EventType.ParameterMap();
			manaParameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			manaParameters.put(EventType.Parameter.PLAYER, You.instance());
			manaParameters.put(EventType.Parameter.MANA, ManaInPool.instance(You.instance()));
			this.addEffect(new EventFactory(EventType.DOUBLE_MANA, manaParameters, "Double the amount of each type of mana in your mana pool"));
		}
	}

	public DoublingCube(GameState state)
	{
		super(state);

		this.addAbility(new DoubleMana(state));
	}
}
