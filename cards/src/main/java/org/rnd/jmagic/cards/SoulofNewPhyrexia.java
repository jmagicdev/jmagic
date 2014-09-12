package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Soul of New Phyrexia")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.AVATAR})
@ManaCost("6")
@ColorIdentity({})
public final class SoulofNewPhyrexia extends Card
{
	public static final class SoulofNewPhyrexiaAbility1 extends ActivatedAbility
	{
		public SoulofNewPhyrexiaAbility1(GameState state)
		{
			super(state, "(5): Permanents you control gain indestructible until end of turn.");
			this.setManaCost(new ManaPool("(5)"));

			SetGenerator youControl = ControlledBy.instance(You.instance());
			this.addEffect(addAbilityUntilEndOfTurn(youControl, org.rnd.jmagic.abilities.keywords.Indestructible.class, "Permanents you control gain indestructible until end of turn."));
		}
	}

	public static final class SoulofNewPhyrexiaAbility2 extends ActivatedAbility
	{
		public SoulofNewPhyrexiaAbility2(GameState state)
		{
			super(state, "(5), Exile Soul of New Phyrexia from your graveyard: Permanents you control gain indestructible until end of turn.");
			this.setManaCost(new ManaPool("(5)"));
			this.addCost(exile(ABILITY_SOURCE_OF_THIS, "Exile Soul of New Phyrexia from your graveyard"));
			this.activateOnlyFromGraveyard();

			SetGenerator youControl = ControlledBy.instance(You.instance());
			this.addEffect(addAbilityUntilEndOfTurn(youControl, org.rnd.jmagic.abilities.keywords.Indestructible.class, "Permanents you control gain indestructible until end of turn."));

		}
	}

	public SoulofNewPhyrexia(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// (5): Permanents you control gain indestructible until end of turn.
		this.addAbility(new SoulofNewPhyrexiaAbility1(state));

		// (5), Exile Soul of New Phyrexia from your graveyard: Permanents you
		// control gain indestructible until end of turn.
		this.addAbility(new SoulofNewPhyrexiaAbility2(state));
	}
}
