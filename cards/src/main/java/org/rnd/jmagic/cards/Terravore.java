package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Terravore")
@Types({Type.CREATURE})
@SubTypes({SubType.LHURGOYF})
@ManaCost("1GG")
@Printings({@Printings.Printed(ex = Expansion.ODYSSEY, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class Terravore extends Card
{
	public static final class TerravoreAbility1 extends CharacteristicDefiningAbility
	{
		public TerravoreAbility1(GameState state)
		{
			super(state, "Terravore's power and toughness are each equal to the number of land cards in all graveyards.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator landCards = Intersect.instance(HasType.instance(Type.LAND), InZone.instance(GraveyardOf.instance(Players.instance())));
			SetGenerator number = Count.instance(landCards);
			this.addEffectPart(setPowerAndToughness(This.instance(), number, number));
		}
	}

	public Terravore(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Terravore's power and toughness are each equal to the number of land
		// cards in all graveyards.
		this.addAbility(new TerravoreAbility1(state));
	}
}
