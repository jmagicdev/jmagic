package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fog Bank")
@Types({Type.CREATURE})
@SubTypes({SubType.WALL})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class FogBank extends Card
{
	public static final class FogBankAbility2 extends StaticAbility
	{
		public FogBankAbility2(GameState state)
		{
			super(state, "Prevent all combat damage that would be dealt to and dealt by Fog Bank.");

			ReplacementEffect replacement = new org.rnd.jmagic.abilities.PreventCombatDamageDealtToOrBy(state.game, This.instance(), "Fog Bank");
			this.addEffectPart(replacementEffectPart(replacement));
		}
	}

	public FogBank(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(2);

		// Defender (This creature can't attack.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Prevent all combat damage that would be dealt to and dealt by Fog
		// Bank.
		this.addAbility(new FogBankAbility2(state));
	}
}
