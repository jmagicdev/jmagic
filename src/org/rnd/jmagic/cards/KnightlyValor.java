package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Knightly Valor")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("4W")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class KnightlyValor extends Card
{
	public static final class KnightlyValorAbility1 extends EventTriggeredAbility
	{
		public KnightlyValorAbility1(GameState state)
		{
			super(state, "When Knightly Valor enters the battlefield, put a 2/2 white Knight creature token with vigilance onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			CreateTokensFactory factory = new CreateTokensFactory(1, 2, 2, "Put a 2/2 white Knight creature token with vigilance onto the battlefield.");
			factory.setColors(Color.WHITE);
			factory.setSubTypes(SubType.KNIGHT);
			factory.addAbility(org.rnd.jmagic.abilities.keywords.Vigilance.class);
			this.addEffect(factory.getEventFactory());
		}
	}

	public KnightlyValor(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// When Knightly Valor enters the battlefield, put a 2/2 white Knight
		// creature token with vigilance onto the battlefield.
		this.addAbility(new KnightlyValorAbility1(state));

		// Enchanted creature gets +2/+2 and has vigilance.
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, EnchantedBy.instance(This.instance()), "Enchanted creature", +2, +2, org.rnd.jmagic.abilities.keywords.Vigilance.class, false));
	}
}
