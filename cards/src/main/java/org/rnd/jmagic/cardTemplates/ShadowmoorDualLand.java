package org.rnd.jmagic.cardTemplates;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public abstract class ShadowmoorDualLand extends Card
{
	public final static class TapForColored extends ActivatedAbility
	{
		private String mana1;
		private String mana2;

		public TapForColored(GameState state, String mana1, String mana2)
		{
			super(state, "(" + mana1 + mana2 + "), (T): Add (" + mana1 + ")(" + mana1 + "), (" + mana1 + ")(" + mana2 + "), or (" + mana2 + ")(" + mana2 + ") to your mana pool.");

			this.mana1 = mana1;
			this.mana2 = mana2;

			this.setManaCost(new ManaPool("(" + mana1 + mana2 + ")"));
			this.costsTap = true;

			String mana = "(" + mana1 + mana2 + ")(" + mana1 + mana2 + ")";

			EventFactory factory = new EventFactory(EventType.ADD_MANA, ("Add (" + mana1 + ")(" + mana1 + "), (" + mana1 + ")(" + mana2 + "), or (" + mana2 + ")(" + mana2 + ") to your mana pool."));
			factory.parameters.put(EventType.Parameter.SOURCE, AbilitySource.instance(This.instance()));
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.MANA, Identity.instance(new ManaPool(mana)));
			this.addEffect(factory);
		}

		@Override
		public TapForColored create(Game game)
		{
			return new TapForColored(game.physicalState, this.mana1, this.mana2);
		}
	}

	public ShadowmoorDualLand(GameState state, String mana1, String mana2)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));
		this.addAbility(new TapForColored(state, mana1, mana2));
	}
}
