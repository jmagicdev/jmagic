package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Mesa Enchantress")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.DRUID})
@ManaCost("1WW")
@ColorIdentity({Color.WHITE})
public final class MesaEnchantress extends Card
{
	public static final class Tingle extends EventTriggeredAbility
	{
		public Tingle(GameState state)
		{
			super(state, "Whenever you cast an enchantment spell, you may draw a card.");

			SetGenerator enchantmentSpells = Intersect.instance(Spells.instance(), HasType.instance(Type.ENCHANTMENT));
			SetGenerator yourEnchantmentSpells = Intersect.instance(ControlledBy.instance(You.instance(), Stack.instance()), enchantmentSpells);

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.OBJECT, yourEnchantmentSpells);
			this.addPattern(pattern);

			this.addEffect(youMay(drawCards(You.instance(), 1, "Draw a card"), "You may draw a card."));
		}
	}

	public MesaEnchantress(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(2);

		this.addAbility(new Tingle(state));
	}
}
