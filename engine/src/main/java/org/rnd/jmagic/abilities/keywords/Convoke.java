package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Convoke")
public final class Convoke extends Keyword
{
	public Convoke(GameState state)
	{
		super(state, "Convoke");
	}

	@Override
	protected java.util.List<org.rnd.jmagic.engine.StaticAbility> createStaticAbilities()
	{
		return java.util.Collections.<StaticAbility>singletonList(new ConvokeAbility(this.state));
	}

	public static final class ConvokeAbility extends StaticAbility
	{
		public ConvokeAbility(GameState state)
		{
			super(state, "Each creature you tap while casting this spell pays for (1) or by one mana of that creature's color.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ALTERNATE_PAYMENT);
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.instance(CONVOKE_PAYMENT));
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			this.addEffectPart(part);

			this.canApply = THIS_IS_ON_THE_STACK;
		}
	}

	public static PlayerInterface.ChooseReason CONVOKE_WHITE_REASON = new PlayerInterface.ChooseReason("Convoke", "Tap white creatures to pay white mana with convoke.", true);
	public static PlayerInterface.ChooseReason CONVOKE_BLUE_REASON = new PlayerInterface.ChooseReason("Convoke", "Tap blue creatures to pay blue mana with convoke.", true);
	public static PlayerInterface.ChooseReason CONVOKE_BLACK_REASON = new PlayerInterface.ChooseReason("Convoke", "Tap black creatures to pay black mana with convoke.", true);
	public static PlayerInterface.ChooseReason CONVOKE_RED_REASON = new PlayerInterface.ChooseReason("Convoke", "Tap red creatures to pay red mana with convoke.", true);
	public static PlayerInterface.ChooseReason CONVOKE_GREEN_REASON = new PlayerInterface.ChooseReason("Convoke", "Tap green creatures to pay green mana with convoke.", true);
	public static PlayerInterface.ChooseReason CONVOKE_GENERIC_REASON = new PlayerInterface.ChooseReason("Convoke", "Tap any creatures to pay generic mana with convoke.", true);

	public static java.util.Map<Color, PlayerInterface.ChooseReason> REASONS;
	static
	{
		REASONS = new java.util.HashMap<>();
		REASONS.put(Color.WHITE, CONVOKE_WHITE_REASON);
		REASONS.put(Color.BLUE, CONVOKE_BLUE_REASON);
		REASONS.put(Color.BLACK, CONVOKE_BLACK_REASON);
		REASONS.put(Color.RED, CONVOKE_RED_REASON);
		REASONS.put(Color.GREEN, CONVOKE_GREEN_REASON);
	}

	private static AlternateManaPayment CONVOKE_PAYMENT = new ConvokePayment();

	private static final class ConvokePayment extends AlternateManaPayment
	{
		@Override
		public int hashCode()
		{
			return 1;
		}

		@Override
		public boolean equals(Object obj)
		{
			return obj instanceof AlternateManaPayment;
		}

		private static int manaOfType(ManaSymbol.ManaType type, ManaPool cost)
		{
			int ret = 0;
			if(type == ManaSymbol.ManaType.COLORLESS)
				for(ManaSymbol m: cost)
					ret += m.colorless;
			else
				for(ManaSymbol m: cost)
					if(m.colors.contains(type.getColor()))
						ret++;
			return ret;
		}

		@Override
		public void pay(ManaPool cost, GameObject source)
		{
			ManaPool paid = new ManaPool();
			for(Color color: Color.allColors())
			{
				int quantity = manaOfType(color.getManaType(), cost);
				if(quantity == 0)
					continue;

				EventFactory tapCreatures = new EventFactory(EventType.TAP_CHOICE, "Tap up to " + org.rnd.util.NumberNames.get(quantity) + " " + color + " creatures you control.");
				tapCreatures.parameters.put(EventType.Parameter.CAUSE, This.instance());
				tapCreatures.parameters.put(EventType.Parameter.PLAYER, You.instance());
				tapCreatures.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(HasColor.instance(color), CREATURES_YOU_CONTROL));
				tapCreatures.parameters.put(EventType.Parameter.NUMBER, Between.instance(0, quantity));
				tapCreatures.parameters.put(EventType.Parameter.REASON, Identity.instance(REASONS.get(color)));
				Event tapEvent = tapCreatures.createEvent(source.game, source);
				tapEvent.perform(null, true);

				Set tapped = tapEvent.getChoices(source.getController(source.game.actualState));
				for(int i = 0; i < tapped.size(); i++)
					paid.add(new ManaSymbol(color));
			}
			// colorless
			int quantity = manaOfType(ManaSymbol.ManaType.COLORLESS, cost);
			if(quantity > 0)
			{
				EventFactory tapCreatures = new EventFactory(EventType.TAP_CHOICE, "Tap up to " + org.rnd.util.NumberNames.get(quantity) + " creatures you control (to pay the generic cost).");
				tapCreatures.parameters.put(EventType.Parameter.CAUSE, This.instance());
				tapCreatures.parameters.put(EventType.Parameter.PLAYER, You.instance());
				tapCreatures.parameters.put(EventType.Parameter.CHOICE, CREATURES_YOU_CONTROL);
				tapCreatures.parameters.put(EventType.Parameter.NUMBER, Between.instance(0, quantity));
				tapCreatures.parameters.put(EventType.Parameter.REASON, Identity.instance(CONVOKE_GENERIC_REASON));
				Event tapEvent = tapCreatures.createEvent(source.game, source);
				tapEvent.perform(null, true);

				Set tapped = tapEvent.getChoices(source.getController(source.game.actualState));
				paid.addAll(new ManaPool("" + tapped.size()));
			}
			cost.reduce(paid);
		}
	}
}
