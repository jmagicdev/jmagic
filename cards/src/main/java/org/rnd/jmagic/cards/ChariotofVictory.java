package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chariot of Victory")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("3")
@ColorIdentity({})
public final class ChariotofVictory extends Card
{
	public static final class ChariotofVictoryAbility0 extends StaticAbility
	{
		public ChariotofVictoryAbility0(GameState state)
		{
			super(state, "Equipped creature has first strike, trample, and haste.");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()),//
					org.rnd.jmagic.abilities.keywords.FirstStrike.class,//
					org.rnd.jmagic.abilities.keywords.Trample.class,//
					org.rnd.jmagic.abilities.keywords.Haste.class));
		}
	}

	public ChariotofVictory(GameState state)
	{
		super(state);

		// Equipped creature has first strike, trample, and haste.
		this.addAbility(new ChariotofVictoryAbility0(state));

		// Equip (1)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(1)"));
	}
}
