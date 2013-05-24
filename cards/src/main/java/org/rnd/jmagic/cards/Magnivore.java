package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Magnivore")
@Types({Type.CREATURE})
@SubTypes({SubType.LHURGOYF})
@ManaCost("2RR")
@Printings({@Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ODYSSEY, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class Magnivore extends Card
{
	public static final class MagnivoreAbility1 extends CharacteristicDefiningAbility
	{
		public MagnivoreAbility1(GameState state)
		{
			super(state, "Magnivore's power and toughness are each equal to the number of sorcery cards in all graveyards.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator count = Count.instance(Intersect.instance(HasType.instance(Type.SORCERY), InZone.instance(GraveyardOf.instance(Players.instance()))));
			this.addEffectPart(setPowerAndToughness(This.instance(), count, count));
		}
	}

	public Magnivore(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Haste (This creature can attack the turn it comes under your
		// control.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Magnivore's power and toughness are each equal to the number of
		// sorcery cards in all graveyards.
		this.addAbility(new MagnivoreAbility1(state));
	}
}
