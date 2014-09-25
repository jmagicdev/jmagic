package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dragon Throne of Tarkir")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("4")
@ColorIdentity({})
public final class DragonThroneofTarkir extends Card
{
	public static final class PumpThings extends ActivatedAbility
	{
		public PumpThings(GameState state)
		{
			super(state, "(2), (T): Other creatures you control gain trample and get +X/+X until end of turn, where X is this creature's power.");
			this.setManaCost(new ManaPool("(2)"));
			this.costsTap = true;

			SetGenerator X = PowerOf.instance(ABILITY_SOURCE_OF_THIS);
			SetGenerator otherCreatures = RelativeComplement.instance(CREATURES_YOU_CONTROL, ABILITY_SOURCE_OF_THIS);
			this.addEffect(ptChangeAndAbilityUntilEndOfTurn(otherCreatures, X, X, "Other creatures you control gain trample and get +X/+X until end of turn, where X is this creature's power.", org.rnd.jmagic.abilities.keywords.Trample.class));
		}
	}

	public static final class DragonThroneofTarkirAbility0 extends StaticAbility
	{
		public DragonThroneofTarkirAbility0(GameState state)
		{
			super(state, "Equipped creature has defender and \"(2), (T): Other creatures you control gain trample and get +X/+X until end of turn, where X is this creature's power.\"");
			this.addEffectPart(addAbilityToObject(EquippedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Defender.class, PumpThings.class));
		}
	}

	public DragonThroneofTarkir(GameState state)
	{
		super(state);

		// Equipped creature has defender and
		// "(2), (T): Other creatures you control gain trample and get +X/+X until end of turn, where X is this creature's power."
		this.addAbility(new DragonThroneofTarkirAbility0(state));

		// Equip (3)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(3)"));
	}
}
