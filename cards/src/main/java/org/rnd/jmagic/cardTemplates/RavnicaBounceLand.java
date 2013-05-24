package org.rnd.jmagic.cardTemplates;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public abstract class RavnicaBounceLand extends Card
{
	public static final class Bounce extends EventTriggeredAbility
	{
		private final String landName;

		public Bounce(GameState state, String landName)
		{
			super(state, "When " + landName + " enters the battlefield, return a land you control to its owner's hand.");
			this.landName = landName;

			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator landsYouControl = Intersect.instance(HasType.instance(Type.LAND), ControlledBy.instance(You.instance()));

			EventFactory factory = new EventFactory(EventType.PUT_INTO_HAND_CHOICE, "Return a land you control to its owner's hand.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			factory.parameters.put(EventType.Parameter.CHOICE, landsYouControl);
			this.addEffect(factory);
		}

		@Override
		public Bounce create(Game game)
		{
			return new Bounce(game.physicalState, this.landName);
		}
	}

	public static final class TapForColored extends org.rnd.jmagic.abilities.TapForMana
	{
		protected char mana1;
		protected char mana2;

		public TapForColored(GameState state, char mana1, char mana2)
		{
			super(state, "(" + mana1 + ")(" + mana2 + ")");
			this.mana1 = mana1;
			this.mana2 = mana2;
		}

		@Override
		public TapForColored create(Game game)
		{
			return new TapForColored(game.physicalState, this.mana1, this.mana2);
		}
	}

	public RavnicaBounceLand(GameState state, char mana1, char mana2)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));
		this.addAbility(new Bounce(state, this.getName()));
		this.addAbility(new TapForColored(state, mana1, mana2));
	}
}
