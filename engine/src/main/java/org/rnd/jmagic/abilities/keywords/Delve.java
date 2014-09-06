package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Delve")
public final class Delve extends Keyword
{
	public static final String COST_TYPE = "Delve";

	public Delve(GameState state)
	{
		super(state, "Delve");
	}

	@Override
	protected java.util.List<org.rnd.jmagic.engine.StaticAbility> createStaticAbilities()
	{
		return java.util.Collections.<StaticAbility>singletonList(new DelveAbility(this.state));
	}

	public static final class DelveAbility extends StaticAbility
	{
		public DelveAbility(GameState state)
		{
			super(state, "Each card you exile from your graveyard while casting this spell pays for (1).");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ALTERNATE_PAYMENT);
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.instance(DELVE_PAYMENT));
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			this.addEffectPart(part);

			this.canApply = THIS_IS_ON_THE_STACK;
		}
	}

	public static PlayerInterface.ChooseReason DELVE_REASON = new PlayerInterface.ChooseReason("Delve", "Exile cards from your graveyard to pay generic mana with delve.", true);

	private static AlternateManaPayment DELVE_PAYMENT = new DelvePayment();

	private static final class DelvePayment extends AlternateManaPayment
	{
		@Override
		public int hashCode()
		{
			return 1;
		}

		@Override
		public boolean equals(Object obj)
		{
			return obj instanceof DelvePayment;
		}

		@Override
		public void pay(ManaPool cost, GameObject source)
		{
			int quantity = cost.stream().mapToInt(m -> m.colorless).sum();
			EventFactory exile = new EventFactory(EventType.EXILE_CHOICE, "Exile up to " + quantity + " cards from your graveyard.");
			exile.parameters.put(EventType.Parameter.CAUSE, This.instance());
			exile.parameters.put(EventType.Parameter.NUMBER, Between.instance(0, quantity));
			exile.parameters.put(EventType.Parameter.OBJECT, InZone.instance(GraveyardOf.instance(You.instance())));
			exile.parameters.put(EventType.Parameter.PLAYER, You.instance());
			Event exileEvent = exile.createEvent(source.game, source);
			exileEvent.perform(null, true);

			int exiled = exileEvent.getChoices(source.getController(source.game.actualState)).size();
			cost.reduce(new ManaPool("" + exiled));
		}
	}
}
