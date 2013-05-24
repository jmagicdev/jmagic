package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Argothian Enchantress")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.DRUID})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class ArgothianEnchantress extends Card
{
	public static final class ArgothianEnchantressAbility1 extends EventTriggeredAbility
	{
		public ArgothianEnchantressAbility1(GameState state)
		{
			super(state, "Whenever you cast an enchantment spell, draw a card.");

			SimpleEventPattern whenYouCastASpell = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			whenYouCastASpell.put(EventType.Parameter.PLAYER, You.instance());
			whenYouCastASpell.put(EventType.Parameter.OBJECT, HasType.instance(Type.ENCHANTMENT));
			this.addPattern(whenYouCastASpell);

			this.addEffect(drawACard());
		}
	}

	public ArgothianEnchantress(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		// Shroud (This permanent can't be the target of spells or abilities.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Shroud(state));

		// Whenever you cast an enchantment spell, draw a card.
		this.addAbility(new ArgothianEnchantressAbility1(state));
	}
}
