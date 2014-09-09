package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Security Blockade")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class SecurityBlockade extends Card
{
	public static final class SecurityBlockadeAbility1 extends EventTriggeredAbility
	{
		public SecurityBlockadeAbility1(GameState state)
		{
			super(state, "When Security Blockade enters the battlefield, put a 2/2 white Knight creature token with vigilance onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			CreateTokensFactory token = new CreateTokensFactory(1, 2, 2, "Put a 2/2 white Knight creature token with vigilance onto the battlefield.");
			token.setColors(Color.WHITE);
			token.setSubTypes(SubType.KNIGHT);
			token.addAbility(org.rnd.jmagic.abilities.keywords.Vigilance.class);
			this.addEffect(token.getEventFactory());
		}
	}

	public static final class SecurityBlockadeAbility2 extends StaticAbility
	{
		public static final class PreventDamage extends ActivatedAbility
		{
			public PreventDamage(GameState state)
			{
				super(state, "(T): Prevent the next 1 damage that would be dealt to you this turn.");
				this.costsTap = true;

				EventFactory factory = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "Prevent the next 1 damage that would be dealt to you this turn.");
				factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
				factory.parameters.put(EventType.Parameter.PREVENT, Identity.instance(You.instance(), 1));
				this.addEffect(factory);
			}
		}

		public SecurityBlockadeAbility2(GameState state)
		{
			super(state, "Enchanted land has \"(T): Prevent the next 1 damage that would be dealt to you this turn.\"");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), PreventDamage.class));
		}
	}

	public SecurityBlockade(GameState state)
	{
		super(state);

		// Enchant land
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Land(state));

		// When Security Blockade enters the battlefield, put a 2/2 white Knight
		// creature token with vigilance onto the battlefield.
		this.addAbility(new SecurityBlockadeAbility1(state));

		// Enchanted land has
		// "(T): Prevent the next 1 damage that would be dealt to you this turn."
		this.addAbility(new SecurityBlockadeAbility2(state));
	}
}
