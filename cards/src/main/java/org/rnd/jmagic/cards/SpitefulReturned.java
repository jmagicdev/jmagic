package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.SimpleEventPattern;

@Name("Spiteful Returned")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class SpitefulReturned extends Card
{
	public static final class SpitefulReturnedAbility1 extends EventTriggeredAbility
	{
		public SpitefulReturnedAbility1(GameState state)
		{
			super(state, "Whenever Spiteful Returned or enchanted creature attacks, defending player loses 2 life.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			pattern.put(EventType.Parameter.OBJECT, EnchantedBy.instance(ABILITY_SOURCE_OF_THIS));
			this.addPattern(pattern);
			this.addPattern(whenThisAttacks());

			SetGenerator defender = DefendingPlayer.instance(EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.OBJECT));
			this.addEffect(loseLife(defender, 2, "Defending player loses 2 life."));
		}
	}

	public static final class SpitefulReturnedAbility2 extends StaticAbility
	{
		public SpitefulReturnedAbility2(GameState state)
		{
			super(state, "Enchanted creature gets +1/+1.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +1, +1));
		}
	}

	public SpitefulReturned(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Bestow (3)(B) (If you cast this card for its bestow cost, it's an
		// Aura spell with enchant creature. It becomes a creature again if it's
		// not attached to a creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bestow(state, "(3)(B)"));

		// Whenever Spiteful Returned or enchanted creature attacks, defending
		// player loses 2 life.
		this.addAbility(new SpitefulReturnedAbility1(state));

		// Enchanted creature gets +1/+1.
		this.addAbility(new SpitefulReturnedAbility2(state));
	}
}
