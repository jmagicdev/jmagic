package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chromanticore")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.MANTICORE})
@ManaCost("WUBRG")
@ColorIdentity({Color.RED, Color.WHITE, Color.BLUE, Color.BLACK, Color.GREEN})
public final class Chromanticore extends Card
{
	public static final class ChromanticoreAbility2 extends StaticAbility
	{
		public ChromanticoreAbility2(GameState state)
		{
			super(state, "Enchanted creature gets +4/+4 and has flying, first strike, vigilance, trample, and lifelink.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +4, +4));
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()),//
					org.rnd.jmagic.abilities.keywords.Flying.class,//
					org.rnd.jmagic.abilities.keywords.FirstStrike.class,//
					org.rnd.jmagic.abilities.keywords.Vigilance.class,//
					org.rnd.jmagic.abilities.keywords.Trample.class,//
					org.rnd.jmagic.abilities.keywords.Lifelink.class));
		}
	}

	public Chromanticore(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Bestow (2)(W)(U)(B)(R)(G) (If you cast this card for its bestow cost,
		// it's an Aura spell with enchant creature. It becomes a creature again
		// if it's not attached to a creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bestow(state, "(2)(W)(U)(B)(R)(G)"));

		// Flying, first strike, vigilance, trample, lifelink
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));

		// Enchanted creature gets +4/+4 and has flying, first strike,
		// vigilance, trample, and lifelink.
		this.addAbility(new ChromanticoreAbility2(state));
	}
}
