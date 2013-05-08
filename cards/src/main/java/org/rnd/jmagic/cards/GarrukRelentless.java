package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Garruk Relentless")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.GARRUK})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.MYTHIC)})
@ColorIdentity({Color.GREEN})
@BackFace(GarruktheVeilCursed.class)
public final class GarrukRelentless extends Card
{
	public static final class GarrukRelentlessAbility0 extends StateTriggeredAbility
	{
		public GarrukRelentlessAbility0(GameState state)
		{
			super(state, "When Garruk Relentless has two or fewer loyalty counters on him, transform him.");

			SetGenerator loyaltyCountersOnThis = CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.LOYALTY);
			SetGenerator numLoyaltyCountersOnThis = Count.instance(loyaltyCountersOnThis);
			SetGenerator twoOrLess = Between.instance(null, 2);
			SetGenerator triggerCondition = Intersect.instance(twoOrLess, numLoyaltyCountersOnThis);
			this.addCondition(triggerCondition);

			this.addEffect(transformThis("Garruk Relentless"));
		}
	}

	public static final class GarrukRelentlessAbility1 extends LoyaltyAbility
	{
		public GarrukRelentlessAbility1(GameState state)
		{
			super(state, 0, "Garruk Relentless deals 3 damage to target creature. That creature deals damage equal to its power to him.");

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

			this.addEffect(permanentDealDamage(3, target, "Garruk Relentless deals 3 damage to target creature."));

			EventFactory damage = new EventFactory(EventType.DEAL_DAMAGE_EVENLY, "That creature deals damage equal to its power to him.");
			damage.parameters.put(EventType.Parameter.SOURCE, target);
			damage.parameters.put(EventType.Parameter.NUMBER, PowerOf.instance(target));
			damage.parameters.put(EventType.Parameter.TAKER, ABILITY_SOURCE_OF_THIS);
			this.addEffect(damage);
		}
	}

	public static final class GarrukRelentlessAbility2 extends LoyaltyAbility
	{
		public GarrukRelentlessAbility2(GameState state)
		{
			super(state, 0, "Put a 2/2 green Wolf creature token onto the battlefield.");

			CreateTokensFactory factory = new CreateTokensFactory(1, 2, 2, "Put a 2/2 green Wolf creature token onto the battlefield.");
			factory.setColors(Color.GREEN);
			factory.setSubTypes(SubType.WOLF);
			this.addEffect(factory.getEventFactory());
		}
	}

	public GarrukRelentless(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(3);

		// When Garruk Relentless has two or fewer loyalty counters on him,
		// transform him.
		this.addAbility(new GarrukRelentlessAbility0(state));

		// 0: Garruk Relentless deals 3 damage to target creature. That creature
		// deals damage equal to its power to him.
		this.addAbility(new GarrukRelentlessAbility1(state));

		// 0: Put a 2/2 green Wolf creature token onto the battlefield.
		this.addAbility(new GarrukRelentlessAbility2(state));
	}
}
