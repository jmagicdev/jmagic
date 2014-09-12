package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.SimpleZoneChangePattern;

@Name("Eternal Thirst")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class EternalThirst extends Card
{
	public static final class StayThirstyMyFriends extends EventTriggeredAbility
	{
		public StayThirstyMyFriends(GameState state)
		{
			super(state, "");
			SimpleZoneChangePattern death = new SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), CreaturePermanents.instance(), OpponentsOf.instance(You.instance()), true);
			this.addPattern(death);
			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "this creature"));
		}
	}

	public static final class EternalThirstAbility1 extends StaticAbility
	{
		public EternalThirstAbility1(GameState state)
		{
			super(state, "Enchanted creature has lifelink and \"Whenever a creature an opponent controls dies, put a +1/+1 counter on this creature.\"");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Lifelink.class, StayThirstyMyFriends.class));
		}
	}

	public EternalThirst(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature has lifelink and
		// "Whenever a creature an opponent controls dies, put a +1/+1 counter on this creature."
		// (Damage dealt by a creature with lifelink also causes its controller
		// to gain that much life.)
		this.addAbility(new EternalThirstAbility1(state));
	}
}
